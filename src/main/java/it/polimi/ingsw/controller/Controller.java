package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.*;


import java.util.ArrayList;

public class Controller{

    private Game game;

    /*Quello che ho immaginato è che avendo 4 colonne e 3 righe indextobuy sia un numero da 0 a 6
     * dove da 0 a 3 sono esattamente le 4 colonne e da 4 a 6 siano le tre righe */

    /**This method returns the marbles bought from the market*/
    private ArrayList<MarbleColor> TakeMarbles(Player player,MovetypeMarket movetype){
        ArrayList<MarbleColor> buyedresources;

        /*Compro la colonna o la riga corretta*/
        switch (movetype.getIndextobuy()) {
            case 0:
                buyedresources = game.getMarketTray().buyColumn(0);
                break;
            case 1:
                buyedresources = game.getMarketTray().buyColumn(1);
                break;
            case 2:
                buyedresources = game.getMarketTray().buyColumn(2);
                break;
            case 3:
                buyedresources = game.getMarketTray().buyColumn(3);
                break;
            case 4:
                buyedresources = game.getMarketTray().buyRow(0);
                break;
            case 5:
                buyedresources = game.getMarketTray().buyRow(1);
                break;
            case 6:
                buyedresources = game.getMarketTray().buyRow(2);
                break;
            default: return null;
        }
        return buyedresources;
    }

    /**This method tries to convert the Marble as the player asked. If it can it makes the conversion
     * and stores the converted resources in the Converter Class. Returns TRUE if the conversion ends correctly,
     * returns FALSE if it doesn't.*/
    private boolean ConvertFromMarket(Player player, ArrayList<MarbleColor> buyedresources, MovetypeMarket movetype){
        try {
            player.getConverter().convertAll(buyedresources);
        }
        catch (HaveToChooseException error) {
            return false;
        }
        return true;
    }

    /**This method converts the white marbles. It returns TRUE if it ends correctly.*/
    private boolean ConvertJustWhites(Player player, MovetypeMarket movetype){
        if(player.getConverter().CheckIntegrityToConvert(movetype.getWhitemarbles())){
            player.getConverter().WhiteMarbleConverter(movetype.getWhitemarbles());
        }
        else{
            return false;
        }
        return true;
    }

    /**This method tries to store the resources from converter to warehouse. If it can returns TRUE */
    private boolean StoreFromMarket(Player player){
        try{
            player.getDepots().addResourcesFromMarket(player.getConverter().getResources());
            return true;
        }
        catch (UnableToFillException e) {
            return false;
        }
    }

    /**This method discard the resources in converter choosen from the player*/
    private boolean DiscardMarketResources(Player player, MovetypeMarket movetype){
        try{
            player.getConverter().setResources(player.getConverter().getResources().sub(movetype.getTodiscard()));
            for(Player x: game.getPlayers()){
              if(x!=player){
                //TODO finish
              }
            }
            return true;
        }
        catch (OutOfResourcesException e) {
            return false;
        }
    }

    public void buyDevelopmentCard(DevelopmentCard cardtobuy, Game game, Player player, int pos){

        ArrayList<DevelopmentCard>[] cardsOwned = player.getPersonalBoard().getOwnedDevCards();

        if(!game.getDashboard().getTopDevCard(cardtobuy.getColor(),cardtobuy.getLevel()).equals(cardtobuy)){
            //la carta che il player vuole comprare non è la prima della pila quindi deve sceglierne un'altra
            //TODO ERROR MESSAGE
            return;
        }

        NumberOfResources realCost = cardtobuy.getCost().safe_sub(player.getDiscount());
        if (player.getDepots().match(realCost)) {
            try {
                player.getPersonalBoard().addDevCard(cardtobuy,pos);
            } catch (NoSpaceException e) {
                //TODO ERROR MESSAGE
                return;
            }
            game.getDashboard().buyDevCard(cardtobuy.getColor(),cardtobuy.getLevel());
            try {
                player.getDepots().subResource(realCost);
            } catch (OutOfResourcesException ignored) {}
        } else{
            //qui bisogna dire al player che non può comprare quella carta perchè non ha abbastazna risorse e quindi di sceglierne un'altra
            //TODO ERROR MESSAGE
        }

    }

    public void activeProductions(ProductionPower[] toActive, Player player, Game game){
        //check if current player really own the productionPowers that want to active
        ArrayList<ProductionPower> productionOwned = player.getPersonalBoard().getProduction();

        for(ProductionPower p : toActive)
            if(!productionOwned.contains(p))
                throw new IllegalArgumentException();

        //sum all productionPower
        ProductionPower total = new ProductionPower();
        for(ProductionPower p : toActive)
            total = total.add(p);

        try {
            total.active(player);
        }catch(ChoseResourcesException e){
            //TODO
        }catch(OutOfResourcesException e){
            //TODO something different
        }
        game.popesInspection();
    }

    public void leaderMove(LeaderCard card, Player player, int move){
        boolean isPresent = false;
        try {
            for(LeaderCard c : player.getPersonalBoard().getLeaderCards())
                if(c.equals(card)) {
                    isPresent = true;
                    card = c;
                }
        } catch (NoMoreLeaderCardAliveException e) {
            //Il player non ha più carte leader in mano
            //TODO ERROR MESSAGE
        }

        if(!isPresent){
            //TODO ERROR MESSAGE
            return;
        }

        if(move==0){
            if(!card.setPlayed(player)){
                //TODO ERROR MESSAGE
                //return not strictly needed
            }
        }

        if(move==1)
            if(!card.setDiscard(player)){
                //TODO ERROR MESSAGE
                //return not strictly needed
            }
    }

    public void update(MoveType x){

        if(!x.player.equals(game.getCurrPlayer())){
            //TODO error message
            return;
        }

        if(x instanceof  MoveBuyDevCard){
            MoveBuyDevCard move = (MoveBuyDevCard) x;
            buyDevelopmentCard(move.cardToBuy, game, move.player, move.getPos());
        }

        if(x instanceof  MoveActiveProduction){
            MoveActiveProduction move = (MoveActiveProduction) x;
            activeProductions(move.toActive, move.player, game);
        }

        if(x instanceof MoveLeader){
            MoveLeader move = (MoveLeader) x;
            leaderMove(move.leaderCard, move.player, move.move);
        }

    }
}
