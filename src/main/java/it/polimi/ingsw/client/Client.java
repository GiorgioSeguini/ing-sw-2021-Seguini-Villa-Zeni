package it.polimi.ingsw.client;


import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;
    Game simpleGame;
    CLI cli;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    private boolean active = true;

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncReadFromSocket(final DataInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String read;
                    while (isActive()) {
                        read=socketIn.readUTF();
                        System.out.println(read);
                        Message received = StarterClient.fromJson(read, Message.class);
                        received.handleMessage(Client.this);
                        new Thread(cli).start();
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final DataOutputStream socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        String inputLine = stdin.nextLine();
                        socketOut.writeUTF(inputLine);
                        socketOut.flush();
                    }
                }catch(Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        DataInputStream socketIn = new DataInputStream(socket.getInputStream());
        DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        System.out.println("insert your name");
        //System.out.println(socketIn.nextLine());
        String name = stdin.nextLine();
        socketOut.writeUTF(name);
        socketOut.flush();
        try{
            cli = new CLI(this, socketOut);
            Thread t0 = asyncReadFromSocket(socketIn);
            t0.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    public void setSimpleGame(Game game){
        this.simpleGame = game;
    }

    public Game getSimpleGame(){
        return this.simpleGame;
    }

}
