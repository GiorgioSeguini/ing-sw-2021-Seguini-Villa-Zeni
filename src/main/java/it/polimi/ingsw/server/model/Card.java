package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/


import java.util.concurrent.atomic.AtomicInteger;

public abstract class Card {

    private final int victoryPoints;
    private int id;

    public Card(int victoryPoints, int id) {
        this.victoryPoints = victoryPoints;
        this.id=id;
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