package it.polimi.ingsw;

import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.OutOfResourcesException;

public class TestNumberOfResources {
    public static void main(String[] args) throws OutOfResourcesException {
        NumberOfResources test= new NumberOfResources(0,0,4,1);

        System.out.print("My NumberOfResouces: ");
        for (ResourceType x: ResourceType.values()){
            System.out.print(test.getAmountOf(x));
        }
        System.out.println("\nMetodo Size: "+test.size());
        System.out.println("Metodo Isempty: "+ test.IsEmpty());
        System.out.println("Metodo Maxtype: "+test.Max_Resource_Type());
        System.out.print("Metodo clone: ");
        NumberOfResources test2= test.clone();
        for (ResourceType x: ResourceType.values()){
            System.out.print(test2.getAmountOf(x));
        }
        System.out.print("\nMetodo add: ");
        NumberOfResources test3=test.add(new NumberOfResources(1,2,3,4));
        for (ResourceType x: ResourceType.values()){
            System.out.print(test3.getAmountOf(x));
        }
        System.out.print("\nMetodo add con tipo: ");
        NumberOfResources test4=test.add(ResourceType.Coins,4);
        for (ResourceType x: ResourceType.values()){
            System.out.print(test4.getAmountOf(x));
        }
        System.out.print("\nMetodo sub: ");
        try {
            NumberOfResources test5=test.sub(new NumberOfResources(6,1,1,1));
            for (ResourceType x: ResourceType.values()){
                System.out.print(test5.getAmountOf(x));
            }
        }
        catch (OutOfResourcesException e){
            System.out.print("UNABLE TO SUB");
        }

        System.out.print("\nMetodo sub con tipo: ");
        try {
            NumberOfResources test6=test.sub(ResourceType.Coins, 3);
            for (ResourceType x: ResourceType.values()){
                System.out.print(test6.getAmountOf(x));
            }
        }
        catch (OutOfResourcesException e){
            System.out.print("UNABLE TO SUB");
        }



    }
}
