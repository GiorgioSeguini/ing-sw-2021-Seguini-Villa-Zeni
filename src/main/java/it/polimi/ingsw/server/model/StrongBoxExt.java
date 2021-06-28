package it.polimi.ingsw.server.model;


import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.StrongBox;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

/**
 * StrongBoxExt class.
 * Extends StrongBox.
 * Manage the strong box and its resources.
 */
public class StrongBoxExt extends StrongBox {

    /*Additional Methods*/

    /**
     * This method adds resources to the strongbox.
     * @param input of type NumberOfResources: the resources to add.
     */
    public void addResource(NumberOfResources input) {
        super.setResources(getResources().add(input));
    }

    /**
     * This method subs resources to the strongbox.
     * @param required of type NumberOfResources: the resources that has to be subtracted.
     *
     * @throws OutOfResourcesException the out of resources exception
     */
    public void subResource(NumberOfResources required) throws OutOfResourcesException {
        NumberOfResources new_resources = getResources().sub(required);   //may throw n exception if sub is impossible
        super.setResources(new_resources);
    }
}