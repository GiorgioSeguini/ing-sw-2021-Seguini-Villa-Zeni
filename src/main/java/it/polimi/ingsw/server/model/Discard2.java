package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;

/**
 * Discard 2 class.
 * Implements SoloActionTokens interface.
 * Discard 2 Development card of the specified color from the DashBoard.
 */
public class Discard2 implements SoloActionTokens {

    public static final String name = "DISCARD2";
    private final ColorDevCard color;

    /**
     * Instantiates a new Discard 2.
     *
     * @param color of type ColorDevCard: the color of the cards that have to be discarded.
     */
    /*Default constructor*/
    public Discard2(ColorDevCard color) {
        this.color=color;
    }

    /**
     * Gets color.
     *
     * @return the card's color
     */
    /*Getter*/
    public ColorDevCard getColor() {
        return color;
    }

    /**
     * Remove two cards with a selected color.
     * @param game of type GameExt
     */
    @Override
    public void ActivateToken(GameExt game) {
        try {
            game.getDashboard().removeCard(color, 2);
        }catch(IllegalArgumentException ignored){}

    }

    /**
     *
     * @return of type String: the name.
     */
    @Override
    public String getName(){
        return name;
    }

    /**
     *
     * @return of type String: the name and the color.
     */
    @Override
    public String getEnum() {
        return name + "_" + color;
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o  is equals to this. False if param o isn't an instance of Discard2 or o isn't equals to this.
     */
    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof Discard2))
            return false;

        Discard2 other = (Discard2) o;

        if(this.color!=other.getColor()){
            return false;
        }

        return this.getName().equals(other.getName());
    }
}