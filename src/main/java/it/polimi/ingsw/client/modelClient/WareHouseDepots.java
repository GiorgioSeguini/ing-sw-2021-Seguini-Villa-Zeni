package it.polimi.ingsw.client.modelClient;

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

    /*@Override
    public String toString(){
        String res = "Warehouse Depots: \n";
        for(Shelf x: shelfs){
            res+=x+"\n";
        }
        return res;
    }*/

}
