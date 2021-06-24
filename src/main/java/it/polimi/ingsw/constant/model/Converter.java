package it.polimi.ingsw.constant.model;

/*Las Edit: William Zeni*/

import it.polimi.ingsw.constant.enumeration.ResourceType;
import java.util.ArrayList;

public class Converter {

    private final int ownerId;
    private NumberOfResources inwait;
    private final ArrayList<ResourceType> toconvert;
    private int white;

    /*Default Constructor*/
    public Converter(int ownerId){
        this.toconvert=new ArrayList<>();
        toconvert.add(ResourceType.Coins); // TODO: 6/23/21 remove it just for some graphical testing
        toconvert.add(ResourceType.Servants);
        this.ownerId=ownerId;
        this.inwait=new NumberOfResources();
        this.white=0;
    }

    /*Getter*/

    public ArrayList<ResourceType> getToconvert() {
        return toconvert;
    }

    public void addToconvert(ResourceType type){
        this.toconvert.add(type);
        if(toconvert.size()>2) throw new IllegalArgumentException();
    }

    /**This method returns the Resources stored in Converter and it cleans them/. */
    public NumberOfResources getResources(){
       return inwait;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setResources(NumberOfResources resources){
      this.inwait=resources;
    }

    public boolean isWhiteAbilityActive(){
        return toconvert.size()>0;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }
}
