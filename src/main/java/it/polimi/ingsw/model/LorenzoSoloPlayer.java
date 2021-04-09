package it.polimi.ingsw.model;

import java.util.*;

/*Last Edit: Fabio*/
public class LorenzoSoloPlayer {

    private final FaithTrack faithTrack;
    private final Game game;

    private ArrayList<SoloActionTokens> soloActionTokens;
    private ArrayList<SoloActionTokens> copyOfSoloActionTokens;
    //private ArrayList<SoloActionTokens> soloActionTokensDiscarded;


    /*default constructor*/
    public LorenzoSoloPlayer(Game game) {
        this.game = game;
        faithTrack = new FaithTrack(0);
        soloActionTokens = new ArrayList<SoloActionTokens>(7);

        copyOfSoloActionTokens = new ArrayList<SoloActionTokens>(7);
        copyOfSoloActionTokens = (ArrayList<SoloActionTokens>) soloActionTokens.clone();
    }

    /*Getter*/
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<SoloActionTokens> getSoloActionTokens() {
        return soloActionTokens; //TODO controllare
    }

    /*public ArrayList<SoloActionTokens> getSoloActionTokensDiscarded() {
        return soloActionTokensDiscarded;
    }*/

    public ArrayList<SoloActionTokens> getCopyOfSoloActionTokensInit() {
        return copyOfSoloActionTokens;
    }

    /*setter*/
    public void setSoloActionTokens(ArrayList<SoloActionTokens> soloActionTokens) {
        this.soloActionTokens = soloActionTokens;
    }

    /*Additional methods*/
    /**this reveal first stack's token and  apply the effect**/
    public void revealToken() {

        SoloActionTokens soloActionTokensRevealed = soloActionTokens.get(0);
        //soloActionTokensDiscarded.add(soloActionTokensRevealed);
        soloActionTokens.remove(0);
        soloActionTokensRevealed.ActivateToken(game);
    }


}