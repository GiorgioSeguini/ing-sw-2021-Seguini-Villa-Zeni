package it.polimi.ingsw.client.modelClient;

/*Last Edit: Gio*/

import it.polimi.ingsw.client.modelClient.enumeration.ResourceType;

public class DepotsAbility implements Ability {

    public static final int extraSize = 2;
    private final ResourceType typeOfRes;

    /*Default constructor*/
    public DepotsAbility(ResourceType typeOfRes) {
        this.typeOfRes=typeOfRes;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }


    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof DepotsAbility))
            return false;

        DepotsAbility other = (DepotsAbility) o;

        return other.typeOfRes.equals(this.typeOfRes);
    }

}