package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

/* Type of Game's Resources */
public enum ResourceType {
    Servants(0),
    Shields(1),
    Coins(2),
    Stones(3);

    private int index;

    private ResourceType(int x){
        this.index=x;
    }

    public int getIndex(){
        return this.index;
    }
}