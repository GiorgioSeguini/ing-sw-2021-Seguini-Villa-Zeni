package it.polimi.ingsw.model;

import java.util.*;

/*Last Edit: Fabio*/
public class LorenzoSoloPlayer {

    private FaithTrack faithTrack;

    private ArrayList<SoloActionTokens> soloActionTokens;
    private ArrayList<SoloActionTokens> soloActionTokensDiscarded;

    private SoloActionTokens soloActionTokensRevealed;


    //default constructor
    public LorenzoSoloPlayer() {
        soloActionTokens = new ArrayList<SoloActionTokens>(7);
    }

    /**
     * @return
     */
    public void revealdToken() {

        soloActionTokensRevealed = soloActionTokens.get(0);
        soloActionTokensDiscarded.add(soloActionTokensRevealed);
        soloActionTokens.remove(0);
        soloActionTokensRevealed.ActivateToken();
    }

   /*public void activeToken(){
        SoloActionTokens ToActiveToken = revealdToken();
        ToActiveToken.ac
    }*/

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

}