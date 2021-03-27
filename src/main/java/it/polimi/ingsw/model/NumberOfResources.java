package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

public class NumberOfResources {
    private final int[] resources = new int[4]; //if issue attempt, make it in constructor

    NumberOfResources(int Servants, int Shields, int Coins, int Stones){
        resources[0]=Servants;
        resources[1]=Shields;
        resources[2]=Coins;
        resources[3]=Stones;
    }

    public int getAmountOf(ResourceType type){
        return resources[type.ordinal()];
    }

    public NumberOfResources add(NumberOfResources other){
        int[] x= new int[4];
        for(ResourceType type: ResourceType.values()){
            x[type.ordinal()]=resources[type.ordinal()]+ other.getAmountOf(type);
        }
        NumberOfResources new_resources= new NumberOfResources(x[0],x[1],x[2],x[3]);

        return new_resources;
    }

    public NumberOfResources sub(NumberOfResources other) throws IllegalArgumentException{
        int[] x= new int[4];
        for(ResourceType type: ResourceType.values()){
            if (resources[type.ordinal()]>=other.getAmountOf(type)){
                x[type.ordinal()]=resources[type.ordinal()]- other.getAmountOf(type);
            }
            else{
                throw new IllegalArgumentException();
            }
        }
        NumberOfResources new_resources= new NumberOfResources(x[0],x[1],x[2],x[3]);

        return new_resources;
    }
    public NumberOfResources sub(ResourceType type, int tosub) throws IllegalArgumentException{
        if(this.getAmountOf(type)<tosub){
            throw new IllegalArgumentException();
        }
        else{
            NumberOfResources new_resources= this.clone();
            new_resources.resources[type.ordinal()]=new_resources.getAmountOf(type)-tosub;
            return new_resources;
        }
    }

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

    public NumberOfResources clone(){
        return new NumberOfResources(this.resources[0],this.resources[1],this.resources[2],this.resources[3]);
    }



}