package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.message.InitialMessage;
import it.polimi.ingsw.constant.message.LastMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.setupper.SetUp;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Performable;
import it.polimi.ingsw.server.model.DashboardExt;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.MarketExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client class.
 * Manage the proper operation of the game from client's view.
 */
public class Client {

    private final String ip;
    private final int port;
    private final int ui;       //ui -> 1 for cli, 2 for GUI
    private boolean online = false;
    private boolean active = true;
    //game info
    private GameClient simpleGame;
    private String roomName;
    private GUI gui;
    private CLI cli;

    //for online
    public DataOutputStream socketOut;
    private DataInputStream socketIn;
    private Socket socket;
    private Thread readingThread;

    //for single player offline
    private Controller controller;
    private ExecutorService executor;

    /**
     * Instantiates a new Client.
     *
     * @param ip of type String: the ip address.
     * @param port of type int: the server port.
     * @param ui of type int: the ui
     */
    public Client(String ip, int port, int ui){
        this.ip = ip;
        this.port = port;
        this.ui = ui;
    }

    /**
     * Synchronized method.
     * Check if the client is active.
     *
     * @return of type boolean: True if the client is active. Otherwise False.
     */
    public synchronized boolean isActive(){
        return active;
    }

    /**
     * Set active attribute.
     *
     * @param active of type boolean: the active value.
     */
    public synchronized void setActive(boolean active){
        this.active = active;
        if(!active) notifyAll();
    }

    /**
     * Async read from socket thread.
     *
     * @param socketIn of type DataInputStream: the socket in.
     * @return of type Thread: the thread.
     */
    public Thread asyncReadFromSocket(final DataInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String read;
                    while (isActive()) {
                        read=socketIn.readUTF();
                        //System.out.println(read);
                        Message received = StarterClient.fromJson(read, Message.class);
                        received.handleMessage(Client.this);
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }


    /**
     * Method that run and stop the thread.
     *
     * @throws IOException the io exception.
     */
    public void run() throws IOException {
        try{
            if(ui==1) {
                cli = new CLI(this);
                Thread t1 = new Thread(cli);
                t1.start();
            }
            else{
                GUI.entry(this);
            }
            //chose has been made
            synchronized (this){
                while (isActive()){
                    this.wait();
                }
            }
            //t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            if(online) {
                socketIn.close();
                socketOut.close();
                socket.close();
            }
            //stdin.close();
            System.exit(0);
        }
    }

    /**
     * Set simple game.
     *
     * @param game of type GameClient: the game.
     */
    public void setSimpleGame(GameClient game){
        this.simpleGame = game;
    }

    /**
     * Set room name.
     *
     * @param roomName of type String: the room name.
     */
    public void setRoomName(String roomName){
        this.roomName= roomName;
    }

    /**
     * Gets room name.
     *
     * @return of type String: the room name.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Get simple game client.
     *
     * @return of type GameClient: the game client.
     */
    public GameClient getSimpleGame(){
        return this.simpleGame;
    }

    /**
     * Set gui.
     *
     * @param gui of type GUI: the gui.
     */
    public void setGui(GUI gui){
        this.gui=gui;
    }

    /**
     * Get ui.
     *
     * @return of type UI: the ui.
     */
    public UI getUI(){
        if(gui==null)
            return cli;
        return gui;
    }

    /**
     * Send move to the server.
     *
     * @param move of type MoveType: the move to send.
     */
    public void sendMove(MoveType move){
        if(online) {
            try {
                socketOut.writeUTF(StarterClient.toJson(move, MoveType.class));
                socketOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                this.setActive(false);
            }
        }else{
            executor.submit(()->{
                String s = StarterClient.toJson(move, MoveType.class);
                Performable performable = Starter.fromJson(s, Performable.class);
                this.controller.update(performable);
            });
        }

    }

    /**
     * Start an offline game.
     *
     * @param name of type String: the player's name.
     */
    public void startOffline(String name){
        ArrayList<PlayerExt> players = new ArrayList<>();
        players.add(new PlayerExt(name));
        GameExt game = null;

        game=new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), Starter.TokensParser(), Starter.LeaderCardsParser());

        Controller controller = new Controller(game);

        Observer<Message> observer = message -> {
            String json = Starter.toJson(message, Message.class);
            Message clientMessage = StarterClient.fromJson(json, Message.class);
            //System.out.println(json);
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
        observer.update(new InitialMessage(game, myID, game.getActivableLeadCard(players.get(0)), "LocalRoom"));
        observer.update(new LastMessage());

        online = false;
        executor= Executors.newFixedThreadPool(1);
    }

    /**
     * Sets online.
     *
     * @return of type boolean: True if the online game starts correctly. Otherwise False.
     */
    public boolean setOnline() {
        try{
            this.socket = new Socket(ip, port);
            this.socketIn = new DataInputStream(socket.getInputStream());
            this.socketOut = new DataOutputStream(socket.getOutputStream());
            readingThread = asyncReadFromSocket(socketIn);
            online = true;
            System.out.println("Connection established");
        }catch (IOException e){
            setActive(false);
            return false;
        }
        return true;
    }

    /**
     * Send setupper.
     *
     * @param setupper of type SetUp: the setupper.
     */
    public void sendSetupper(SetUp setupper) {
        if(online) {
            try {
                String json = StarterClient.toJson(setupper, SetUp.class);
                socketOut.writeUTF(json);
                socketOut.flush();
                //System.out.println("Sent :" + json);
            } catch (IOException e) {
                e.printStackTrace();
                this.setActive(false);
            }
        }else{
            this.setActive(false);
            synchronized (this){this.notifyAll();}
        }
    }
}
