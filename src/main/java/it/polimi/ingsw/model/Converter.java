package it.polimi.ingsw.model;

/*Las Edit: William Zeni*/

import java.util.ArrayList;

public class Converter {

    boolean WhiteAbilityActive=false;

    public boolean getWhiteAbility(){
        return this.WhiteAbilityActive;
    }

    public void setWhiteAbility(){
        this.WhiteAbilityActive=true;
    }

    public ResourceType convert(MarbleColor toconvert, Player player) throws HaveToChooseException{
        switch (toconvert) {
            case Blue: return ResourceType.Shields;
            case Grey: return ResourceType.Stones;
            case Purple: return ResourceType.Servants;
            case Yellow: return ResourceType.Coins;
            case White:
                if (WhiteAbilityActive) {
                    throw new HaveToChooseException();
                } else {
                    return null;
                }
            case Red: return null;
            default: return null;
        }
    }
}
