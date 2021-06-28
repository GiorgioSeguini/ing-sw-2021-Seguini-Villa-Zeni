package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.LorenzoSoloPlayer;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  LorenzoSoloPlayerExt class.
 *  Extends LorenzoSoloPlayer and implements Observable interface.
 */
public class LorenzoSoloPlayerExt extends LorenzoSoloPlayer implements Observable<Message> {

    private transient final GameExt game;
    private transient ArrayList<SoloActionTokens> soloActionTokens;
    private transient ArrayList<SoloActionTokens> copyOfSoloActionTokens;
    private SoloActionTokens revealed;

    /**
     * Instantiates a new Lorenzo solo player ext.
     *
     * @param game of type GameExt: the game.
     * @param faithTrack of type FaithTrackExt: the faith track.
     * @param startSoloActionTokens of type ArrayList<SoloActionTokens>: the starting solo action tokens.
     */
    /*default constructor*/
    public LorenzoSoloPlayerExt(GameExt game, FaithTrackExt faithTrack, ArrayList<SoloActionTokens> startSoloActionTokens) {
        super(faithTrack);
        this.game = game;
        soloActionTokens = new ArrayList<>(startSoloActionTokens);
        Collections.shuffle(soloActionTokens);

        copyOfSoloActionTokens = new ArrayList<>(7);
        copyOfSoloActionTokens = new ArrayList<>(soloActionTokens);
    }

    /**
     *
     * @return of type FaithTrackExt: the faithtrack.
     */
    @Override
    public FaithTrackExt getFaithTrack() {
        return (FaithTrackExt) super.getFaithTrack();
    }


    /*Getter*/

    /**
     * Gets solo action tokens.
     *
     * @return of type ArrayList<SoloActionTokens>: the solo action tokens.
     */
    public ArrayList<SoloActionTokens> getSoloActionTokens() {
        return new ArrayList<>(soloActionTokens);
    }


    /**
     * Gets copy of the initial solo action tokens.
     *
     * @return the copy of the initial solo action tokens.
     */
    public ArrayList<SoloActionTokens> getCopyOfSoloActionTokensInit() {
        return new ArrayList<>(copyOfSoloActionTokens);
    }


    /**
     * Sets solo action tokens.
     *
     * @param soloActionTokens of type ArrayList<SoloActionTokens>: the solo action tokens to set.
     */
    /*setter*/
    public void setSoloActionTokens(ArrayList<SoloActionTokens> soloActionTokens) {
        this.soloActionTokens = soloActionTokens;
    }

    /*Additional methods*/

    /**
     * Reveal first stack's token and  apply the effect
     */
    public void revealToken() {

        SoloActionTokens soloActionTokensRevealed = soloActionTokens.get(0);
        //soloActionTokensDiscarded.add(soloActionTokensRevealed);
        soloActionTokens.remove(soloActionTokensRevealed);
        soloActionTokensRevealed.ActivateToken(getGame());
        this.revealed = soloActionTokensRevealed;
    }

    /**
     * Gets game.
     *
     * @return of type GameExt: the game.
     */
    public GameExt getGame() {
        return game;
    }

    //Observable implementation
    private transient final List<it.polimi.ingsw.server.observer.Observer<Message>> observers = new ArrayList<>();

    /**
     *
     * @param observer of type Observer<Message>: the observer to add
     */
    @Override
    public void addObserver(it.polimi.ingsw.server.observer.Observer<Message> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     *
     * @param message of type Message: the notifying message
     */
    @Override
    public void notify(Message message) {
        synchronized (observers) {
            for(Observer<Message> observer : observers){
                observer.update(message);
            }
        }
    }
}