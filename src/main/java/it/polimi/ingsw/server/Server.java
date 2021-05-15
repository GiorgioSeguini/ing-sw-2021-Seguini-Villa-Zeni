package it.polimi.ingsw.server;

import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
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
            PlayerExt player1 = new PlayerExt(keys.get(0));
            PlayerExt player2 = new PlayerExt(keys.get(1));

            //instance of a new game
            ArrayList<MarbleColor> marble;
            ArrayList<DevelopmentCardExt> developmentCards;
            ArrayList<SoloActionTokens> tokens;
            ArrayList<LeaderCardExt> leaderCards;
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
            ArrayList<PlayerExt> players = new ArrayList<>(); players.add(player1); players.add(player2);
            GameExt game = new GameExt(players, new MarketExt(marble), new DashboardExt(developmentCards), tokens, leaderCards);

            Controller controller = new Controller(game);

            ArrayList<View> playersView =new ArrayList<View>();
            playersView.add(new RemoteView(player1,  c1));
            playersView.add(new RemoteView(player2,  c2));

            for(View view : playersView){
                //add model - view links
                ((MarketExt)game.getMarketTray()).addObserver(view);
                ((DashboardExt)game.getDashboard()).addObserver(view);
                game.addObserver(view);
                for(Player player : game.getPlayers()){
                    ((PersonalBoardExt)player.getPersonalBoard()).addObserver(view);
                    ((FaithTrackExt)player.getFaithTrack()).addObserver(view);
                    ((DepotsExt)player.getDepots()).addObserver(view);
                    ((PlayerExt)player).addObserver(view);
                }

                //add controller - view links
                view.addObserver(controller);
            }

            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();

            //send initial message
            for(View view : playersView){
                view.sendInitialMessage(game);
            }
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
