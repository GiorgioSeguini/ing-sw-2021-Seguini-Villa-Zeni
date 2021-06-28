package it.polimi.ingsw.server.model;


import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.server.model.exception.ChoseResourcesException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;


/**
 * ProductionPowerExt class.
 * Extends ProductionPower.
 * Manage the production power.
 */
public class ProductionPowerExt extends ProductionPower {

    /**
     * Instantiates a new Production power ext.
     *
     * @param points of type int: the faithpoints.
     * @param outputRes of type NumberOfResources: the output resources.
     * @param inputRes of type NumberOfResources: the input resources.
     * @param ofYourChoiceInput of type NumberOfResources: resources of your choice in input.
     * @param ofYourChoiceOutput of type NumberOfResources: resources of your choice in output.
     */
    public ProductionPowerExt(int points, NumberOfResources outputRes, NumberOfResources inputRes, int ofYourChoiceInput, int ofYourChoiceOutput){
        super(points, outputRes, inputRes, ofYourChoiceInput, ofYourChoiceOutput);
    }

    /**
     * Instantiates a new Production power ext without resources of your choice.
     *
     * @param points of type int: the faithpoints.
     * @param outputRes of type NumberOfResources: the output resources.
     * @param inputRes  of type NumberOfResources: the input resources.
     */
    public ProductionPowerExt(int points, NumberOfResources outputRes, NumberOfResources inputRes){
        super(points, outputRes, inputRes);
    }

    /**
     * Instantiates a new Production power ext without resources of your choice and faithpoints.
     *
     * @param outputRes of type NumberOfResources: the output resources.
     * @param inputRes  of type NumberOfResources: the input resources.
     */
    public ProductionPowerExt(NumberOfResources outputRes, NumberOfResources inputRes) {
        super(outputRes, inputRes);
    }

    /**
     * Instantiates an empty Production power ext.
     */
    public ProductionPowerExt(){
        super();
    }

    /**
     *
     * @param other of type ProductionPower: the production power that has to be add.
     * @return of type ProductionPowerExt: a new ProductionPowerExt which has this attributes plus the param other attributes.
     */
    @Override
    public ProductionPowerExt add(ProductionPower other) {
        return new ProductionPowerExt(this.getFaithPointsOut() + other.getFaithPointsOut(),
                    this.getOutputRes().add(other.getOutputRes()),
                    this.getInputRes().add(other.getInputRes()),
                    this.getOfYourChoiceInput() + other.getOfYourChoiceInput(),
                    this.getOfYourChoiceOutput() + other.getOfYourChoiceOutput());
    }

    /**
     * Active a production power without resources of your choice.
     *
     * @param owner of type PlayerExt: the production power's owner.
     * @throws OutOfResourcesException the out of resources exception
     * @throws ChoseResourcesException the chose resources exception
     */
    /*Modifier*/
    public void active(PlayerExt owner) throws OutOfResourcesException, ChoseResourcesException{
            active(owner, new NumberOfResources(), new NumberOfResources());
    }

    /**
     * Active.
     *
     * @param owner of type PlayerExt: the production power's owner.
     * @param choiceInput of type NumberOfResources: resources of your choice in input.
     * @param choiceOutput of type NumberOfResources: resources of your choice in output.
     * @throws OutOfResourcesException the out of resources exception
     * @throws ChoseResourcesException the chose resources exception
     */
    public void active(PlayerExt owner, NumberOfResources choiceInput, NumberOfResources choiceOutput) throws OutOfResourcesException, ChoseResourcesException{
        if(this.getOfYourChoiceInput() != choiceInput.size() || this.getOfYourChoiceOutput() !=choiceOutput.size()){
            throw new ChoseResourcesException(this.getOfYourChoiceInput(), this.getOfYourChoiceOutput());
        }

        owner.getDepots().subResource(this.getInputRes().add(choiceInput));
        owner.getDepots().addResourceFromProduction(this.getOutputRes().add(choiceOutput));

        for(int i = 0; i< this.getFaithPointsOut(); i++){
            owner.getFaithTrack().addPoint();
        }

    }

}