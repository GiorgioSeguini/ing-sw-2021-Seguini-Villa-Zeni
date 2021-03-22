package it.polimi.ingsw.model;

/*Last Edit: Gio*/

public enum ColorDevCard {
    Yellow(1),
    Green(2),
    Blue(3),
    Purple(4);

    private final int index;

    ColorDevCard(int i) {
        this.index=i;
    }


    public int getIndex() {
        return index;
    }
}