package it.polimi.ingsw.constant.enumeration;

/*Last Edit: Fabio*/
/* DevelopmentCard's level*/
public enum Level {
    ONE,
    TWO,
    THREE;

    public static int size(){
        return Level.values().length;
    }

    public Level getNext() throws IllegalArgumentException{
        if(this.ordinal() == Level.size() -1) {
            throw new IllegalArgumentException();
        }
        return Level.values()[this.ordinal() +1];
    }
}