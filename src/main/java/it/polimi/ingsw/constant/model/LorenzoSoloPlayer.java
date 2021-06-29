package it.polimi.ingsw.constant.model;

/**
 * LorenzoSoloPlayer class.
 * Superclass of LorenzoSoloPlayerExt (server) and of LorenzoSoloPlayerClient (client).
 */
public abstract class LorenzoSoloPlayer {

    private final FaithTrack faithTrack;
    private boolean isWinner;


    /**
     * Instantiates a new Lorenzo solo player.
     *
     * @param faithTrack of type FaithTrack: the faith track.
     */
    /*default constructor*/
    public LorenzoSoloPlayer(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
        this.isWinner=false;
    }


    /**
     * Gets faith track.
     *
     * @return of type FaithTrack: the faith track.
     */
    /*Getter*/
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    /**
     * Check if it is the winner.
     *
     * @return of type boolean: isWinner.
     */
    public boolean isWinner(){
        return this.isWinner;
    }

    /**
     * Sets isWinner on True.
     */
    public void setWinner() {
        this.isWinner = true;
    }
}