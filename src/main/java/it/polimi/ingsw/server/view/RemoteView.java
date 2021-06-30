package it.polimi.ingsw.server.view;


import it.polimi.ingsw.constant.message.LastMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.controller.MoveAutoPlay;
import it.polimi.ingsw.server.controller.Performable;
import it.polimi.ingsw.server.network.ClientConnection;
import it.polimi.ingsw.server.network.Settable;
import it.polimi.ingsw.server.network.SocketClientConnection;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;

/**
 * Remote View class.
 */
public class RemoteView extends View {

    /**
     * Private internal class that menages the new message received from the client.
     */
    private class MessageReceiver implements Observer<String> {

        /**
         * Tries to perform a MoveType. If it cannot tryes to handle a Setupper.
         * @param info
         */
        @Override
        public void update(String info) {
            System.out.println("Received: " + info);
            try{
                Performable move= Starter.fromJson(info, Performable.class);
                handleMove(move);
            }catch (NullPointerException e){
                Settable setupper= Starter.fromJson(info,Settable.class);
                SocketClientConnection connection= (SocketClientConnection)clientConnection;
                connection.handleSetupper(setupper);
            }
        }

    }

    private ClientConnection clientConnection;

    /**
     * Default constructor. It makes the ClientConnection observable for the internal private class.
     * @param player
     * @param c
     */
    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
    }

    /**
     * Sets a new clientConnection. It makes the ClientConnection observable for the internal private class.
     * @param clientConnection
     */
    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        clientConnection.addObserver(new MessageReceiver());
    }

    /**
     * Sends a class Message to the client to updates its model. When the game is offline notifies a MoveAutoPlay
     * @param message
     */
    @Override
    public void update(Message message){
        if(!this.isOffline()){
            clientConnection.send(Starter.toJson(message, Message.class));
        }else{
            if(message instanceof LastMessage){
                notify(new MoveAutoPlay(getPlayer().getID()));
            }
        }
    }

}
