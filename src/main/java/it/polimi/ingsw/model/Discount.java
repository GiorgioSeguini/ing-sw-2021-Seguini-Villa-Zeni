package it.polimi.ingsw.model;

/*Last Edit: Gio*/

public class Discount extends Ability{

    private ColorDevCard cardColor;
    private int discount;

    /*Default constructor*/
    public Discount() {
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