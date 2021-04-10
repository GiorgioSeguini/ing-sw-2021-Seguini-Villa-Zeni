package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.*;


import java.util.ArrayList;

public class Controller{

    private Game game;

    /*Quello che ho immaginato è che avendo 4 colonne e 3 righe indextobuy sia un numero da 0 a 6
     * dove da 0 a 3 sono esattamente le 4 colonne e da 4 a 6 siano le tre righe */

    /**This method returns the marbles bought from the market*/
    private ArrayList<MarbleColor> takeMarbles(int indexToBuy){
        ArrayList<MarbleColor> buyedresources = null;

        /*Compro la colonna o la riga corretta*/
        if(indexToBuy>=0 && indexToBuy<=3)
            buyedresources = game.getMarketTray().buyColumn(indexToBuy);

        if(indexToBuy>=4 && indexToBuy<=6)
            buyedresources = buyedresources = game.getMarketTray().buyRow(indexToBuy - 4);

        if(buyedresources==null);
            //TODO check

        return buyedresources;
    }

    /**This method tries to convert the Marble as the player asked. If it can it makes the conversion
     * and stores the converted resources in the Converter Class. Returns TRUE if the conversion ends correctly,
     * returns FALSE if it doesn't.*/
    private boolean buyFromMarket(Player player, int indexToBuy){
        try {
            player.getConverter().convertAll(takeMarbles(indexToBuy));
        }
        catch (HaveToChooseException error) {
            return false;
        }
        return true;
    }

    /**This method converts the white marbles. It returns TRUE if it ends correctly.*/
    private boolean convertJustWhites(Player player, ArrayList<ResourceType> whiteMarbles){
        if(player.getConverter().CheckIntegrityToConvert(whiteMarbles)){
            player.getConverter().WhiteMarbleConverter(whiteMarbles);
        }
        else{
            return false;
        }
        return true;
    }

    /**This method tries to store the resources from converter to warehouse. If it can returns TRUE */
    private boolean storeFromMarket(Player player){
        try{
            player.getDepots().addResourcesFromMarket(player.getConverter().getResources());
            return true;
        }
        catch (UnableToFillException e) {
            return false;
        }
    }

    /**This method discard the resources in converter chosen from the player*/
    private boolean discardMarketResources(Player player, NumberOfResources toDiscard){
        try{
            player.getConverter().setResources(player.getConverter().getResources().sub(toDiscard));
            for(int i=0; i<toDiscard.size();i++){
              for(Player y: game.getPlayers()){
                if(y!=player){
                  player.getFaithTrack().addPoint();
                }
              }
              game.popesInspection();
            }
            return true;
        }
        catch (OutOfResourcesException e) {
            return false;
        }
    }

    public boolean buyDevelopmentCard(DevelopmentCard cardtobuy, Game game, Player player, int pos){

        ArrayList<DevelopmentCard>[] cardsOwned = player.getPersonalBoard().getOwnedDevCards();

        if(!game.getDashboard().getTopDevCard(cardtobuy.getColor(),cardtobuy.getLevel()).equals(cardtobuy)){
            //la carta che il player vuole comprare non è la prima della pila quindi deve sceglierne un'altra
            //TODO ERROR MESSAGE
            return false;
        }

        NumberOfResources realCost = cardtobuy.getCost().safe_sub(player.getDiscount());
        if (player.getDepots().match(realCost)) {
            try {
                player.getPersonalBoard().addDevCard(cardtobuy,pos);
            } catch (NoSpaceException e) {
                //TODO ERROR MESSAGE
                return false;
            }
            game.getDashboard().buyDevCard(cardtobuy.getColor(),cardtobuy.getLevel());
            try {
                player.getDepots().subResource(realCost);
            } catch (OutOfResourcesException ignored) {}
        } else{
            //qui bisogna dire al player che non può comprare quella carta perchè non ha abbastazna risorse e quindi di sceglierne un'altra
            //TODO ERROR MESSAGE
        }
        return true;
    }

    public boolean choseProductions(ProductionPower[] toActive, Player player, Game game){
        //check if current player really own the productionPowers that want to active
        ArrayList<ProductionPower> productionOwned = player.getPersonalBoard().getProduction();

        for(ProductionPower p : toActive)
            if(!productionOwned.contains(p)) {
                //TODO error Message
                return false;
            }
        //sum all productionPower
        ProductionPower total = new ProductionPower();
        for(ProductionPower p : toActive)
            total = total.add(p);

        player.setToActive(total);

        if(player.isActivable()) {
            player.setToActive(null);
            //TODO error message
            return false;
        }
        return true;
    }

