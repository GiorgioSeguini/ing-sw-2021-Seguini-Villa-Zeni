package it.polimi.ingsw.constant.enumeration;

/**
 * The enum Resource type.
 */
public enum ResourceType {
    /**
     * Servants resource type.
     */
    Servants(MarbleColor.PURPLE),
    /**
     * Shields resource type.
     */
    Shields(MarbleColor.BLUE),
    /**
     * Coins resource type.
     */
    Coins(MarbleColor.YELLOW),
    /**
     * Stones resource type.
     */
    Stones(MarbleColor.GREY);

    private final MarbleColor color;

    /**
     * Constructor.
     * @param color of type MarbleColor: marble's color
     */
    ResourceType(MarbleColor color){
        this.color=color;
    }

    /**
     * Gets color.
     *
     * @return of type MarbleColor: the color.
     */
    public MarbleColor getColor() {
        return color;
    }
}