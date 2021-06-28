package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;

/*Last Edit: Gio*/

public abstract class Dashboard {

    /**
     * @param color of the card wanted
     * @param level of che card wanted
     * @return null if stack is empty, otherwise return the card in the top position, the one you can buy
     */
    public abstract DevelopmentCard getTopDevCard(ColorDevCard color, Level level);


}