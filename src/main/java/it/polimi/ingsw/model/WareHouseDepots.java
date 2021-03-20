package it.polimi.ingsw.model;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.util.*;


public class WareHouseDepots {

    private Shelf[] shelfs= new Shelf[3];

    /**Default constructor*/
    public WareHouseDepots() {
        for(int i=0; i<3; i++){
            shelfs[i]=new Shelf();
            shelfs[i].setMaxSize(i+1);
        }
    }

    /**Getter*/
    public NumberOfResources getResources() {
        return null;
    }

    /**Additional Methods*/
    public void addResource(NumberOfResources input) {
        for(Shelf x: shelfs){
            if(input.getAmountOf(x.getResType())>0){
                x.add(input.getAmountOf(x.getResType()));
            }
            else{
                System.out.println("Type of resources not found.");
            }
        }

    }/** This method add the resources to all the shelf if it found them. */

    public void subResource(NumberOfResources required) {
        for(Shelf x: shelfs){
            if(required.getAmountOf(x.getResType())>0){
                x.sub(required.getAmountOf(x.getResType()));
            }
            else{
                System.out.println("Type of resources not found.");
            }
        }
    }

    public boolean canAdd(NumberOfResources input){

        return false;
    }

}