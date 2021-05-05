package it.polimi.ingsw.server.model;

/*Last Edit: William Zeni*/

public interface SoloActionTokens{

    /*method that has to be implemented*/
    public abstract void ActivateToken(Game game);

    public abstract String getName();
}