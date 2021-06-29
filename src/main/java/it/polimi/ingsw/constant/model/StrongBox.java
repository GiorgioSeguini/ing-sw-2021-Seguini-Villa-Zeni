package it.polimi.ingsw.constant.model;

/**
 * StrongBox class.
 * Superclass of StrongBoxExt.
 */
public class StrongBox {

    private NumberOfResources resources;

    /**
     * Instantiates a new Strong box.
     */
    /* Default constructor*/
    public StrongBox() {
        resources=new NumberOfResources();
    }

    /**
     * Gets resources.
     *
     * @return of type NumberOfResources: the resources in the strong box.
     */
    /*Getter*/
    public NumberOfResources getResources() {
        return resources;
    }


    /**
     * Sets resources.
     *
     * @param resources of type NumberOfResources: the resources to set.
     */
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