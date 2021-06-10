package it.polimi.ingsw.constant.enumeration;

public enum PlayerStatus {
    Waiting("", ""),
    Active("Fai una mossa", "Sta scegliendo che mossa fare"),
    MovePerformed("Hai fatto la tua mossa", "Ha fatto la sua mossa"),
    NeedToConvert("", "Sta comprendo dal mercato"),
    NeedToStore("", "Sta comprendo dal mercato"),
    NeedToChoseRes("", "Sta attivando una produzione");

    private final String textForMe;
    private final String textForOther;

    PlayerStatus(String textForMe, String textForOther) {
        this.textForMe = textForMe;
        this.textForOther = textForOther;
    }

    public String getTextForMe() {
        return textForMe;
    }

    public String getTextForOther() {
        return textForOther;
    }

}
