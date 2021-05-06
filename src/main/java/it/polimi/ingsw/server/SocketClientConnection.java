package it.polimi.ingsw.server;

import it.polimi.ingsw.server.observer.Observable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Each instance is a connection to a specific client
 */
public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

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
        Scanner in;

        try{
            in = new Scanner(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            server.lobby(this, read);
            while(isActive()){
                read=in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
        }
    }
}
