package it.polimi.ingsw.server.view;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constant.message.InitialMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.controller.MoveType;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class View extends Observable<MoveType> implements Observer<Message> {

    private final Player player;


    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    /*protected abstract void showMessage(Object message);*/

    void handleMove(MoveType x) {
        notify(x);
    }

    public void sendInitialMessage(Game game){
        if(game.getPlayerIndex(this.player)==-1)
            return;
        String model = Starter.toJson(game, Game.class);
        int myID = this.player.getID();
        Type type = new TypeToken<ArrayList<LeaderCard>>(){}.getType();
        String leaderCards = Starter.toJson(game.getActivableLeadCard(player), type);

        this.update(new InitialMessage(model, myID, leaderCards));
    }

}
