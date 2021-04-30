package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Starter;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.enumeration.MarbleColor;
import it.polimi.ingsw.server.view.RemoteView;
import it.polimi.ingsw.server.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private final Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();

    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }

    //Wait for another player
    public synchronized void lobby(ClientConnection c, String name){
        waitingConnection.put(name, c);
        if (waitingConnection.size() == 2) {
            ArrayList<String> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            Player player1 = new Player(keys.get(0));
            Player player2 = new Player(keys.get(1));

            //instance of a new game
            ArrayList<MarbleColor> marble;
            ArrayList<DevelopmentCard> developmentCards;
            ArrayList<SoloActionTokens> tokens;
            ArrayList<LeaderCard> leaderCards;
            try {
                 marble = Starter.MarblesParser();
                developmentCards = Starter.DevCardParser();
                tokens = Starter.TokensParser();
                leaderCards = Starter.LeaderCardsParser();
            }catch(Exception e){
                e.printStackTrace();
                //TODO
                return;
            }
            ArrayList<Player> players = new ArrayList<>(); players.add(player1); players.add(player2);
            Game game = new Game(players, new Market(marble), new Dashboard(developmentCards), tokens, leaderCards);
            View player1View = new RemoteView(player1,  c1);
            View player2View = new RemoteView(player2,  c2);

            Controller controller = new Controller(game);


            /*game.addObserver(player1View);
            game.addObserver(player2View);*/
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();

        }
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

}
