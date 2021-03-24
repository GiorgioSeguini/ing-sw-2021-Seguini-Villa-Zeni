package it.polimi.ingsw.model;

/**
 * 
 */
public enum Level {
    One,
    Two,
    Three;

    public static int size(){
        return Level.values().length;
    }
}