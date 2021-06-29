package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;

/**
 * Dashboard abstract class.
 * Superclass of DashboardExt (Server) and DashBoardClient (modelClient).
 */
public abstract class Dashboard {

    /**
     * Gets the development card (color,level) on the top of the stack.
     *
     * @param color of type ColorDevCard: the color of the card wanted.
     * @param level of type Level: the level of the card wanted.
     * @return null if stack is empty, otherwise return the card in the top position, the one you can buy
     */
    public abstract DevelopmentCard getTopDevCard(ColorDevCard color, Level level);


}