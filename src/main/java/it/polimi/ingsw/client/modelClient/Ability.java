package it.polimi.ingsw.client.modelClient;

/*Last Edit: Gio*/

import it.polimi.ingsw.client.modelClient.enumeration.AbilityType;
import it.polimi.ingsw.client.modelClient.enumeration.ResourceType;

public class Ability {

    private ResourceType resource;
    private AbilityType abilityType;

    public Ability(ResourceType resource, AbilityType abilityType) {
        this.resource = resource;
        this.abilityType = abilityType;
    }

    public ResourceType getResource() {
        return resource;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof Ability))
            return false;

        Ability other = (Ability) o;

        if(other.getAbilityType()!=this.getAbilityType())
            return false;

        return other.getResource()==this.getResource();
    }

}