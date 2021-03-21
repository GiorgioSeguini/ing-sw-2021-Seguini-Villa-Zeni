package it.polimi.ingsw.model;

import java.util.*;

public class NumberOfResources {
    private int[]resources= new int[4];

    NumberOfResources(int Servants, int Shields, int Coins, int Stones){
       resources[0]=Servants;
       resources[1]=Shields;
       resources[2]=Coins;
       resources[3]=Stones;
    }

    public int getAmountOf(ResourceType type){
        return resources[type.getIndex()];
    }

    public NumberOfResources add(NumberOfResources other){
        int[] x= new int[4];
        for(ResourceType type: ResourceType.values()){
            x[type.getIndex()]=resources[type.getIndex()]+ other.getAmountOf(type);
        }
        NumberOfResources new_resources= new NumberOfResources(x[0],x[1],x[2],x[3]);

        return new_resources;
    }

    public NumberOfResources sub(NumberOfResources other){
        int[] x= new int[4];
        for(ResourceType type: ResourceType.values()){
            if (resources[type.getIndex()]>=other.getAmountOf(type)){
                x[type.getIndex()]=resources[type.getIndex()]- other.getAmountOf(type);
            }
            else{
                //trow exceptions TODO
            }
        }
        NumberOfResources new_resources= new NumberOfResources(x[0],x[1],x[2],x[3]);

        return new_resources;
    }



}