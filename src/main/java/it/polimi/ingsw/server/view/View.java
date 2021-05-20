package it.polimi.ingsw.server.view;


import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constant.message.InitialMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.controller.Performable;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.LeaderCardExt;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class View implements Observable<Performable>, Observer<Message> {

    private final Player player;


    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    /*protected abstract void showMessage(Object message);*/

    void handleMove(Performable x) {
        notify(x);
    }

    public void sendInitialMessage(GameExt game){
        if(game.getPlayerIndex(this.player)==-1)
            return;

        int myID = this.player.getID();
        this.update(new InitialMessage(game, myID, game.getActivableLeadCard(player)));
    }

    //Observable implementation
    private transient final List<Observer<Performable>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<Performable> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public void notify(Performable move) {
        synchronized (observers) {
            for(Observer<Performable> observer : observers){
                observer.update(move);
            }
        }
    }

}
