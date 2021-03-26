package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

public class StrongBox {

    private NumberOfResources resources;

    /* Default constructor*/
    StrongBox() {
        resources=new NumberOfResources(0,0,0,0);
    }

    /*Getter*/
    public NumberOfResources getResources() {
        return resources;
    }

    /*Additional Methods*/
    /**This method adds resources to the strongbox*/
    public void addResource(NumberOfResources input) {
        resources = resources.add(input);
    }

    /**This method subs resources to the strongbox*/
    public void subResource(NumberOfResources required) throws IllegalArgumentException{
        NumberOfResources old_resources= resources.clone();
        try{
            resources=resources.sub(required);
        }catch (IllegalArgumentException error){
            resources=old_resources;
        }
    }

}