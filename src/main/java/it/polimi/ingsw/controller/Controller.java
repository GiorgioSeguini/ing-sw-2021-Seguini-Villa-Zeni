package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Market;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.ChoseResourcesException;
import it.polimi.ingsw.model.exception.HaveToChooseException;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.UnableToFillError;

import java.util.ArrayList;

public class Controller {

    /*Quello che ho immaginato è che avendo 4 colonne e 3 righe indextobuy sia un numero da 0 a 6
     * dove da 0 a 3 sono esattamente le 4 colonne e da 4 a 6 siano le tre righe */

    public void BuyFromMarket(int indextobuy, Market market, Player player) {
        ArrayList<MarbleColor> buyedresources = new ArrayList<>();
        NumberOfResources myresources = new NumberOfResources();
        NumberOfResources whiteresources = new NumberOfResources();
        NumberOfResources tosub = new NumberOfResources();


        switch (indextobuy) {
            case 0:
                buyedresources = market.buyColumn(0);
                break;
            case 1:
                buyedresources = market.buyColumn(1);
                break;
            case 2:
                buyedresources = market.buyColumn(2);
                break;
            case 3:
                buyedresources = market.buyColumn(3);
                break;
            case 4:
                buyedresources = market.buyRow(0);
                break;
            case 5:
                buyedresources = market.buyRow(1);
                break;
            case 6:
                buyedresources = market.buyRow(2);
                break;
        }
        try {
            myresources = player.getConverter().convertAll(buyedresources);
        } catch (HaveToChooseException error) {
            /*qui ci deve essere una parte di richiesta alla view di far scegliere al giocatore
             * le risorse bianche nuove dell'abilità che devono essere messe in whiteresources TODO*/
            myresources = player.getConverter().rescueConversion(whiteresources);
        } finally {
            boolean acceptable = false;
            while (!acceptable) {
                try {
                    player.getDepots().addResourcesFromMarket(myresources);
                    acceptable = true;
                } catch (UnableToFillError cannot) {
                    /*qui dobbiamo chiedere al player cosa scartare(sto ipotizzando che quello
                     * che gli torni un numberofresources da scartare*/
                    myresources = myresources.sub(tosub);
                }
            }
        }
    }


    public void mossa2 (  ){
        //compra carta sviluppo
    }

    public void activeProductions(ProductionPower[] toActive, Player player){
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
    }

    /*
    public void mossaleader(  ){

    }
    public ispezionePapale(){

    }

    public void performMove(Classe MOveType){

    }

}

class MoveType{

    Player active;
    View view;
    bool isLastMove;

    //.... scelte
}
*/
}