package it.polimi.ingsw.constant.model;

/*Last Edit: William Zeni*/


public class StrongBox {

    private NumberOfResources resources;

    /* Default constructor*/
    public StrongBox() {
        resources=new NumberOfResources(50,50, 50, 50);
    }

    /*Getter*/
    public NumberOfResources getResources() {
        return resources;
    }


    public void setResources(NumberOfResources resources) {
        this.resources = resources;
    }

    @Override
    public String toString(){
        String res="Strong Box\n";

        res+=getResources();
        return res;
    }
}