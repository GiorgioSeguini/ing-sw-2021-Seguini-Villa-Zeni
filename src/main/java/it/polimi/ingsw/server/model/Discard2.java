package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.constant.enumeration.ColorDevCard;

public class Discard2 implements SoloActionTokens {

    public static final String name = "Discard2";
    private final ColorDevCard color;


    /*Default constructor*/
    public Discard2(ColorDevCard color) {
        this.color=color;
    }

    /*Getter*/
    public ColorDevCard getColor() {
        return color;
    }

    @Override
    public void ActivateToken(Game game) {
        try {
            game.getDashboard().removeCard(color, 2);
        }catch(IllegalArgumentException ignored){};

    }

    @Override
    public String getName(){
        return name;
    }
}