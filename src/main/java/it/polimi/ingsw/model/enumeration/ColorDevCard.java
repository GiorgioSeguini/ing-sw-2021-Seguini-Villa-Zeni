package it.polimi.ingsw.model.enumeration;

/*Last Edit: Gio*/

//removed functions getIndex, use ordinal instead

public enum ColorDevCard {
    Yellow,
    Green,
    Blue,
    Purple;

    public static int size(){
        return ColorDevCard.values().length;
    }
}