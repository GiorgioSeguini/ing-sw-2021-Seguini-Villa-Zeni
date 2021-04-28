package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/


public abstract class Card {

    private final int victoryPoints;

    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    /**
     * @return
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

}