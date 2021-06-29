package it.polimi.ingsw.constant.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;

/**
 * 
 */
public class DevelopmentCard extends Card {

    private final Level level;
    private final ColorDevCard cardColor;
    private final NumberOfResources cost;
    private final ProductionPower productionPower;

    /**
     * Default constructor
     */
    public DevelopmentCard(Level level, ColorDevCard cardColor, NumberOfResources cost, ProductionPower productionPower, int victoryPoints) {
        super(victoryPoints);
        this.level = level;
        this.cardColor = cardColor;
        this.cost = cost;
        this.productionPower = productionPower;
    }

    /**
     * @return
     */
    public NumberOfResources getCost() {
        return cost;
    }

    /**
     * @return
     */
    public ProductionPower getProductionPower() {
        return productionPower;
    }

    /**
     * @return
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @return
     */
    public ColorDevCard getColor() {
        return cardColor;
    }

    @Override
    public String toString(){
        String card="\n";
        card+="CARD "+this.getId()+"\n";
        card+="Level: "+getLevel() +"\n";
        card+="Color: "+getColor() +"\n";
        card+="VictoryPoints: "+getVictoryPoints() +"\n";
        card+="Cost:" +"\n";
        card+="\t"+getCost()+"\n";
        card+="Production Power:" +"\n";
        card+="\tInput Resources:" +"\n";
        card+=("\t\t"+getProductionPower().getInputRes()+"\n");
        card+="\tOutput Resources:" +"\n";
        card+=("\t\t"+getProductionPower().getOutputRes()+"\n");
        card+="\t\tFaithPoints out: "+getProductionPower().getFaithPointsOut() +"\n";
        card+="\t\tYourChoiceIn: "+getProductionPower().getOfYourChoiceInput() +"\n";
        card+="\t\tYourChoiceOut: "+getProductionPower().getOfYourChoiceOutput() +"\n";
        return card;
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o.getId is equals to this.getId. False if param o isn't an instance of DevelopmentCard or o isn't equals to this.
     */
    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;
        if(!(o instanceof DevelopmentCard)){
            return false;
        }
        return ((DevelopmentCard) o).getId() == this.getId();
    }

}