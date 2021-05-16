package it.polimi.ingsw.server.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.server.model.exception.ChoseResourcesException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;


public class ProductionPowerExt extends ProductionPower {

    public ProductionPowerExt(int points, NumberOfResources outputRes, NumberOfResources inputRes, int ofYourChoiceInput, int ofYourChoiceOutput){
        super(points, outputRes, inputRes, ofYourChoiceInput, ofYourChoiceOutput);
    }
    public ProductionPowerExt(int points, NumberOfResources outputRes, NumberOfResources inputRes){
        super(points, outputRes, inputRes);
    }
    public ProductionPowerExt(NumberOfResources outputRes, NumberOfResources inputRes) {
        super(outputRes, inputRes);
    }
    public ProductionPowerExt(){
        super();
    }
        @Override
    public ProductionPowerExt add(ProductionPower other) {
        return (ProductionPowerExt) super.add(other);
    }

    /*Modifier*/
    public void active(PlayerExt owner) throws OutOfResourcesException, ChoseResourcesException{
            active(owner, new NumberOfResources(), new NumberOfResources());
    }

    public void active(PlayerExt owner, NumberOfResources choiceInput, NumberOfResources choiceOutput) throws OutOfResourcesException, ChoseResourcesException{
        if(this.getOfYourChoiceInput() != choiceInput.size() || this.getOfYourChoiceInput() !=choiceOutput.size()){
            throw new ChoseResourcesException(this.getOfYourChoiceInput(), this.getOfYourChoiceOutput());
        }

        ((DepotsExt)owner.getDepots()).subResource(this.getInputRes().add(choiceInput));
        ((DepotsExt)owner.getDepots()).addResourceFromProduction(this.getOutputRes().add(choiceOutput));

        for(int i = 0; i< this.getFaithPointsOut(); i++){
            ((FaithTrackExt)owner.getFaithTrack()).addPoint();
        }

    }

}