package it.polimi.ingsw.client.modelClient;

/*Last Edit: Gio*/

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.enumeration.ResourceType;

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

    /*@Override
    public String toString(){
        String card="";
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
    }*/

}