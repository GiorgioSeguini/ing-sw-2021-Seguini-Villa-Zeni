package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Requirements;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

import java.util.*;
/*Last Edit: Gio*/

/**
 * We consider a player to match the requirements if it's own a card of the required color with a level equal or HIGHER than the required
 */
public class RequirementsExt extends Requirements {

    /**
     *
     * @param owner Leader Card's owner
     * @return true iff player can activate the development card
     */
    public boolean match(PlayerExt owner) {
        //match for the resources
        NumberOfResources ownedRes = owner.getDepots().getResources();
        try{
            ownedRes.sub(getNumberOfResourceses());
        }catch(OutOfResourcesException e){
            return false;
        }

        //match for the development card
        PersonalBoardExt board = (PersonalBoardExt) owner.getPersonalBoard();
        ArrayList<DevelopmentCard> ownedDevCard= board.getOwnedDevCards();
        for(ColorDevCard c : ColorDevCard.values()){
            for(Level l: Level.values()){
                int missing = super.getMinNumber()[c.ordinal()][l.ordinal()];
                Level l1 = l;
                while(missing>0){
                    //search for a match
                    for (DevelopmentCard dev : ownedDevCard) {
                        if (dev.getColor().equals(c) && dev.getLevel().equals(l1)) {
                            ownedDevCard.remove(dev);
                            missing--;
                            if(missing == 0) break;
                        }
                    }
                    try{ l1 = l1.getNext(); }catch(IllegalArgumentException e){break;}
                }
                if(missing>0)  return false;
            }
        }

        return true;
    }

}