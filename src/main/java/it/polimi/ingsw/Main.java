package it.polimi.ingsw;

import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.enumeration.ResourceType;

public class Main {
    public void main(){
        NumberOfResources test= new NumberOfResources();
        for (ResourceType x: ResourceType.values()){
            System.out.print(test.getAmountOf(x));
        }
    }
}
