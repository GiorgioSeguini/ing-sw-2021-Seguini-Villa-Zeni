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

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public void send(String json) {
            try {
                out.writeUTF(json);
                out.flush();
            } catch(IOException e){
                System.err.println(e.getMessage());
            }

    }

    @Override
    public synchronized void closeConnection() {
        try {
            //send("Connection closed");
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void close(String playerName, Room room) {
        System.out.println("Deregistering client...");
        if(room!=null && server.findRoom(room.getRoomName())){
            if(room.disconnectConnection(playerName)){
                server.removeRoom(room);
            }
        }
        System.out.println("Done!");
        closeConnection();
    }

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

    @Override
    public void addObserver(Observer<String> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public void notify(String message) {
        synchronized (observers) {
            for(Observer<String> observer : observers){
                observer.update(message);
            }
        }
    }

    public void handleSetupper(Settable setupper) {
        if(setupper.canSetAction(server,(SetUp) setupper)){
            SetUp set= (SetUp) setupper;
            setupper.setAction(server,this,set);
        }else {
            send(Starter.toJson(new RejectMessage(), RejectMessage.class));
        }
    }
}
