package it.polimi.ingsw.constant.model;

/*Last Edit: Fabio*/
public abstract class LorenzoSoloPlayer {

    private final FaithTrack faithTrack;
    private boolean isWinner;



    /*default constructor*/
    public LorenzoSoloPlayer(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
        this.isWinner=false;
    }


    /*Getter*/
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public boolean isWinner(){
        return this.isWinner;
    }

    public void setWinner() {
        this.isWinner = true;
    }
}