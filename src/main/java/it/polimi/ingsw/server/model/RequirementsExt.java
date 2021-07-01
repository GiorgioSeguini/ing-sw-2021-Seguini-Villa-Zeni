package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Requirements;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

import java.util.ArrayList;
import java.util.Map;

/**
 * RequirementsExt class.
 * Extends Requirements.
 * Manage the card's requirements.
 * We consider a player to match the requirements if it's own a card of the required color with a level equal or HIGHER than the required
 */
public class RequirementsExt extends Requirements {

    /**
     * Instantiates an empty Requirements ext.
     */
    public RequirementsExt(){
        super();
    }

    /**
     * Instantiates a new Requirements ext with only the number and the type of resources required.
     *
     * @param resources of type NumberOfResources: the resources.
     */
    public RequirementsExt(NumberOfResources resources){
        super(resources, new ArrayList<>());
    }

    /**
     * Instantiates a new Requirements ext with only the number and the type of card required.
     *
     * @param requirements of type ArrayList<Map.Entry<ColorDevCard, Level>>:  the requirements.
     */
    public RequirementsExt(ArrayList<Map.Entry<ColorDevCard, Level>> requirements){
        super(new NumberOfResources(), requirements);
    }

    /**
     * Match the requirements and what the player has.
     *
     * @param owner of type PlayerExt: the player.
     * @return true if the player owns the requirements. Otherwise False.
     */
    public boolean match(PlayerExt owner) {
        //match for the resources
        NumberOfResources ownedRes = owner.getDepots().getResources();
        try{
            ownedRes.sub(getNumberOfResources());
        }catch(OutOfResourcesException e){
            return false;
        }

        //match for the development card
        PersonalBoardExt board = owner.getPersonalBoard();
        ArrayList<DevelopmentCard> ownedDevCard= board.getOwnedDevCards();
        for(ColorDevCard c : ColorDevCard.values()){
            for(Level l: Level.values()){
                int missing = super.getMinNumber()[c.ordinal()][l.ordinal()];
                Level l1 = l;
                while(missing>0){
                    //search for a match
                    ArrayList<DevelopmentCard> toRemove = new ArrayList<>();
                    for (DevelopmentCard dev : ownedDevCard) {
                        if (dev.getColor().equals(c) && dev.getLevel().equals(l1)) {
                            toRemove.add(dev);
                            missing--;
                            if(missing == 0) break;
                        }
                    }
                    ownedDevCard.removeAll(toRemove);
                    try{ l1 = l1.getNext(); }catch(IllegalArgumentException e){break;}
                }
                if(missing>0)  return false;
            }
        }

        return true;
    }

}