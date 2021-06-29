package it.polimi.ingsw.constant.model;

import java.util.ArrayList;

/**
 * WareHouseDepots class.
 * Superclass of WareHouseDepotsExt.
 */
public class WareHouseDepots {

    protected final ArrayList<Shelf> shelfs=new ArrayList<>();

    /**
     * Instantiates a new Ware house depots.
     */
    /*Default constructor*/
    public WareHouseDepots(){
        for (int i=0; i<3; i++){
            shelfs.add(new Shelf(i+1));
        }
    }

    /**
     * Gets resources.
     *
     * @return of type NumberOfResources: all the resources in the ware house depots.
     */
    /*Getter*/
    public NumberOfResources getResources() {
        int[] x =new int[4];
        for (Shelf shelf : shelfs) {
            try {
                if (shelf.getResType() != null)
                    x[shelf.getResType().ordinal()] += shelf.getUsed();
            } catch (NullPointerException ignored) {
            }
        }
        return new NumberOfResources(x[0],x[1],x[2],x[3]);
    }

    /**
     * Gets shelves.
     *
     * @return of type ArrayList<Shelf>: the shelves.
     */
    public ArrayList<Shelf> getShelves() {
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


    @Override
    public String toString(){
        String res = "Warehouse Depots: \n";
        for(Shelf x: shelfs){
            res+=x+"\n";
        }
        return res;
    }

}
