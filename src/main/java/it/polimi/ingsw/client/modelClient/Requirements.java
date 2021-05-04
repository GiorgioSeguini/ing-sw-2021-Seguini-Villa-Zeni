package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;

import java.util.ArrayList;
import java.util.Map;
/*Last Edit: Gio*/

/**
 * We consider a player to match the requirements if it's own a card of the required color with a level equal or HIGHER than the required
 */
public class Requirements {

    private final NumberOfResources numberOfResourceses;
    private final int [][] minNumber;


    /*Default constructor*/
    public Requirements(NumberOfResources resources, ArrayList<Map.Entry<ColorDevCard, Level>> requirements){
        numberOfResourceses = resources;
        minNumber = new int[ColorDevCard.values().length][Level.values().length];
        for(Map.Entry<ColorDevCard, Level> entry : requirements){
            minNumber[entry.getKey().ordinal()][entry.getValue().ordinal()]++;
        }
    }

    public Requirements(){
        this(new NumberOfResources(), new ArrayList<>());
    }

    public Requirements(NumberOfResources resources){
        this(resources, new ArrayList<>());
    }

    public Requirements(ArrayList<Map.Entry<ColorDevCard, Level>> requirements){
        this(new NumberOfResources(), requirements);
    }





    /*Getter*/

    public int[][] getMinNumber() {
        return minNumber;
    }

    public NumberOfResources getNumberOfResourceses() {
        return numberOfResourceses;
    }


    public int getReq(ColorDevCard color, Level level) {
        return minNumber[color.ordinal()][level.ordinal()];
    }


    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof Requirements))
            return false;

        Requirements other = (Requirements) o;

        for(int i = 0; i< ColorDevCard.values().length; i++)
            for(int j = 0; j< Level.values().length; j++)
                if(this.minNumber[i][j]!=other.minNumber[i][j])
                    return false;

        return this.numberOfResourceses.equals(other.numberOfResourceses);
    }

}