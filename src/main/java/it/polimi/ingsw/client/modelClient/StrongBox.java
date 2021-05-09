package it.polimi.ingsw.client.modelClient;

/*Last Edit: William Zeni*/


public class StrongBox {

    private NumberOfResources resources;

    /* Default constructor*/
    StrongBox() {
        resources=new NumberOfResources();
    }

    /*Getter*/
    public NumberOfResources getResources() {
        return resources;
    }

    /*@Override
    public String toString(){
        String res="Strong Box\n";

        res+=getResources();
        return res;
    }*/
}