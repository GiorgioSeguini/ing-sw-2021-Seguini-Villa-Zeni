package it.polimi.ingsw;

import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.WareHouseDepots;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.UnableToFillException;

public class TestWareHouseDepots {

    public static void main(String[] args){
        WareHouseDepots mywarehouse= new WareHouseDepots();
        //mywarehouse.print();
        Shelf shelf4= new Shelf(ResourceType.Stones,2);
        Shelf shelf5= new Shelf(ResourceType.Coins,2);
        mywarehouse.addExtraShelf(shelf4);
        mywarehouse.addExtraShelf(shelf5);
        //mywarehouse.print();

        System.out.print("\nAddRes Test: ");
        NumberOfResources test= new NumberOfResources(0,1,0,1);
        NumberOfResources test2= new NumberOfResources(0,0,1,3);
        NumberOfResources test3= new NumberOfResources(1,0,0,1);
       try {
           mywarehouse.addResource(test);
           for (ResourceType x: ResourceType.values()){
               System.out.print(mywarehouse.getResources().getAmountOf(x));
           }

       } catch (UnableToFillException e) {
           System.out.print("Unable to fill error\n");
           System.out.print("My shelf: ");
           for (ResourceType x: ResourceType.values()){
               System.out.print(mywarehouse.getResources().getAmountOf(x));
           }
       }
        //System.out.println("\n3typeshlef diff: "+mywarehouse.check_3_shelf_type_Integrity());
        //mywarehouse.print();
        System.out.print("\nAddRes Test: ");
        try{
            mywarehouse.addResource(test2);
            for (ResourceType x: ResourceType.values()){
                System.out.print(mywarehouse.getResources().getAmountOf(x));
            }

        } catch (UnableToFillException e) {
            System.out.print("Unable to fill error\n");
            System.out.print("My shelf: ");
            for (ResourceType x: ResourceType.values()){
                System.out.print(mywarehouse.getResources().getAmountOf(x));
            }
        }
       // mywarehouse.print();

        System.out.print("\nSubRes Test: ");
        try{
            mywarehouse.subResource(test3);
            for (ResourceType x: ResourceType.values()){
                System.out.print(mywarehouse.getResources().getAmountOf(x));
            }

        } catch (OutOfResourcesException e) {
            System.out.print("Unable to sub\n");
            System.out.print("My shelf: ");
            for (ResourceType x: ResourceType.values()){
                System.out.print(mywarehouse.getResources().getAmountOf(x));
            }
        }

        //System.out.println("\n3typeshlef diff: "+mywarehouse.check_3_shelf_type_Integrity());
        //mywarehouse.print();
        /*
        mywarehouse.CleanShelf();
        System.out.print("My shelf: ");
        for (ResourceType x: ResourceType.values()){
            System.out.print(mywarehouse.getResources().getAmountOf(x));
        }
        System.out.println("\n3typeshlef diff: "+mywarehouse.check_3_shelf_type_Integrity());
       // mywarehouse.print();
        Shelf x= new Shelf(2);
        Shelf y= new Shelf(ResourceType.Coins,2);
        mywarehouse.addExtraShelf(x);
        mywarehouse.addExtraShelf(y);
        //mywarehouse.print();


        //la prima unable to fill non funziona, 3shelftype ritorna false no sense, gli atrli casi funzionano
        public void print(){
        System.out.println("\nWareHouse");
        for (Shelf layer: shelfs){
            System.out.println("\nShelf");
            System.out.print(("ResourceType: "+layer.getResType()));
            System.out.print(("\tItems: "+layer.getUsed()));
            System.out.print(("\tMaxSize: "+layer.getMaxSize()));
            System.out.print(("\tIs extra: "+layer.getIsExtra()));
        }
    }//just for testing

     public WareHouseDepots(ResourceType[] type){
        for (int i=0; i<3; i++){
            shelfs.add(new Shelf(type[i],i+1));
        }
    }//just for testing


*/

    }


}
