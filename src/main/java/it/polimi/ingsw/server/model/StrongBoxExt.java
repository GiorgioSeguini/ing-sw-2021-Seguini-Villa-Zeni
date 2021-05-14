package it.polimi.ingsw.server.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.StrongBox;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

public class StrongBoxExt extends StrongBox {

    /*Additional Methods*/
    /**This method adds resources to the strongbox*/
    public void addResource(NumberOfResources input) {
        super.setResources(getResources().add(input));
    }

    /**This method subs resources to the strongbox*/
    public void subResource(NumberOfResources required) throws OutOfResourcesException {
        NumberOfResources new_resources = getResources().sub(required);   //may throw n exception if sub is impossible
        super.setResources(new_resources);
    }
}