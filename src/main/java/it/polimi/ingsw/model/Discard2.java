package it.polimi.ingsw.model;

/*Last Edit: Gio*/

public class Discard2 extends SoloActionTokens {

    private ColorDevCard color;


    /*Default constructor*/
    public Discard2() {
    }

    /*Getter*/
    public ColorDevCard getColor() {
        return color;
    }

    /*Abstract class to implement*/
    public void ActivateToken(Game game) {
        try {
            game.getDashboard().removeCard(color, 2);
        }catch(IllegalArgumentException e){
            //TODO game ended, Lorenzo Il Magnifico wins
        }
    }
}