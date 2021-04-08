package it.polimi.ingsw.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.model.enumeration.ColorDevCard;

public class DiscountAbility implements Ability{

    private ColorDevCard cardColor;
    private int discount;

    /*Default constructor*/
    public DiscountAbility(ColorDevCard cardColor, int discount) {
        this.cardColor = cardColor;
        this.discount = discount;

    }

    /*Getter*/
    public ColorDevCard getDiscountCardColor() {
        return cardColor;
    }

    public int getDiscountAmount() {
        return discount;
    }

    @Override
    public void RunAbility(Player owner){
        //TODO
    }

}