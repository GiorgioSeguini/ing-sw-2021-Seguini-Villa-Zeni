package it.polimi.ingsw.server.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

public class StrongBox {

    private NumberOfResources resources;

    /* Default constructor*/
    StrongBox() {
        resources=new NumberOfResources();
    }

    /*Getter*/
    public NumberOfResources getResources() {
        return resources;
    }

    /*Additional Methods*/
    /**This method adds resources to the strongbox*/
    public void addResource(NumberOfResources input) {
        resources = resources.add(input);
    }

    /**This method subs resources to the strongbox*/
    public void subResource(NumberOfResources required) throws OutOfResourcesException {
        NumberOfResources new_resources = resources.sub(required);   //may throw n exception if sub is impossible
        resources = new_resources;
    }

    @Override
    public String toString(){
        String res="Strong Box\n";

        res+=getResources();
        return res;
    }
}