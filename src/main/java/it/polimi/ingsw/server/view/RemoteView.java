package it.polimi.ingsw.server.view;


import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.controller.*;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.server.parse.Starter;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String info) {
            System.out.println("Received: " + info);
            MoveType move= (MoveType) Starter.fromJson(info, MoveType.class);
            handleMove(move);
        }

    }

    private final ClientConnection clientConnection;

    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
    }

    /*@Override
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }*/

    @Override
    public void update(Message message){
        clientConnection.asyncSend(Starter.toJson(message, Message.class));
    }

}
