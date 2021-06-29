package it.polimi.ingsw.constant.enumeration;

/**
 * The enum Level.
 */
public enum Level {
    /**
     * One level.
     */
    ONE,
    /**
     * Two level.
     */
    TWO,
    /**
     * Three level.
     */
    THREE;

    /**
     *  Enumeration's size.
     *
     * @return of type int: the size.
     */
    public static int size(){
        return Level.values().length;
    }

    /**
     * Gets next level.
     *
     * @return of type Level: the next level.
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Level getNext() throws IllegalArgumentException{
        if(this.ordinal() == Level.size() -1) {
            throw new IllegalArgumentException();
        }
        return Level.values()[this.ordinal() +1];
    }
}