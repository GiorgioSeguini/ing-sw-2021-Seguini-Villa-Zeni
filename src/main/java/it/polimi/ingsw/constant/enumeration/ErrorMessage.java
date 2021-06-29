package it.polimi.ingsw.constant.enumeration;

/**
 * The enum Error message.
 */
public enum ErrorMessage {
    /**
     * No error message.
     */
    NoError,
    /**
     * Choose resource error message.
     */
    ChooseResourceError,
    /**
     * Final turn error message.
     */
    FinalTurnError,
    /**
     * Have to choose error message.
     */
    HaveToChooseError,
    /**
     * No more leader card alive error message.
     */
    NoMoreLeaderCardAliveError,
    /**
     * The No space error.
     */
    NoSpaceError("Non c'è spazio in questo slot!"),
    /**
     * The Out of resources error.
     */
    OutOfResourcesError("Non hai le risorse per effettuare questa azione!\n"),
    /**
     * The Unable to fill error.
     */
    UnableToFillError("Non è possibile disporre le risorse come richiesto, scarta qualcos'altro!\n"),
    /**
     * Move not allowed error message.
     */
    MoveNotAllowed,
    /**
     * Not your turn error message.
     */
    NotYourTurn,
    /**
     * Card not owned error message.
     */
    CardNotOwned,
    /**
     * The Bad choice.
     */
    BadChoice("Questa scelta non può essere fatta");

    private String text=null;

    /**
     * Void constructor.
     */
    ErrorMessage(){
    }

    /**
     * Constructor with the String.
     * @param text of type String: text's message.
     */
    ErrorMessage(String text){
        this.text= text;
    }

    /**
     * To string message.
     * @return of type String: the string message.
     */
    @Override
    public String toString() {
        if(text==null){
            return super.toString();
        }
        else{
            return text;
        }
    }


}
