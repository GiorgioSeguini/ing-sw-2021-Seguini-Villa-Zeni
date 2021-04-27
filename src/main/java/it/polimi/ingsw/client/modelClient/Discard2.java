package it.polimi.ingsw.client.modelClient;

/*Last Edit: Gio*/

import it.polimi.ingsw.client.modelClient.enumeration.ColorDevCard;

public class Discard2 implements SoloActionTokens {

    private final ColorDevCard color;


    /*Default constructor*/
    public Discard2(ColorDevCard color) {
        this.color=color;
    }

    /*Getter*/
    public ColorDevCard getColor() {
        return color;
    }

}