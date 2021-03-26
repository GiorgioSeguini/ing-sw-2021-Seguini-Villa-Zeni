package it.polimi.ingsw.model;

/*Last Edit: Gio*/

public class DiscountAbility extends Ability{

    private ColorDevCard cardColor;
    private int discount;

    /*Default constructor*/
    public DiscountAbility() {
    }

    /*Getter*/
    public ColorDevCard getDiscountCardColor() {
        return cardColor;
    }

    public int getDiscountAmount() {
        return discount;
    }

    /*Abstract class to implement*/
    public void RunAbility(Player owner){
        //TODO
    }

}