package it.polimi.ingsw.model;

/*Last Edit: Fabio*/
/* DevelopmentCard's level*/
public enum Level {
    One,
    Two,
    Three;

    public static int size(){
        return Level.values().length;
    }
}