package it.polimi.ingsw.client.modelClient;

/**
 * The enum Token type.
 */
public enum TokenType {
    /**
     * Discard 2 blue Development card from the DashBoard,
     */
    DISCARD2_BLUE("Lorenzo ha scartato 2 carte sviluppo blu."),
    /**
     * Discard 2 green Development card from the DashBoard.
     */
    DISCARD2_GREEN("Lorenzo ha scartato 2 carte sviluppo verdi."),
    /**
     * Discard 2 purple Development card from the DashBoard.
     */
    DISCARD2_PURPLE("Lorenzo ha scartato 2 carte sviluppo viola."),
    /**
     * Discard 2 yellow Development card from the DashBoard.
     */
    DISCARD2_YELLOW("Lorenzo ha scartato 2 carte sviluppo gialle."),
    /**
     * Lorenzo solo Player gains 2 faithPoints
     */
    MOVE2("Lorenzo è avanzato di due caselle sul tracciato fede. "),
    /**
     * Lorenzo solo Player gains 1 faithPoints. Tokens' deck is shuffled
     */
    MOVE_SHUFFLE("Lorenzo è avanzato di una casella sul tracciato fede e ha rimescolato i Segnalini Azione.");


    private final String textToPrint;

    TokenType(String textToPrint){
        this.textToPrint= textToPrint;
    }

    /**
     * Gets text to print to describe tokens action
     *
     * @return the text to print - of type String
     */
    public String getTextToPrint() {
        return textToPrint;
    }
}
