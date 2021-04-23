package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.MoveActiveProduction;
import it.polimi.ingsw.controller.MoveBuyDevCard;
import it.polimi.ingsw.controller.MoveType;
import it.polimi.ingsw.controller.MovetypeMarket;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductionPower;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
                //TODO parsing
                MoveType move;
                if(message.equals("1")){
                    move= new MoveBuyDevCard(getPlayer(), 0, null);
                }else if(message.equals("2")){
                    move= new MovetypeMarket(getPlayer(), 0);
                }else if(message.equals("3")){
                    move = new MoveActiveProduction(getPlayer(), new ProductionPower[]{new ProductionPower()});
                }else{
                    clientConnection.asyncSend("Error!");
                    return;
                }

                handleMove(move);
        }

    }

    private final ClientConnection clientConnection;

    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
    }

    @Override
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }

    /*
    TODO Metodo per comunicare alla view che il model è cambiato
    @Override
    public void update(MoveMessage message)
    {
        showMessage(message.getBoard());
        String resultMsg = "";
        boolean gameOver = message.getBoard().isGameOver(message.getPlayer().getMarker());
        boolean draw = message.getBoard().isFull();
        if (gameOver) {
            if (message.getPlayer() == getPlayer()) {
                resultMsg = gameMessage.winMessage + "\n";
            } else {
                resultMsg = gameMessage.loseMessage + "\n";
            }
        }
        else {
            if (draw) {
                resultMsg = gameMessage.drawMessage + "\n";
            }
        }
        if(message.getPlayer() == getPlayer()){
            resultMsg += gameMessage.waitMessage;
        }
        else{
            resultMsg += gameMessage.moveMessage;
        }

        showMessage(resultMsg);
    }
    */


}
