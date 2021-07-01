package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.message.AcceptMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.RejectMessage;
import it.polimi.ingsw.constant.setupper.SetUp;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Each instance is a connection to a specific client
 */
@SuppressWarnings("ALL")
public class SocketClientConnection implements  Observable<String>, ClientConnection, Runnable {

    private final Socket socket;
    private DataOutputStream out;
    private final Server server;
    private String nickName;
    private Room room;

    private boolean active = true;
    private final boolean standby= false;

    /**
     * Default constructor.
     * @param socket
     * @param server
     */
    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * @return true if the connection is active.
     */
    private synchronized boolean isActive(){
        return active;
    }

    /**
     * Sets the player nickname
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Sets the room the connection is inside of.
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @see ClientConnection
     * @param json
     */
    @Override
    public void send(String json) {
            try {
                out.writeUTF(json);
                out.flush();
                //System.out.println("Send" + json);
            } catch(IOException e){
                System.err.println(e.getMessage());
            }

    }

    /**
     * @see ClientConnection
     */
    @Override
    public synchronized void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
    }

    /**
     * Set if a connection is active.
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Close a connection. Finds the room and removes the player from it.
     * If theres no more player in the room removes the room from the server.
     * Then, close the socket.
     * @param playerName
     * @param room
     */
    private void close(String playerName, Room room) {
        System.out.println("Deregistering client...");
        if(room!=null && server.findRoom(room.getRoomName())){
            if(room.disconnectConnection(playerName)){
                server.removeRoom(room);
            }
        }
        System.out.println("Done!");
        closeConnection();
    }

    /**
     * When a socket runs, it stays in waiting for a Setupper.
     * If the Setupper is not accepted send a Reject Message and keeps listening.
     * If the Setupper is accepted sends an Accept Message and makes the Setupper Action,
     * then it stays in waiting for the next message (Should be a move type or a disconnection Setupper).
     * When a connection is set and recives a new Message it calls the notify.
     * If a connection is not Active for some reason or throws an exception calls the close method.
     */
    @Override
    public void run() {
        DataInputStream in;

        try{
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Settable setupper;
            String read;
            boolean confirm= false;
            int numofplayer = 0;
            do {
                read = in.readUTF();
                System.out.println("Recived: "+read);
                setupper= Starter.fromJson(read, Settable.class);
                confirm=setupper.canSetAction(this.server,(SetUp) setupper);
                if(!confirm){
                    send(Starter.toJson(new RejectMessage(), Message.class));
                }
            }while(!confirm);
            send(Starter.toJson(new AcceptMessage(((SetUp) setupper).getRoomName()), Message.class));
            setupper.setAction(server, this, (SetUp) setupper);
            while(isActive()) {
                read = in.readUTF();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            setActive(false);
            close(nickName,room);
        }
    }

    //Observable implementation
    private transient final List<Observer<String>> observers = new ArrayList<>();

    /**
     * @see ClientConnection
     * @param observer
     */
    @Override
    public void addObserver(Observer<String> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * For all observers calls the update method.
     * @param message
     */
    @Override
    public void notify(String message) {
        synchronized (observers) {
            for(Observer<String> observer : observers){
                observer.update(message);
            }
        }
    }

    /**
     * If a setupper can set an action, makes the action.
     * Otherwise sends a RejectMessage.
     * Should be called just for a Disconnection Setupper.
     * @param setupper
     */
    public void handleSetupper(Settable setupper) {
        if(setupper.canSetAction(server,(SetUp) setupper)){
            SetUp set= (SetUp) setupper;
            setupper.setAction(server,this,set);
        }else {
            send(Starter.toJson(new RejectMessage(), RejectMessage.class));
        }
    }
}
