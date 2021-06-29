package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;

import java.util.ArrayList;
import java.util.Map;

/**
 * Requirements class.
 * Superclass of RequirementsExt.
 * We consider a player to match the requirements if it's own a card of the required color with a level equal or HIGHER than the required
 */
public class Requirements {

    private final NumberOfResources numberOfResources;
    private final int [][] minNumber;


    /**
     * Instantiates a new Requirements.
     *
     * @param resources of type NumberOfResources: the resources required.
     * @param requirements of type ArrayList<Map.Entry<ColorDevCard, Level>>: the number and type of card required.
     */
    /*Default constructor*/
    public Requirements(NumberOfResources resources, ArrayList<Map.Entry<ColorDevCard, Level>> requirements){
        numberOfResources = resources;
        minNumber = new int[ColorDevCard.values().length][Level.values().length];
        for(Map.Entry<ColorDevCard, Level> entry : requirements){
            minNumber[entry.getKey().ordinal()][entry.getValue().ordinal()]++;
        }
    }

    /**
     * Instantiates an empty Requirements.
     */
    public Requirements(){
        this(new NumberOfResources(), new ArrayList<>());
    }

    /*Getter*/

    /**
     * Gets the cards required.
     *
     * @return of type int[][]: the cards required.
     */
    public int[][] getMinNumber() {
        return minNumber;
    }

    /**
     * Gets the resources required.
     *
     * @return of type NumberOfResources: the resources required.
     */
    public NumberOfResources getNumberOfResources() {
        return numberOfResources;
    }


    /**
     * Gets the required number of that specific card.
     *
     * @param color of type ColorDevCard: the color.
     * @param level of type Level: the level.
     * @return of type int: the required number of that specific card.
     */
    public int getReq(ColorDevCard color, Level level) {
        return minNumber[color.ordinal()][level.ordinal()];
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o is equals to this or if o.numberOfResources is equals to o.numberOfResources.
     *         False if param o isn't an instance of Requirements or o.minNumber[i][j] isn't equals to this.minNumber[i][j].
     *         False if o.numberOfResources isn't equals to o.numberOfResources.
     */
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

        return this.numberOfResources.equals(other.numberOfResources);
    }

    @Override
    public String toString(){

        String req = "";
        if(!getNumberOfResources().equals(new NumberOfResources())){
            req += "Number of Resources: \n";
            req += "\t"+ getNumberOfResources();
        }

        boolean check=false;
        for(int i = 0; i< ColorDevCard.values().length && !check; i++) {
            for (int j = 0; j < Level.values().length && !check; j++) {
                if (this.minNumber[i][j] > 0) {
                    check=true;
                }
            }
        }

        if(check){
            req += "Number or Type of Development card: \n";
            for(int i = 0; i< ColorDevCard.values().length; i++) {
                for (int j = 0; j < Level.values().length; j++) {
                    if (this.minNumber[i][j] > 0) {
                        req += "\t" + minNumber[i][j];
                        req += "\t" + ColorDevCard.values()[i];
                        req += "\tLevel: " + Level.values()[j] + "\n";
                    }
                }
            }
        }

        req += "\n";
        return req;
    }

}