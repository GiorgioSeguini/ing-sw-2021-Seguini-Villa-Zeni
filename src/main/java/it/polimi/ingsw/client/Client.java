package it.polimi.ingsw.client;


import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.message.InitialMessage;
import it.polimi.ingsw.constant.message.LastMessage;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Performable;
import it.polimi.ingsw.server.model.DashboardExt;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.MarketExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.server.view.View;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;
    private boolean online = true;
    public DataOutputStream socketOut;
    public boolean recived;
    GameClient simpleGame;
    CLI cli;
    private GUI gui;
    private Controller controller;

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
        //System.out.println(socketIn.nextLine());
        System.out.println("insert 1 for CLI, 2 for GUI");
        int i = stdin.nextInt();
        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            if(i==1) {
                cli = new CLI(this, socketOut);
                Thread t1 = new Thread(cli);
                t1.start();
            }
            else{
                Thread t1 = new Thread(()->GUI.entry(this));
                t1.start();
            }
            t0.join();
            //t1.join();
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

    public void setGui(GUI gui){
        this.gui=gui;
    }

    public UI getUI(){
        if(gui==null)
            return cli;
        return gui;
    }

    public void sendMove(MoveType move){
        if(online) {
            try {
                socketOut.writeUTF(StarterClient.toJson(move, MoveType.class));
                socketOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                //TODO
            }
        }else{
            String s = StarterClient.toJson(move, MoveType.class);
            Performable performable = Starter.fromJson(s, Performable.class);
            this.controller.update(performable);
        }

    }

    public void startOffline(String name){
        online = false;
        ArrayList<PlayerExt> players = new ArrayList<>();
        players.add(new PlayerExt(name));
        GameExt game = null;

        try {
            game=new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), Starter.TokensParser(), Starter.LeaderCardsParser());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //TODO
        }
        Controller controller = new Controller(game);

        Observer<Message> observer = message -> {
            String json = Starter.toJson(message, Message.class);
            Message clientMessage = StarterClient.fromJson(json, Message.class);
            System.out.println(json);
            clientMessage.handleMessage(Client.this);
        };
        //add model - view links
        game.getMarketTray().addObserver(observer);
        game.getDashboard().addObserver(observer);
        game.addObserver(observer);
        for(Player player : game.getPlayers()){
            PlayerExt playerExt = (PlayerExt) player;
            playerExt.getPersonalBoard().addObserver(observer);
            playerExt.getFaithTrack().addObserver(observer);
            playerExt.getDepots().addObserver(observer);
            playerExt.addObserver(observer);
            playerExt.getConverter().addObserver(observer);
        }


        //add controller - view links
        this.controller = controller;

        //send initial message
        int myID = players.get(0).getID();
        observer.update(new InitialMessage(game, myID, game.getActivableLeadCard(players.get(0))));
        observer.update(new LastMessage());

    }

}
