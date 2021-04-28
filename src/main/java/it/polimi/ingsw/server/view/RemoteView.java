package it.polimi.ingsw.server.view;


import it.polimi.ingsw.server.controller.MoveActiveProduction;
import it.polimi.ingsw.server.controller.MoveBuyDevCard;
import it.polimi.ingsw.server.controller.MoveType;
import it.polimi.ingsw.server.controller.MovetypeMarket;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.ProductionPower;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            JSONParser parser= new JSONParser();
            JSONObject info=null;
            try {
                info=(JSONObject) parser.parse(message);
            } catch (ParseException e) {}

            JSONObject movex=(JSONObject) info.get("Movetype");
            MoveType move;

            switch (movex.toString()){
                case "MoveActiveProduction": move= new MoveActiveProduction(getPlayer(),null); break; // TODO: 4/28/21
                case "MoveBuyDevCard": move= new MoveBuyDevCard(getPlayer(),(int) info.get("Position"),null);break;
                case "MoveChoseInitialResources": break;
                case "MoveChoseLeaderCards": break;
                case "MoveChoseResources": break;
                case "MoveEndTurn": break;
                case "MovetypeMarket": break;
                case "MoveWhiteConversion": break;
                default: clientConnection.asyncSend("Error!"); return;
            }
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
