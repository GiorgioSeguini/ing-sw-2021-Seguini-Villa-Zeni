package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.*;
import org.graalvm.compiler.lir.aarch64.AArch64Move;


import java.util.ArrayList;

public class Controller{

    private Game game;

    /*Quello che ho immaginato è che avendo 4 colonne e 3 righe indextobuy sia un numero da 0 a 6
     * dove da 0 a 3 sono esattamente le 4 colonne e da 4 a 6 siano le tre righe */

    public void BuyFromMarket(int indextobuy, Game game, Player player){
        ArrayList<MarbleColor> buyedresources = new ArrayList<>();

        /*Compro la colonna o la riga corretta*/
        switch (indextobuy) {
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
        }

        try {
            player.getConverter().convertAll(buyedresources);
        }
        catch (HaveToChooseException error) {
            ArrayList<ResourceType> whiteresources=new ArrayList<>();
            /*qui ci deve essere una parte di richiesta alla view di far scegliere al giocatore
             * le risorse bianche nuove dell'abilità che devono essere messe in whiteresources.
             * Il numero di biglie bianche da convertire è raccolta nell'eccezione.
             * Mi aspetto che si riempia una MoveType per l'occasione TODO*/

            /*biglie da convertire= */error.getWhitemarbles();
            player.getConverter().WhiteMarbleConverter(whiteresources);
        }
        NumberOfResources myresources=player.getConverter().getResourcesAndClean();
        /*Finito questo blocco ho le biglie convertite in risorse. Inoltre il converter è pulito*/

        boolean acceptable = false;
        NumberOfResources tosub=new NumberOfResources();
        while (!acceptable) {
            try {
                player.getDepots().addResourcesFromMarket(myresources);
                acceptable = true;
            } catch (UnableToFillException error) {
                /*qui dobbiamo chiedere al player cosa scartare(sto ipotizzando che quello
                 * che gli torni sia un numberofresources da scartare tosub*/
                boolean acceptable2 = false;
                while (!acceptable2) {
                    try {
                        myresources = myresources.sub(tosub);
                        acceptable2 = true;
                    } catch (OutOfResourcesException e) {
                        /*qui dobbiamo dire al player che sta provando a scartare più risorse di quello che ha.
                         * Potremmo chiedergli se sta tentando di scartare tutto. Se si allora si pone tosub=myresources.
                         * Se no bisogna fargli sistemare la tosub*/
                    }
                }
            }
            if (!tosub.isEmpty()) {
                for (int i = 0; i < tosub.size(); i++) {
                    for (Player x : game.getPlayers()) {
                        if (player != x) {
                            x.getFaithTrack().addPoint();
                        }
                    }
                    game.popesInspection();
                }
            }
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


