package it.polimi.ingsw.client.modelClient;

import java.util.ArrayList;
import java.util.Collections;

/*Last Edit: Fabio*/
public class MoveShuffle implements SoloActionTokens {

    private ArrayList<SoloActionTokens> shuffledDeck;

    /*Default constructor*/
    public MoveShuffle() {
        shuffledDeck = new ArrayList<>(7);
    }




    /**This shuffle randomly the SoloActionToken's stack**/
    /*private ArrayList<SoloActionTokens> Shuffle(ArrayList<SoloActionTokens> soloActionTokens){
        for(int i=0; i<7; i++) {
            int n = (int) (Math.random() * 6.1);
            while(shuffledDeck.get(n) != null) {
                n = (int) (Math.random() * 6.1);
            }
            shuffledDeck.add(n, soloActionTokens.get(i));
        }
        return shuffledDeck;
    }*/

}
