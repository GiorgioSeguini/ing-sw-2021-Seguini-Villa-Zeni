package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.ConverterMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Converter;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.exception.HaveToChooseException;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class Ext.
 * Class that extends Converter and and implements Observable interface.
 * Manage resources and marbles just buyed from the market and that have to be stored or converted in resources.
 */
public class ConverterExt extends Converter implements Observable<Message> {
    private transient final PlayerExt owner;

    /**
     * Instantiates a new Converter ext.
     *
     * @param owner of type PlayerExt: the owner
     */
    /*Default Constructor*/
    public ConverterExt(PlayerExt owner){
        super(owner.getID());
        this.owner=owner;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public PlayerExt getOwner() {
        return owner;
    }

    /**
     * Set white ability.
     *
     * @param type the type
     */
    /*Setter*/
    public void setWhiteAbility(ResourceType type){
        for(ResourceType x: getToconvert()){
            if(type==x){
                throw new IllegalArgumentException();
            }
        }
        this.addToconvert(type);
    }

    /**
     * set the resources and notify the change
     *
     * @param resources of type NumberOfResources: the resources to set
     */
    @Override
    public void setResources(NumberOfResources resources) {
        super.setResources(resources);
        notify(new ConverterMessage(this));
    }

    /*Additional Methods*/

    /**
     * Returns TRUE if the WhiteAbility is active
     * @return the boolean
     */
    public boolean IsWhiteAbilityActive(){
        if(getToconvert().size()!=0){
            return true;
        }
        return false;
    }

    /**
     * It converts bought marbles and it throws an exception if there are white marbles to convert.
     * @param input of type ArrayList<MarbleColor>: the input
     *
     * @throws HaveToChooseException the have to choose exception
     */
    public void convertAll(ArrayList<MarbleColor> input) throws HaveToChooseException {
        ArrayList<MarbleColor> without_white=new ArrayList<>();
        int howmany=0;

        for (MarbleColor x: input){
            if(x!=MarbleColor.WHITE){
                without_white.add(x);
            }
            else{
                howmany++;
            }
        }
        setResources(convert_resources(without_white));
        if(howmany!=0){
            if (getToconvert().size()>1){
                setWhite(howmany);
                notify(new ConverterMessage(this));
                throw new HaveToChooseException();
            }
            else{
                if(getToconvert().size()==1){
                    setResources(getResources().add(getToconvert().get(0),howmany));
                }
            }
        }
        notify(new ConverterMessage(this));
    }

    /**
     * It just store the white resources chosen from the player in the converter.
     * @param whiteres of type ArrayList<ResourcesType>: the resources to set in the converter
     */
    public void WhiteMarbleConverter(ArrayList<ResourceType> whiteres){
        for(ResourceType x: whiteres){
            setResources(getResources().add(x, 1));
        }
    }

    /**
     * Check integrity to convert boolean.
     *
     * @param toconvert of type ArrayList<ResourceType>: the resources you have to convert
     * @return the boolean
     */
    public boolean CheckIntegrityToConvert(ArrayList<ResourceType> toconvert){
        if(toconvert.size()!=getWhite())
            return false;
        for(ResourceType type: toconvert){
            if(!getToconvert().contains(type))
                return false;
        }
        return true;
    }

    /**
     * Clean converter.
     */
    public void CleanConverter(){
        setResources(new NumberOfResources());
        notify(new ConverterMessage(this));
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

    //Observable implementation
    private transient final List<Observer<Message>> observers = new ArrayList<>();

    /**
     *
     * @param observer of type Observer<Message>: the observer to add
     */
    @Override
    public void addObserver(Observer<Message> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     *
     * @param message of type Message: the notifying message
     */
    @Override
    public void notify(Message message) {
        synchronized (observers) {
            for(Observer<Message> observer : observers){
                observer.update(message);
            }
        }
    }
}
