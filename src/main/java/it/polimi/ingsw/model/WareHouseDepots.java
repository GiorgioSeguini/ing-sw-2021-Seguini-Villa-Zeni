package it.polimi.ingsw.model;

public class WareHouseDepots {

    private final Shelf[] shelfs= new Shelf[3];

    /*Default constructor*/
     WareHouseDepots() {
        for(int i=0; i<3; i++){
            shelfs[i]=new Shelf(i+1);
        }
    }

    /*Getter*/
    public NumberOfResources getResources() {
        int[] x =new int[4];
        for (Shelf layer: shelfs){
            x[layer.getResType().getIndex()]= layer.getUsed();
        }
        NumberOfResources resources = new NumberOfResources(x[0],x[1],x[2],x[3]);
        return resources;
    }

    /** This method add the resources to all the shelf if it found them. */
    public void addResource(NumberOfResources input) {
        for(Shelf x: shelfs){
            if(input.getAmountOf(x.getResType())>0){
                x.add(input.getAmountOf(x.getResType()));
            }
            else{
                System.out.println("Type of resources not found.");
            }
        }

    }

    /** This method add the resources to all the shelf if it found them. */
    public void subResource(NumberOfResources required) {
        for(Shelf x: shelfs){
            if(required.getAmountOf(x.getResType())>0){
                x.sub(required.getAmountOf(x.getResType()));
            }
            else{
                System.out.println("Type of resources not found.");
            }
        }
    }

    /*Additional Methods*/
    public boolean canAdd(NumberOfResources input){
        if(check_shelf_type_Integrity() && check_NumberOfResources_Integrity_for_WareHouseDepots(input)){
            for (ResourceType x: ResourceType.values()){
                if(input.getAmountOf(x)!=0){
                    int check=0;
                    for (Shelf layer: shelfs){
                        if(x==layer.getResType()){
                            if(layer.getUsed()+input.getAmountOf(x)> layer.getMaxSize()){
                                return false;
                            }
                        }else{
                            check++;
                        }
                    }
                    if(check==3){
                        return false;
                    }

                }
            }
        }
        return true;
    }/**This method check this: if there is just one mismatch with the request to add, returns false*/
    //Svuoto mensole, creo NumberOfResources mia+aggiungere e poi vedo se riesco a sistemarla sugli scaffali

    public boolean canSub(NumberOfResources input){
        if(check_shelf_type_Integrity() && check_NumberOfResources_Integrity_for_WareHouseDepots(input)){
            for (ResourceType x: ResourceType.values()){
                if(input.getAmountOf(x)!=0){
                    int check=0;
                    for (Shelf layer: shelfs){
                        if(x==layer.getResType()){
                            if(layer.getUsed()-input.getAmountOf(x)<0){
                                return false;
                            }
                        }else{
                            check++;
                        }
                    }
                    if(check==3){
                        return false;
                    }

                }
            }
        }
        return true;
    }/**This method check this: if there is just one mismatch with the request to sub, returns false*/

    public boolean check_shelf_type_Integrity(){
        for(int i=0; i<2;i++){
            for(int j=i+1; j<3;j++){
                if(shelfs[i].getResType()==shelfs[j].getResType()){
                    return false;
                }
            }
        }
        return true;
    }
    /**This method check if all the shelfs have different types of resources*/

    private boolean check_NumberOfResources_Integrity_for_WareHouseDepots(NumberOfResources input){
        int check=0;
        for (ResourceType x: ResourceType.values()){
            if(input.getAmountOf(x)!=0){
                check++;
            }
        }
        if (check==4){
            return false;
        }
        else{
            return true;
        }
    }
    /**This method check that the number of resources is not asking to add 4 different types of resources */

}
