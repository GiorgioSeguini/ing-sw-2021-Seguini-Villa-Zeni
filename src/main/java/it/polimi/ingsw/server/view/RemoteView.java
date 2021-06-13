package it.polimi.ingsw.server.view;


import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.controller.*;
import it.polimi.ingsw.server.network.Settable;
import it.polimi.ingsw.server.network.SocketClientConnection;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.network.ClientConnection;
import it.polimi.ingsw.server.parse.Starter;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String info) {
            System.out.println("Received: " + info);
            try{
                Performable move= (Performable) Starter.fromJson(info, Performable.class);
                handleMove(move);
            }catch (NullPointerException e){
                Settable setupper=(Settable) Starter.fromJson(info,Settable.class);
                SocketClientConnection connection= (SocketClientConnection)clientConnection;
                connection.handleSetupper(setupper);
            }
        }

    }

    private ClientConnection clientConnection;


    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    /*@Override
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }*/

    @Override
    public void update(Message message){
        if(!this.isOffline()){
            clientConnection.send(Starter.toJson(message, Message.class));
        }
    }

}
