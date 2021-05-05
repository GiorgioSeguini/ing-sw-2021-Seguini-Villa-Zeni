package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 */
public class DevelopmentCard extends Card {

    private final Level level;
    private final ColorDevCard cardColor;
    private final NumberOfResources cost;
    private final ProductionPower productionPower;
    static AtomicInteger nextId= new AtomicInteger();

    /**
     * Default constructor
     */
    public DevelopmentCard(Level level, ColorDevCard cardColor, NumberOfResources cost, ProductionPower productionPower, int victoryPoints) {
        super(victoryPoints, nextId.incrementAndGet());
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
        String res="";
        res+="Level: "+getLevel() +"\n";
        res+="Color: "+getColor() +"\n";
        res+="VictoryPoints: "+getVictoryPoints() +"\n";
        res+="Cost" +"\n";
        for(ResourceType y: ResourceType.values()){
            res+="\t"+y+": "+getCost().getAmountOf(y) +"\n";
        }
        res+="Production Power" +"\n";
        res+="\tInput Resources" +"\n";
        for(ResourceType y: ResourceType.values()){
            res+=("\t\t"+y+": "+getProductionPower().getInputRes().getAmountOf(y) +"\n");
        }
        res+="\tOutput Resources" +"\n";
        for(ResourceType y: ResourceType.values()){
            res+=("\t\t"+y+": "+getProductionPower().getOutputRes().getAmountOf(y) +"\n");
        }
        res+="\t\tFaithPoints out: "+getProductionPower().getFaithPointsOut() +"\n";
        res+="\t\tYourChoiceIn: "+getProductionPower().getOfYourChoiceInput() +"\n";
        res+="\t\tYourChoiceOut: "+getProductionPower().getOfYourChoiceOutput() +"\n";


        return res;
    }

}