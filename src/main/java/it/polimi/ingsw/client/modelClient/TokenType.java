package it.polimi.ingsw.client.modelClient;

public enum TokenType {
    DISCARD2_BLUE("Lorenzo ha scartato 2 carte sviluppo blu."),
    DISCARD2_GREEN("Lorenzo ha scartato 2 carte sviluppo verdi."),
    DISCARD2_PURPLE("Lorenzo ha scartato 2 carte sviluppo viola."),
    DISCARD2_YELLOW("Lorenzo ha scartato 2 carte sviluppo gialle."),
    MOVE2("Lorenzo è avanzato di due caselle sul tracciato fede. "),
    MOVE_SHUFFLE("Lorenzo è avanzato di una casella sul tracciato fede e ha rimescolato i Segnalini Azione.");


    private final String textToPrint;

    TokenType(String textToPrint){
        this.textToPrint= textToPrint;
    }

    public String getTextToPrint() {
        return textToPrint;
    }
}
