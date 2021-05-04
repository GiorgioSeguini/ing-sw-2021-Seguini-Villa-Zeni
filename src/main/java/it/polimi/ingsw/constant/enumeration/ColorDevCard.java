package it.polimi.ingsw.constant.enumeration;

/*Last Edit: Gio*/

//removed functions getIndex, use ordinal instead

public enum ColorDevCard {
    YELLOW,
    GREEN,
    BLUE,
    PURPLE;

    public static int size(){
        return ColorDevCard.values().length;
    }
}