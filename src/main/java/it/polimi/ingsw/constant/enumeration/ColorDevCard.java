package it.polimi.ingsw.constant.enumeration;

/**
 * The enum Color dev card.
 */
public enum ColorDevCard {
    /**
     * Yellow color dev card.
     */
    YELLOW,
    /**
     * Green color dev card.
     */
    GREEN,
    /**
     * Blue color dev card.
     */
    BLUE,
    /**
     * Purple color dev card.
     */
    PURPLE;

    /**
     *  Enumeration's size.
     *
     * @return of type int: the size.
     */
    public static int size(){
        return ColorDevCard.values().length;
    }
}