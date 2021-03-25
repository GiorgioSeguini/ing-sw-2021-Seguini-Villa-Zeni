package it.polimi.ingsw.model;

import java.util.ArrayList;

/*Last Edit: William Zeni*/
public class WareHouseDepots {

    private final ArrayList<Shelf> shelfs;

    /*Default constructor*/
    WareHouseDepots(){
        shelfs=new ArrayList<Shelf>();
        for (int i=0; i<3; i++){
            shelfs.add(new Shelf(i+1));
        }
    }

    /*Getter*/
    public NumberOfResources getResources() {
        int[] x =new int[4];
        for (Shelf layer: shelfs){
            x[layer.getResType().ordinal()]= layer.getUsed();
        }
        return new NumberOfResources(x[0],x[1],x[2],x[3]);
    }

    /*Additional Methods*/
    /** This method add the resources to all the shelf if it found them. */
    public void addResource (NumberOfResources input) {
        NumberOfResources old_resources=this.getResources();
        NumberOfResources new_resources=this.getResources();
        new_resources=new_resources.add(input);

        CleanShelf();
        try{
            fill_correctly(new_resources);
        }
        catch (Exception ImpossibleFill){
            fill_correctly(old_resources);
        }
    }

    /** This method add the resources to all the shelf if it found them. */
    public void subResource(NumberOfResources required) {
      //TODO
    }

    /**This method checks if you can really add what do you want to add in WareHouseDepots*/
    public boolean canAdd(NumberOfResources input){
       return false;
    }

    public boolean canSub(NumberOfResources input){
        return false;
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
        shelf.setIsExtra();
    }

    public void CleanShelf(){
        for (Shelf x: shelfs){
            x.setUsed(0);
            if (!x.getIsExtra()){
                x.setResType(null);
            }
        }
    }

    /**This method return true if with the current disposition of the shelf you can fill them with a Number of resources */
    private void fill_correctly(NumberOfResources my_resources){

    }


}
