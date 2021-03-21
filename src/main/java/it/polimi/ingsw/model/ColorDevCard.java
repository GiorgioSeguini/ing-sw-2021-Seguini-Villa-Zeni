package it.polimi.ingsw.model;

/**
 * 
 */
public enum ColorDevCard {
    Yellow(1),
    Green(2),
    Blue(3),
    Purple(4);


    ColorDevCard(int i) {
        this.index=i;
    }

    /**
    *
     */
    private final int index;

    /**
    *
     */
    public int getIndex() {
        return index;
    }
}