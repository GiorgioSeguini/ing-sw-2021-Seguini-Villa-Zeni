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
    public void ActivateToken(GameExt game) {
        try {
            ((DashboardExt)game.getDashboard()).removeCard(color, 2);
        }catch(IllegalArgumentException ignored){};

    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof Discard2))
            return false;

        Discard2 other = (Discard2) o;

        if(this.color!=other.getColor()){
            return false;
        }

        return this.getName().equals(other.getName());
    }
}