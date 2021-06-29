package it.polimi.ingsw.constant.enumeration;

/**
 * The enum Marble color.
 */
public enum MarbleColor {

    /**
     * Yellow marble color.
     */
    YELLOW('Y'),
    /**
     * Blue marble color.
     */
    BLUE('B'),
    /**
     * Grey marble color.
     */
    GREY('G'),
    /**
     * Red marble color.
     */
    RED('R'),
    /**
     * White marble color.
     */
    WHITE('W'),
    /**
     * Purple marble color.
     */
    PURPLE('P');
    private final char color;

    /**
     * Constructor.
     * @param color of type char: the color's char.
     */
    MarbleColor(char color) {
        this.color = color;
    }

    /**
     * Get color's char.
     *
     * @return of type char: the char.
     */
    public char getChar(){
        return color;
    }
}