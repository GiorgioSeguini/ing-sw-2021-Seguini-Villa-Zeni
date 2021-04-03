package it.polimi.ingsw;

import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.WareHouseDepots;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.UnableToFillError;

import java.util.ArrayList;

public class TestWareHouseDepots {

    public static void main(String[] args){
        WareHouseDepots mywarehouse= new WareHouseDepots(ResourceType.values());
        System.out.print("My shelf: ");
       try {
            for (ResourceType x: ResourceType.values()){
                System.out.print(mywarehouse.getResources().getAmountOf(x));
            }
       }
       catch (NullPointerException e){
           System.out.print("Shelf vuote");
       }

        System.out.print("\nAddRes Test: ");
        NumberOfResources test= new NumberOfResources(1,0,2,2);
       try {
           mywarehouse.addResource(test);
           for (ResourceType x: ResourceType.values()){
               System.out.print(mywarehouse.getResources().getAmountOf(x));
           }

       } catch (UnableToFillError e) {
           System.out.print("Unable to fill error\n");
           System.out.print("My shelf: ");
           for (ResourceType x: ResourceType.values()){
               System.out.print(mywarehouse.getResources().getAmountOf(x));
           }
       }
        System.out.println("\n3typeshlef diff: "+mywarehouse.check_3_shelf_type_Integrity());
    }
}
