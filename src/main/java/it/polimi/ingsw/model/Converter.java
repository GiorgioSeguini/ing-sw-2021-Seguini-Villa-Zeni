package it.polimi.ingsw.model;

/*Las Edit: William Zeni*/

import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.HaveToChooseException;
import java.util.ArrayList;

public class Converter {

    boolean WhiteAbilityActive;
    Player owner;
    NumberOfResources inwait;

    Converter(Player owner){
        WhiteAbilityActive=false;
        this.owner=owner;
    }

    public boolean getWhiteAbility(){
        return this.WhiteAbilityActive;
    }

    public void setWhiteAbility(){
        this.WhiteAbilityActive=true;
    }

    /*Questa classe converte tutte le risorse non bianche. Se trova qualcosa di bianco e l'abilità è attiva
    lancia un eccezione e ripone le risorse già convertite in inwait (che sono le risorse non bianche convertite). Sarà a cura del controllorer o
    del player gestire l'eccezione, creare una number of resources delle quantità bianche da convertire in base alla scelta
    del giocatore e usare rescueConversion per terminare la conversione.
     */
    public NumberOfResources convertAll(ArrayList<MarbleColor> input) throws HaveToChooseException{
        ArrayList<MarbleColor>without_white=new ArrayList<>();
        boolean check_white_presence=false;

        for (MarbleColor x: input){
            if(x!=MarbleColor.White){
                without_white.add(x);
            }
            else{
                check_white_presence=true;
            }
        }
        if (check_white_presence && WhiteAbilityActive){
            inwait=convert_resources(without_white); //TODO gestire eccezione
            throw new HaveToChooseException();
        }
        else{
            return convert_resources(without_white);
        }

    }

    public NumberOfResources rescueConversion(NumberOfResources whiteresources){
        whiteresources=whiteresources.add(inwait);
        inwait=new NumberOfResources();
        return whiteresources;
    }

    private ResourceType convert_single_marble(MarbleColor toconvert){
        switch (toconvert) {
            case Blue: return ResourceType.Shields;
            case Grey: return ResourceType.Stones;
            case Purple: return ResourceType.Servants;
            case Yellow: return ResourceType.Coins;
            default: return null; //It should not enter here
        }
    }

    private NumberOfResources convert_resources(ArrayList<MarbleColor> input){
        NumberOfResources output=new NumberOfResources();
        for(MarbleColor marble: input){
            if(marble.equals(MarbleColor.Red)){
                owner.getFaithTrack().addPoint(); //TODO gestire eccezione
            }
            else{
                output=output.add(convert_single_marble(marble),1);
            }
        }
        return output;
    }


}
