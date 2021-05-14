package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.FaithTrack;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LorenzoSoloPlayer;

import java.util.*;

/*Last Edit: Fabio*/
public class LorenzoSoloPlayerExt extends LorenzoSoloPlayer {


    private transient ArrayList<SoloActionTokens> soloActionTokens;
    private ArrayList<SoloActionTokens> copyOfSoloActionTokens;
    //private ArrayList<SoloActionTokens> soloActionTokensDiscarded;


    /*default constructor*/
    public LorenzoSoloPlayerExt(Game game, FaithTrack faithTrack, ArrayList<SoloActionTokens> startSoloActionTokens) {
        super(game, faithTrack);
        soloActionTokens = new ArrayList<SoloActionTokens>(startSoloActionTokens);
        Collections.shuffle(soloActionTokens);

        copyOfSoloActionTokens = new ArrayList<SoloActionTokens>(7);
        copyOfSoloActionTokens = (ArrayList<SoloActionTokens>) soloActionTokens.clone();
    }


    /*Getter*/

    public ArrayList<SoloActionTokens> getSoloActionTokens() {
        return (ArrayList<SoloActionTokens>) soloActionTokens.clone();
    }


    public ArrayList<SoloActionTokens> getCopyOfSoloActionTokensInit() {
        return (ArrayList<SoloActionTokens>) copyOfSoloActionTokens.clone();
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
        soloActionTokens.remove(soloActionTokensRevealed);
        soloActionTokensRevealed.ActivateToken(super.getGame());
    }


}