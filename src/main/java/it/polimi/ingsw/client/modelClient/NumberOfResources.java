package it.polimi.ingsw.client.modelClient;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.constant.enumeration.ResourceType;

/**This class is unchangeable: it always returns a new address to it */
public class NumberOfResources {
    private final int[] resources = new int[4]; //if issue attempt, make it in constructor

    /*Default Constructor*/
    public NumberOfResources(int Servants, int Shields, int Coins, int Stones){
        if(Servants< 0 || Shields<0 || Coins<0 || Stones<0)
            throw new IllegalArgumentException();
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
        if(toadd<0){
            throw new IllegalArgumentException();
        }
        NumberOfResources new_resources= this.clone();
        new_resources.resources[type.ordinal()]=new_resources.getAmountOf(type)+toadd;
        return new_resources;
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

    //TODO I NEED SETTER TESTING
    /*@Override
    public String toString(){
        String resources="";
        resources+="Servants: "+ this.resources[0]+"\t";
        resources+="Shields: "+ this.resources[1]+"\t";
        resources+="Coins: "+ this.resources[2]+"\t";
        resources+="Stones: "+ this.resources[3]+"\n";

        return resources;
    }*/
}