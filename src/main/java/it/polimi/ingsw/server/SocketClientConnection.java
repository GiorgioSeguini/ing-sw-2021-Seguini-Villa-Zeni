package it.polimi.ingsw.server;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import it.polimi.ingsw.server.observer.Observable;

/**
 * Each instance is a connection to a specific client
 */
public class SocketClientConnection implements  Observable<String>, ClientConnection, Runnable {

    private Socket socket;
    private DataOutputStream out;
    private Server server;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(String json) {
            try {
                //out.reset();
                //socket.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
                //socket.getOutputStream().flush();
                out.writeUTF(json);
                out.flush();
            } catch(IOException e){
                System.err.println(e.getMessage());
            }

    }

    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final String json){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(json);
            }
        }).start();
    }

    @Override
    public void run() {
        DataInputStream in;

        try{
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            //send("Welcome!\nWhat is your name?");
            String read = in.readUTF();
            int numofplayer= in.readInt();
            server.lobby(this, read, numofplayer);
            while(isActive()){
                read=in.readUTF();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
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
}
