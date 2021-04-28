package it.polimi.ingsw.server.model;

/*Las Edit: William Zeni*/

import it.polimi.ingsw.server.model.enumeration.MarbleColor;
import it.polimi.ingsw.server.model.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.HaveToChooseException;
import java.util.ArrayList;

public class Converter {

    private Player owner;
    private NumberOfResources inwait;
    private ArrayList<ResourceType> toconvert;

    /*Default Constructor*/
    public Converter(Player owner){
        this.toconvert=new ArrayList<>();
        this.owner=owner;
        this.inwait=new NumberOfResources();
    }

    /*Getter*/

    public Player getOwner() {
        return owner;
    }

    public ArrayList<ResourceType> getToconvert() {
        return toconvert;
    }

    /**This method returns the Resources stored in Converter and it cleans them/. */
    public NumberOfResources getResources(){
       return inwait;
    }

    /*Setter*/
    public void setWhiteAbility(ResourceType type){
        for(ResourceType x: toconvert){
            if(type==x){
                throw new IllegalArgumentException();
            }
        }
        this.toconvert.add(type);
    }

    public void setResources(NumberOfResources resources){
      this.inwait=resources;
    }

    /*Additional Methods*/
    /**Returns TRUE if the WhiteAbility is active*/
    public boolean IsWhiteAbilityActive(){
        if(toconvert.size()!=0){
            return true;
        }
        return false;
    }

    /**It converts bought marbles and it throws an exception if there are white marbles to convert. */
    public void convertAll(ArrayList<MarbleColor> input) throws HaveToChooseException{
        ArrayList<MarbleColor>without_white=new ArrayList<>();
        int howmany=0;

        for (MarbleColor x: input){
            if(x!=MarbleColor.WHITE){
                without_white.add(x);
            }
            else{
                howmany++;
            }
        }
        inwait=convert_resources(without_white);
        if(howmany!=0){
            if (toconvert.size()>1){
                throw new HaveToChooseException();
            }
            else{
                if(toconvert.size()==1){
                    inwait=inwait.add(toconvert.get(0),howmany);
                }
            }
        }
    }

    /**It just store the white resources chosen from the player. */
    public void WhiteMarbleConverter(ArrayList<ResourceType> whiteres){
        for(ResourceType x: whiteres){
            inwait=inwait.add(x,1);
        }
    }

    public boolean CheckIntegrityToConvert(ArrayList<ResourceType> toconvert){
        ArrayList<ResourceType>temp=new ArrayList<>();
        for (ResourceType x: toconvert){
            temp.add(x);
        }
        for(ResourceType x: this.toconvert){
           while(temp.remove(x)){ }
        }
        if (temp.size()!=0){
            return false;
        }
        return true;
    }

    public void CleanConverter(){
        this.inwait=new NumberOfResources();
    }

    /*Private Methods*/
    /**It converts a single marble. It doesn't convert a Red marble.*/
    private ResourceType convert_single_marble(MarbleColor toconvert){
        switch (toconvert) {
            case BLUE: return ResourceType.Shields;
            case GREY: return ResourceType.Stones;
            case PURPLE: return ResourceType.Servants;
            case YELLOW: return ResourceType.Coins;
            default: throw new IllegalArgumentException(); //It should not enter here
        }
    }

    /**It makes the real conversion*/
    private NumberOfResources convert_resources(ArrayList<MarbleColor> input){
        NumberOfResources output=new NumberOfResources();
        for(MarbleColor marble: input){
            if(marble.equals(MarbleColor.RED)){
                owner.getFaithTrack().addPoint();
            }
            else{
                output=output.add(convert_single_marble(marble),1);
            }
        }
        return output;
    }


}