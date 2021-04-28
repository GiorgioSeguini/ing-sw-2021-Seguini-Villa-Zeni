package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;

import java.util.ArrayList;

/*Last Edit: William Zeni*/
public class WareHouseDepots {

    private final ArrayList<Shelf> shelfs=new ArrayList<>();

    /*Default constructor*/
    public WareHouseDepots(){
        for (int i=0; i<3; i++){
            shelfs.add(new Shelf(i+1));
        }
    }

    /*Getter*/
    public NumberOfResources getResources() {
        int[] x =new int[4];
        for (int i=0; i<shelfs.size();i++){
            try{
                x[shelfs.get(i).getResType().ordinal()]+= shelfs.get(i).getUsed();
            }catch (NullPointerException e){}
        }
        return new NumberOfResources(x[0],x[1],x[2],x[3]);
    }

    /*just for testing*/
    public ArrayList<Shelf> getShelfs() {
        ArrayList<Shelf> copy= new ArrayList<>();
        for (Shelf x: shelfs){
            Shelf cloned= new Shelf(x.getResType(),x.getMaxSize());
            cloned.setUsed(x.getUsed());
            if(x.getIsExtra()){
                cloned.setIsExtra();
            }
            copy.add(cloned);
        }
        return copy;
    }

    /*Additional Methods*/
    /** This method add the resources to all the shelf if it found them. */
    public void addResource (NumberOfResources input) throws UnableToFillException {
        NumberOfResources old_resources=this.getResources();
        NumberOfResources new_resources=this.getResources();
        new_resources=new_resources.add(input);

        try{
            CleanShelf();
            fill_correctly(new_resources);
        }
        catch (UnableToFillException error){
            CleanShelf();
            try {
                fill_correctly(old_resources);
            }catch (UnableToFillException error2){
                //I don't expect to enter here
            }
            throw new UnableToFillException();
        }
    }

    /** This method add the resources to all the shelf if it found them. */
    public void subResource(NumberOfResources required) throws OutOfResourcesException{
        //NumberOfResources old_resources=this.getResources();
        NumberOfResources new_resources=this.getResources();
        new_resources=new_resources.sub(required);
        try{
            CleanShelf();
            fill_correctly(new_resources);
        }
        catch (UnableToFillException error){
            //I don't expect to enter here. If I can sub the required resources, obviously I can fill them;
        }
    }

    /**This method is called by ability Deposit, and can be called 2 times. Add an extra shelf to WareHouseDepots*/
    public void addExtraShelf(Shelf shelf){
        this.shelfs.add(shelf);
        shelf.setIsExtra();
    }

    /**This method clean all the shelf from their items (the number is set to zero and the type is cancelled).
     * For the extra shelf the type is preserved.*/
    public void CleanShelf(){
        for (Shelf x: shelfs){
            x.setUsed(0);
            if (!x.getIsExtra()){
                x.setResType(null);
            }
        }
    }

    /**This method check that the number of resources is not asking to add 4 different types of resources */
    private boolean check_NumberOfResources_Integrity_for_WareHouseDepots(NumberOfResources input){
        int check=0;
        for (ResourceType x: ResourceType.values()){
            if(input.getAmountOf(x)!=0){
                check++;
            }
        }
        return check != 4;
    }

    /**This method return true if with the current disposition of the shelf you can fill them with a Number of resources */
    private void fill_correctly(NumberOfResources my_resources)throws UnableToFillException {

        /*This for checks just for the extra shelf*/
        for(Shelf x: shelfs){
            if(x.getIsExtra()) {
                if (my_resources.getAmountOf(x.getResType()) != 0) {
                    if (my_resources.getAmountOf(x.getResType()) > x.getMaxSize()) {
                        x.setUsed(x.getMaxSize());
                    } else {
                        x.setUsed(my_resources.getAmountOf(x.getResType()));
                    }
                    try {
                        my_resources = my_resources.sub(x.getResType(), x.getUsed());
                    } catch (OutOfResourcesException errorSub) {
                        //I don't expect to enter here.
                    }
                }
            }
        }

        /*This for checks the first three shelfs*/
        if(check_NumberOfResources_Integrity_for_WareHouseDepots(my_resources)){
            for (int i=2;i>=0;i--){
                if(shelfs.get(i).getMaxSize()< my_resources.getAmountOf(my_resources.Max_Resource_Type())){
                    throw new UnableToFillException();
                }
                else{
                    if(my_resources.getAmountOf(my_resources.Max_Resource_Type())!=0) {
                        shelfs.get(i).setUsed(my_resources.getAmountOf(my_resources.Max_Resource_Type()));
                        shelfs.get(i).setResType(my_resources.Max_Resource_Type());
                        try {
                            my_resources = my_resources.sub(shelfs.get(i).getResType(), shelfs.get(i).getUsed());
                        } catch (OutOfResourcesException errorSub) {
                            //I don't expect to enter here.
                        }
                    }
                }
            }
        }
        else {
            throw new UnableToFillException();
        }
    }

}