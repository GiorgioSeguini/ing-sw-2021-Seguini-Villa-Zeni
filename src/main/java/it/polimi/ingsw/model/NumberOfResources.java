package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.OutOfResourcesException;

/**This class is unchangeable: it always returns a new address to it */
public class NumberOfResources {
    private final int[] resources = new int[4]; //if issue attempt, make it in constructor

    /*Default Constructor*/
    public NumberOfResources(int Servants, int Shields, int Coins, int Stones){
        resources[0]=Servants;
        resources[1]=Shields;
        resources[2]=Coins;
        resources[3]=Stones;
    }

    /**
     * Empity constructor
     */
    public NumberOfResources(){
        for(int i: resources) i=0;
    }

    /*Getter*/
    /**It returns the type's item amount. */
    public int getAmountOf(ResourceType type){
        return resources[type.ordinal()];
    }

    /*Additional methods*/
    /**This method adds a NumberOfResources to the current one.*/
    public NumberOfResources add(NumberOfResources other){
        int[] x= new int[4];
        for(ResourceType type: ResourceType.values()){
            x[type.ordinal()]=resources[type.ordinal()]+ other.getAmountOf(type);
        }
        NumberOfResources new_resources= new NumberOfResources(x[0],x[1],x[2],x[3]);

        return new_resources;
    }

    /**This method adds just for a single type of resources.*/
    public NumberOfResources add(ResourceType type, int toadd){
        NumberOfResources new_resources= this.clone();
        new_resources.resources[type.ordinal()]=new_resources.getAmountOf(type)+toadd;
        return new_resources;
    }

    /**This method subs a NumberOfResources to the current one*/
    public NumberOfResources sub(NumberOfResources other) throws OutOfResourcesException {
        int[] x= new int[4];
        for(ResourceType type: ResourceType.values()){
            if (resources[type.ordinal()]>=other.getAmountOf(type)){
                x[type.ordinal()]=resources[type.ordinal()]- other.getAmountOf(type);
            }
            else{
                throw new OutOfResourcesException();
            }
        }
        NumberOfResources new_resources= new NumberOfResources(x[0],x[1],x[2],x[3]);

        return new_resources;
    }

    /**This method subs just for a single type of resources.*/
    public NumberOfResources sub(ResourceType type, int tosub) throws OutOfResourcesException{
        if(this.getAmountOf(type)<tosub){
            throw new OutOfResourcesException();
        }
        else{
            NumberOfResources new_resources= this.clone();
            new_resources.resources[type.ordinal()]=new_resources.getAmountOf(type)-tosub;
            return new_resources;
        }
    }

    /**This method returns the resource type that has the bigger quantity. */
    public ResourceType Max_Resource_Type(){
        int max=resources[0];
        int indexmax=0;
        ResourceType out;

        for (int i=1;i<resources.length;i++){
            if(max<resources[i]){
                max=resources[i];
                indexmax=i;
            }
        }
        switch (indexmax){
            case 0: out = ResourceType.Servants; break;
            case 1: out = ResourceType.Shields; break;
            case 2: out = ResourceType.Coins; break;
            case 3: out = ResourceType.Stones; break;
            default: out = null;
        }
        return out;
    }

    /**This simply clones a NumberOfResources*/
    @Override
    public NumberOfResources clone(){
        return new NumberOfResources(this.resources[0],this.resources[1],this.resources[2],this.resources[3]);
    }

    /**
     *
     * @return the total ammount of any type of resources
     */
    public int size(){
        int res=0;
        for(ResourceType type : ResourceType.values()){
            res+=getAmountOf(type);
        }
        return res;
    }

    public boolean isEmpty(){
        for (int x: resources){
            if (x!=0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof NumberOfResources))
            return false;
        NumberOfResources other = (NumberOfResources) obj;

        for(int i=0; i<resources.length; i++){
            if(this.resources[i]!= other.resources[i])
                return false;
        }
        return true;
    }
}