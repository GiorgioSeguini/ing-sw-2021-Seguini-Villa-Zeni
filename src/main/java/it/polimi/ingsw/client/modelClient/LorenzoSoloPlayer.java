package it.polimi.ingsw.client.modelClient;

import java.util.ArrayList;
import java.util.Collections;

/*Last Edit: Fabio*/
public class LorenzoSoloPlayer {

    private final FaithTrack faithTrack;
    private final Game game;
    private boolean isWinner;

    private ArrayList<SoloActionTokens> soloActionTokens;
    private ArrayList<SoloActionTokens> copyOfSoloActionTokens;
    //private ArrayList<SoloActionTokens> soloActionTokensDiscarded;


    /*default constructor*/
    public LorenzoSoloPlayer(Game game, ArrayList<SoloActionTokens> startSoloActionTokens) {
        this.game = game;
        faithTrack = new FaithTrack();
        soloActionTokens = new ArrayList<SoloActionTokens>(7);
        for(SoloActionTokens x: startSoloActionTokens){
            soloActionTokens.add(x);
        }
        Collections.shuffle(soloActionTokens);
        isWinner=false;

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

    public boolean isWinner(){
        return this.isWinner;
    }

    public ArrayList<SoloActionTokens> getSoloActionTokens() {
        return (ArrayList<SoloActionTokens>) soloActionTokens.clone();
    }

    /*public ArrayList<SoloActionTokens> getSoloActionTokensDiscarded() {
        return soloActionTokensDiscarded;
    }*/

    public ArrayList<SoloActionTokens> getCopyOfSoloActionTokensInit() {
        return (ArrayList<SoloActionTokens>) copyOfSoloActionTokens.clone();
    }


}