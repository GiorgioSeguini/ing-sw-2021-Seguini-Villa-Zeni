package it.polimi.ingsw.model;

import java.util.ArrayList;

/*Last Edit: William Zeni*/
public class WareHouseDepots {

    //private final Shelf[] shelfs= new Shelf[3];
    private ArrayList<Shelf> shelfs= new ArrayList<Shelf>();
    //private ArrayList<Shelf> extra_shelf;

    /*Default constructor*/
     WareHouseDepots() {
        for(int i=0; i<3; i++){
            shelfs.add(new Shelf(i+1));
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

    /*Additional Methods*/
    /** This method add the resources to all the shelf if it found them. */
    public void addResource(NumberOfResources input) {
        //TODO
    }

    /** This method add the resources to all the shelf if it found them. */
    public void subResource(NumberOfResources required) {
      //TODO
    }

    /**This method checks if you can really add what do you want to add in WareHouseDepots*/
    public boolean canAdd(NumberOfResources input){

        NumberOfResources my_resources= this.getResources();
        my_resources=my_resources.add(input); //funza perchè number of resources l'abbiamo fatta immutabile, torna sempre una classe nuova
        return fill_correctly(my_resources);
    }

    public boolean canSub(NumberOfResources input){
        NumberOfResources my_resources= this.getResources();
        my_resources=my_resources.sub(input); //funza perchè number of resources l'abbiamo fatta immutabile, torna sempre una classe nuova
        return fill_correctly(my_resources);
    }

    /**This method check if all the shelfs have different types of resources*/
    public boolean check_3_shelf_type_Integrity(){
        for(int i=0; i<2;i++){
            for(int j=i+1; j<3;j++){
                if(shelfs.get(i).getResType()==shelfs.get(j).getResType()){
                    return false;
                }
            }
        }
        return true;
    }

    /**This method is called by ability Deposit, and can be called 2 times. Add an extra shelf to WareHouseDepots*/
    public void addExtraShelf(Shelf shelf){
        this.shelfs.add(shelf);
        shelf.SetIsExtra();
    }

    /**This method return true if with the current disposition of the shelf you can fill them with a Number of resources */
    private boolean fill_correctly(NumberOfResources my_resources){
        NumberOfResources to_sub;

        for (ResourceType x: ResourceType.values()){
            if(my_resources.getAmountOf(x)!=0){
                for (Shelf layer: shelfs){
                    if(layer.getResType()==x){
                        int new_resource_tosub;
                        if(my_resources.getAmountOf(x)>layer.getMaxSize()){
                            new_resource_tosub=my_resources.getAmountOf(x)-layer.getMaxSize();
                        }
                        else{
                            new_resource_tosub= my_resources.getAmountOf(x);
                        }
                        switch (x.getIndex()){
                            case 0: to_sub=new NumberOfResources(new_resource_tosub,0,0,0);
                                my_resources=my_resources.sub(to_sub);
                                break;
                            case 1: to_sub=new NumberOfResources(0,new_resource_tosub,0,0);
                                my_resources=my_resources.sub(to_sub);
                                break;
                            case 2: to_sub=new NumberOfResources(0,0,new_resource_tosub,0);
                                my_resources=my_resources.sub(to_sub);
                                break;
                            case 3: to_sub=new NumberOfResources(0,0,0, new_resource_tosub);
                                my_resources=my_resources.sub(to_sub);
                                break;
                        }
                    }
                }
            }
        }
        if (my_resources.equals(new NumberOfResources(0,0,0,0))){
            return true;
        }
        else{
            return false;
        }

    }


}
