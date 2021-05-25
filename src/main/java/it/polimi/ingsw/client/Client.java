package it.polimi.ingsw.client;


import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;
    private DataOutputStream socketOut;
    public boolean recived;
    GameClient simpleGame;
    CLI cli;
    private GUI gui;

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
                    while (true) {
                        read=socketIn.readUTF();
                        System.out.println(read);
                        Message received = StarterClient.fromJson(read, Message.class);
                        received.handleMessage(Client.this);
                        recived=true;
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(String s){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        socketOut.writeUTF(s);
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
        socketOut = new DataOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        System.out.println("Inserisci il tuo nome e premi INVIO");
        //System.out.println(socketIn.nextLine());
        String name = stdin.nextLine();
        int x;
        do {
            System.out.println("Con quanti avversari vuoi giocare?\n 1. Da solo \n 2. Un avversario\n 3. Due avversari\n 4. Tre avversari\n");
            System.out.println("--> Digita il numero dell'opzione che preferisci e premi INVIO");
            x=stdin.nextInt();
            if(x<1 || x>4){
                System.out.println("Indice non valido!");
            }
        }while(x<1 || x>4);
        System.out.println("Ottimo "+name+"! Ti stiamo inserendo in una partita da "+x+" giocatori.\nRimani in attesa, la partita inizierà tra breve!");
        socketOut.writeUTF(name);
        socketOut.flush();
        socketOut.writeInt(x);
        socketOut.flush();
        try{
            cli = new CLI(this, socketOut);
            Thread t1 = new Thread(cli);
            t1.start();
            Thread t0 = asyncReadFromSocket(socketIn);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    public void setSimpleGame(GameClient game){
        this.simpleGame = game;
    }

    public GameClient getSimpleGame(){
        return this.simpleGame;
    }

    public void setNumber(String s){

    }

}
