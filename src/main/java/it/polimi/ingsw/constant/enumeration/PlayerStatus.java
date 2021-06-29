package it.polimi.ingsw.constant.enumeration;

/**
 * The enum Player status.
 */
public enum PlayerStatus {
    /**
     * Waiting player status.
     */
    Waiting("", ""),
    /**
     * The Active status.
     */
    Active("Fai una mossa", "Sta scegliendo che mossa fare"),
    /**
     * The Move performed status.
     */
    MovePerformed("Hai fatto la tua mossa", "Ha fatto la sua mossa"),
    /**
     * The Need to convert status.
     */
    NeedToConvert("", "Sta comprendo dal mercato"),
    /**
     * The Need to store status.
     */
    NeedToStore("", "Sta comprendo dal mercato"),
    /**
     * The Need to chose res status.
     */
    NeedToChoseRes("", "Sta attivando una produzione");

    private final String textForMe;
    private final String textForOther;

    /**
     * Constructor.
     * @param textForMe of type String: text for me.
     * @param textForOther of type String: text for the other.
     */
    PlayerStatus(String textForMe, String textForOther) {
        this.textForMe = textForMe;
        this.textForOther = textForOther;
    }

    /**
     * Gets text for me.
     *
     * @return of type String: the text for me.
     */
    public String getTextForMe() {
        return textForMe;
    }

    /**
     * Gets text for other.
     *
     * @return of type String: the text for the other.
     */
    public String getTextForOther() {
        return textForOther;
    }

}
