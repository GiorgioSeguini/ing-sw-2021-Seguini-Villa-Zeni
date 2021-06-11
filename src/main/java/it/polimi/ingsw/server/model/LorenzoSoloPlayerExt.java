package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.FaithTrack;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LorenzoSoloPlayer;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;

import java.util.*;

/*Last Edit: Fabio*/
public class LorenzoSoloPlayerExt extends LorenzoSoloPlayer implements Observable<Message> {

    private transient final GameExt game;
    private transient ArrayList<SoloActionTokens> soloActionTokens;
    private transient ArrayList<SoloActionTokens> copyOfSoloActionTokens;
    private SoloActionTokens revealed;

    /*default constructor*/
    public LorenzoSoloPlayerExt(GameExt game, FaithTrackExt faithTrack, ArrayList<SoloActionTokens> startSoloActionTokens) {
        super(faithTrack);
        this.game = game;
        soloActionTokens = new ArrayList<>(startSoloActionTokens);
        Collections.shuffle(soloActionTokens);

        copyOfSoloActionTokens = new ArrayList<>(7);
        copyOfSoloActionTokens = new ArrayList<>(soloActionTokens);
    }

    @Override
    public FaithTrackExt getFaithTrack() {
        return (FaithTrackExt) super.getFaithTrack();
    }


    /*Getter*/

    public ArrayList<SoloActionTokens> getSoloActionTokens() {
        return new ArrayList<>(soloActionTokens);
    }


    public ArrayList<SoloActionTokens> getCopyOfSoloActionTokensInit() {
        return new ArrayList<>(copyOfSoloActionTokens);
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
        soloActionTokensRevealed.ActivateToken(getGame());
        this.revealed = soloActionTokensRevealed;
    }

    public GameExt getGame() {
        return game;
    }

    //Observable implementation
    private transient final List<it.polimi.ingsw.server.observer.Observer<Message>> observers = new ArrayList<>();

    @Override
    public void addObserver(it.polimi.ingsw.server.observer.Observer<Message> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public void notify(Message message) {
        synchronized (observers) {
            for(Observer<Message> observer : observers){
                observer.update(message);
            }
        }
    }
}