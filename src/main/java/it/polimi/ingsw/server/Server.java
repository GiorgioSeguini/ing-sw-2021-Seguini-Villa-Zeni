package it.polimi.ingsw.server;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.server.view.RemoteView;
import it.polimi.ingsw.server.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private static final int NUMOFPOSSIBLEGAMES= 4;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final ArrayList<Map<String, ClientConnection>> waitingConnections = new ArrayList<>();
    private final Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();

    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);
        //Iterator<String> iterator = waitingConnection.keySet().iterator();
       // while(iterator.hasNext()){
            //if(waitingConnection.get(iterator.next())==c){
         //       iterator.remove();
            //}
        // TODO: 5/20/21
        //}
    }

    //Wait for another player
    public synchronized void lobby(ClientConnection c, String name, int numofplayer){

        if(numofplayer<1 || numofplayer>4){
            throw  new IllegalArgumentException();
        }
        waitingConnections.get(numofplayer-1).put(name,c);

        if(waitingConnections.get(0).size()>=1){
            // TODO: 5/20/21 start lorenzo solo game
        }
        else{
            boolean check=true;
            for(int i=1;i<NUMOFPOSSIBLEGAMES && check; i++ ){

                if (waitingConnections.get(i).size()>=i+1) {
                    check=false;
                    ArrayList<PlayerExt> players=getPlayersforGame(waitingConnections.get(i),i+1);
                    GameExt game = null;

                    try {
                        game=new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), Starter.TokensParser(), Starter.LeaderCardsParser());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        //TODO
                    }
                    Controller controller = new Controller(game);
                    ArrayList<View> playersView = instanceViews(waitingConnections.get(i),game.getPlayers());

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

                    ArrayList<ClientConnection> connections= getConnectionforGame(waitingConnections.get(i), game.getPlayers());
                    for(int j=0;j<connections.size();j++){
                        for (int k=0;k<connections.size();k++){
                            if(j!=k){
                                playingConnection.put(connections.get(j),connections.get(k));
                            }
                        }
                    }

                    waitingConnections.get(i).clear();

                    //send initial message
                    for(View view : playersView){
                        view.sendInitialMessage(game);
                    }
                }

            }

        }

    }

    private ArrayList<View> instanceViews(Map<String, ClientConnection> waitingConnection, ArrayList<Player> players) {
        ArrayList<View> playersView =new ArrayList<>();
        for(Player player: players){
            playersView.add(new RemoteView(player,waitingConnection.get(player.getUserName())));
        }
        return playersView;
    }

    private ArrayList<ClientConnection> getConnectionforGame(Map<String, ClientConnection> waitingConnection, ArrayList<Player> players) {
        ArrayList<ClientConnection> connections= new ArrayList<>();
        for (Player player: players){
            connections.add(waitingConnection.get(player.getUserName()));
        }
        return connections;
    }

    private ArrayList<PlayerExt> getPlayersforGame(Map<String, ClientConnection> waitingConnection, int typeconnection) {
        ArrayList<PlayerExt> players= new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>(waitingConnection.keySet());
        for (int i=0; i<typeconnection; i++){
            players.add(new PlayerExt(keys.get(i)));
        }
        return players;
    }

    private void startLorenzoGame(){
        // TODO: 5/20/21
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        for(int i=0; i<NUMOFPOSSIBLEGAMES;i++){
            waitingConnections.add(new HashMap<>());
        }
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
