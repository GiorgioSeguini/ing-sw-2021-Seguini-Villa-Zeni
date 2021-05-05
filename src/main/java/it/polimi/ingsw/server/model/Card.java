package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/

public abstract class Card {

    private final int victoryPoints;
    private final int id;

    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
        this.id=0;
    }

    public int getId() {
        return id;
    }

    /**
     * @return
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

}