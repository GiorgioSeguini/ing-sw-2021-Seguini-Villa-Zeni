package it.polimi.ingsw.model;

import java.util.*;

/*Last Edit: Fabio*/
public class LorenzoSoloPlayer {

    private FaithTrack faithTrack;
    private Game game;

    private ArrayList<SoloActionTokens> soloActionTokens;
    private ArrayList<SoloActionTokens> soloActionTokensDiscarded;

    private SoloActionTokens soloActionTokensRevealed;


    //default constructor
    public LorenzoSoloPlayer() {
        soloActionTokens = new ArrayList<SoloActionTokens>(7);
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<SoloActionTokens> getSoloActionTokens() {
        return soloActionTokens;
    }

    /**
     * @return
     */
    public void revealdToken() {

        soloActionTokensRevealed = soloActionTokens.get(0);
        soloActionTokensDiscarded.add(soloActionTokensRevealed);
        soloActionTokens.remove(0);
        soloActionTokensRevealed.ActivateToken(game);
    }


}