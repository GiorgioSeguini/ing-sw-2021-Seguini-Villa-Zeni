package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.exception.OutOfResourcesException;

import java.util.*;
/*Last Edit: Gio*/

/**
 * We consider a player to match the requirements if it's own a card of the required color with a level equal or HIGHER than the required
 */
public class Requirements {

    private final NumberOfResources numberOfResourceses;
    private final int [][] minNumber;


    /*Default constructor*/
    Requirements() {
        numberOfResourceses = new NumberOfResources(0,0,0,0);
        minNumber = new int[ColorDevCard.values().length][Level.values().length];
    }

    /*Getter*/
    public int getReq(ColorDevCard color) {
        //TODO
        return 0;
    }

    public int getReq(ColorDevCard color, Level level) {
        return minNumber[color.ordinal()][level.ordinal()];
    }

    /**
     *
     * @param owner Leader Card's owner
     * @return true iff player can activate the development card
     */
    public boolean match(Player owner) {
        //match for the resources
        NumberOfResources ownedRes = owner.getDepots().getResources();
        try{
            ownedRes.sub(numberOfResourceses);
        }catch(OutOfResourcesException e){
            return false;
        }

        //match for the development card
        PersonalBoard board = owner.getPersonalBoard();
        ArrayList<DevelopmentCard> ownedDevCard= board.getAllDevCard();
        for(ColorDevCard c : ColorDevCard.values()){
            for(Level l: Level.values()){
                int missing = minNumber[c.ordinal()][l.ordinal()];
                if(missing>0){
                    //search for a match
                    int increment =0;
                    while(l.ordinal() + increment < Level.values().length && missing>0) {
                        Level l1 = Level.values()[l.ordinal() + increment];
                        for (DevelopmentCard dev : ownedDevCard) {
                            if (dev.getColor().equals(c) && dev.getLevel().equals(l1)) {
                                ownedDevCard.remove(dev);
                                missing--;
                                if(missing == 0) break;
                            }
                        }
                    }
                    if(missing>0)  return false;
                }
            }
        }

        return true;
    }

}