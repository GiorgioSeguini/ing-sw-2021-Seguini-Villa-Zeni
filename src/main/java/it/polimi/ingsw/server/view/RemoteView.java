package it.polimi.ingsw.server.view;


import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.controller.*;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String info) {
            System.out.println("Received: " + info.toString());

            //JSONObject movex=(JSONObject) info.get("Movetype");
            MoveType move=null;
            /*
            switch (movex.toString()){
                case "MoveActiveProduction": move= new MoveActiveProduction(getPlayer(), Starter.getProdArrayfromObjArray((JSONArray) info.get("ProductionPowers"))); break;
                case "MoveBuyDevCard": move= new MoveBuyDevCard(getPlayer(),(int) info.get("Position"),(int) info.get("CardToBuy")) ;break;
                case "MoveChoseInitialResources": move=new MoveChoseInitialResources(getPlayer(),Starter.ConvertObjectToNumOfRes((JSONObject) info.get("Resources"))); break;
                case "MoveChoseLeaderCards": move=new MoveChoseLeaderCards(getPlayer(),(ArrayList<Integer>) info.get("LeaderCards")); break;
                case "MoveChoseResources": move= new MoveChoseResources(getPlayer(),Starter.ConvertObjectToNumOfRes((JSONObject) info.get("ResourcesIn")),Starter.ConvertObjectToNumOfRes((JSONObject) info.get("ResourcesOut")));break;
                case "MoveEndTurn": move= new MoveEndTurn(getPlayer()); break;
                case "MovetypeMarket": move = new MovetypeMarket(getPlayer(), (int) info.get("IndexToBuy")); break;
                case "MoveWhiteConversion": move= new MoveWhiteConversion(getPlayer(),Starter.getResArrayFromObjArray((JSONArray) info.get("WhiteResources"))); break;
                default: clientConnection.asyncSend("Error!"); return;
            }

             */

            handleMove(move);
        } // TODO: 5/5/21 implementare MoveTypeSerializer 

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

    @Override
    public void update(Message message){
        //TODO json conversion
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
