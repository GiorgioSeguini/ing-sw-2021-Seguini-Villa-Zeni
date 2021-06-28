package it.polimi.ingsw.constant.enumeration;

/*Last Edit: William Zeni*/

/* Type of Game's Resources */
public enum ResourceType {
    Servants(MarbleColor.PURPLE),
    Shields(MarbleColor.BLUE),
    Coins(MarbleColor.YELLOW),
    Stones(MarbleColor.GREY);

    private final MarbleColor color;

    ResourceType(MarbleColor color){
        this.color=color;
    }

    public MarbleColor getColor() {
        return color;
    }
}