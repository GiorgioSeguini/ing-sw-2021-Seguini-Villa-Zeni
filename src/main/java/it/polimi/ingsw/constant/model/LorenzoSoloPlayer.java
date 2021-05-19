package it.polimi.ingsw.constant.model;

/*Last Edit: Fabio*/
public abstract class LorenzoSoloPlayer {

    private final FaithTrack faithTrack;
    private final Game game;
    private boolean isWinner;



    /*default constructor*/
    public LorenzoSoloPlayer(Game game, FaithTrack faithTrack) {
        this.game = game;
        this.faithTrack = faithTrack;
        this.isWinner=false;
    }


    /*Getter*/
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Game getGame() {
        return game;
    }

    public boolean isWinner(){
        return this.isWinner;
    }

    public void setWinner() {
        this.isWinner = true;
    }
}