    public boolean activeProductions(Player player, NumberOfResources ofYourChoiceInput, NumberOfResources ofYourChoiceOutput){
        try {
            player.getToActive().active(player, ofYourChoiceInput, ofYourChoiceOutput);
        }catch(ChoseResourcesException e){
            //TODO error message
            return false;
        }catch(OutOfResourcesException e){
            //TODO something different
            return false;
        }
        game.popesInspection();

        return true;
    }

    public boolean leaderMove(LeaderCard card, Player player, int move){
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
            return false;
        }

        if(move==0){
            if(!card.setPlayed(player)){
                //TODO ERROR MESSAGE
                return false;
            }
        }

        if(move==1)
            if(!card.setDiscard(player)){
                //TODO ERROR MESSAGE
                return false;
            }

        return true;
    }

    public void update(MoveType x){

        if(!x.getPlayer().equals(game.getCurrPlayer())){
            //TODO error message
            return;
        }

        /*Production Move*/
        if(x instanceof  MoveBuyDevCard && x.getPlayer().getStatus()==PlayerStatus.Active){
            MoveBuyDevCard move = (MoveBuyDevCard) x;
            buyDevelopmentCard(move.cardToBuy, game, move.getPlayer(), move.getPos());

        }

        if(x instanceof  MoveActiveProduction && x.getPlayer().getStatus()==PlayerStatus.Active){
            MoveActiveProduction move = (MoveActiveProduction) x;
            if(choseProductions(move.toActive, move.getPlayer(), game)){
                if(move.getPlayer().easyActive()){
                    try {
                        move.getPlayer().getToActive().active(move.getPlayer());
                    }catch(ChoseResourcesException | OutOfResourcesException ignored){};
                    move.getPlayer().setStatus(PlayerStatus.MovePerformed);
                }
                else{
                    move.getPlayer().setStatus(PlayerStatus.NeedToCHoseRes);
                }
            }
        }

        if(x instanceof MoveChoseResources && x.getPlayer().getStatus()==PlayerStatus.NeedToCHoseRes) {
            MoveChoseResources move = (MoveChoseResources) x;
            if(activeProductions(move.getPlayer(), move.getOfYourChoiceInput(), move.getOfYourChoiceOutput())){
                move.getPlayer().setStatus(PlayerStatus.MovePerformed);
            }
        }

        if(x instanceof MoveLeader && (x.getPlayer().getStatus()==PlayerStatus.Active || x.getPlayer().getStatus()==PlayerStatus.MovePerformed)){
            MoveLeader move = (MoveLeader) x;
            leaderMove(move.leaderCard, move.getPlayer(), move.move);
        }

        /*Market Move*/
        if(x instanceof MovetypeMarket && x.getPlayer().getStatus()==PlayerStatus.Active){
            MovetypeMarket move = (MovetypeMarket) x;
            if(!buyFromMarket(move.player, move.getIndextobuy())){
                move.getPlayer().setStatus(PlayerStatus.NeedToConvert);
            } else{
                move.getPlayer().setStatus(PlayerStatus.NeedToStore);
            }
        }

        if(x instanceof MoveWhiteConversion && x.getPlayer().getStatus()==PlayerStatus.NeedToConvert){
            MoveWhiteConversion move = (MoveWhiteConversion) x;
            if(!convertJustWhites(move.player, move.getWhitemarbles())){
                move.getPlayer().setStatus(PlayerStatus.NeedToConvert);
                //TODO error message
            } else{
                move.getPlayer().setStatus(PlayerStatus.NeedToStore);
            }
        }

        if(x instanceof  MoveDiscardResources && x.getPlayer().getStatus()==PlayerStatus.NeedToStore){
            MoveDiscardResources move = (MoveDiscardResources) x;
            if(!discardMarketResources(move.player, move.getToDiscard())){
                move.getPlayer().setStatus(PlayerStatus.NeedToStore);
                //TODO error message
            }else{
                if(!storeFromMarket(move.getPlayer())){
                        move.getPlayer().setStatus(PlayerStatus.NeedToStore);
                }else{
                    move.getPlayer().setStatus(PlayerStatus.MovePerformed);
                }
            }
        }

        if(x.isLastMove && x.getPlayer().getStatus()==PlayerStatus.MovePerformed){
            game.nextTurn();
        }
    }
}
