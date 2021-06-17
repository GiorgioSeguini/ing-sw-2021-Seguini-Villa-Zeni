package it.polimi.ingsw.constant.enumeration;

public enum ErrorMessage {
    NoError,
    ChooseResourceError,
    FinalTurnError,
    HaveToChooseError,
    NoMoreLeaderCardAliveError,
    NoSpaceError("Non c'è spazio in questo slot!"),
    OutOfResourcesError("Non hai le risorse per effettuare questa azione!\n"),
    UnableToFillError("Non è possibile disporre le risorse come richiesto, scarta qualcos'altro!\n"),
    MoveNotAllowed,
    NotYourTurn,
    CardNotOwned,
    BadChoice("Questa scelta non può essere fatta");

    private String text=null;

    ErrorMessage(){
    }

    ErrorMessage(String text){
        this.text= text;
    }

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
