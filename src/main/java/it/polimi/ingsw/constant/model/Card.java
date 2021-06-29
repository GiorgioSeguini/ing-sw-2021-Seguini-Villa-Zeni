package it.polimi.ingsw.constant.model;

/**
 * Card abstract class.
 * Superclass of LeaderCard and DevelopmentCard classes.
 */
public abstract class Card {

    private final int victoryPoints;
    private final int id;

    /**
     * Instantiates a new Card.
     *
     * @param victoryPoints of type int: card's victory points.
     */
    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
        this.id = 0;
    }

    /**
     * Gets victory points.
     *
     * @return of type int: the victory points.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Gets id.
     *
     * @return of type int: the card's id
     */
    public int getId() {
        return id;
    }
}