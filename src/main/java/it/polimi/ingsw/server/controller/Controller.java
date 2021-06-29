package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.observer.Observer;

/**
 * Controller class.
 * Implements Observer<Performable> interface.
 * Control of MVC pattern, a single instance for each game active on server.
 */
public class Controller implements Observer<Performable> {

    private final GameExt game;

    /**
     * Instantiates a new Controller.
     *
     * @param game of type GameExt: the game.
     */
    public Controller(GameExt game) {
        this.game = game;
    }

    /**
     * Execute the performable. Otherwise set an error message.
     * @param x of type Performable: the performable.
     */
    @Override
    public synchronized void update(Performable x) {
        if(x.canPerformExt(game)) {
            x.performMove(game);
            game.lastMessage();
        }else{
            System.out.println("Move invalid");
            //x.getPlayer().setErrorMessage();
            game.errorMessage(x.getIdPlayer());
        }
    }

}
