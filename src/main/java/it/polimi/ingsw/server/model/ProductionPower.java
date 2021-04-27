package it.polimi.ingsw.server.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.server.model.exception.ChoseResourcesException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;


public class ProductionPower {

    private final int pointsFaithOut;
    private final NumberOfResources outputRes;
    private final NumberOfResources inputRes;
    private final int ofYourChoiceInput;
    private final int ofYourChoiceOutput;


    /*Default constructor*/
    public ProductionPower(int points, NumberOfResources outputRes, NumberOfResources inputRes, int ofYourChoiceInput, int ofYourChoiceOutput){
        if(ofYourChoiceInput<0 || ofYourChoiceOutput<0 || points<0)
            throw new ArithmeticException();
        this.pointsFaithOut =points;
        this.outputRes=outputRes;
        this.inputRes=inputRes;
        this.ofYourChoiceInput = ofYourChoiceInput;
        this.ofYourChoiceOutput = ofYourChoiceOutput;
    }
    public ProductionPower(int points, NumberOfResources outputRes, NumberOfResources inputRes){
        this(points, outputRes, inputRes, 0, 0);
    }
    public ProductionPower(NumberOfResources outputRes, NumberOfResources inputRes){
        this(0, outputRes, inputRes, 0, 0);
    }
    public ProductionPower(){
        this(0, new NumberOfResources(), new NumberOfResources(), 0, 0);
    }

    /*Getter*/
    public int getFaithPointsOut() {
        return this.pointsFaithOut;
    }

    public NumberOfResources getInputRes() {
        return this.inputRes;
    }

    public NumberOfResources getOutputRes() {
        return this.outputRes;
    }

    public int getOfYourChoiceInput() {
        return ofYourChoiceInput;
    }

    public int getOfYourChoiceOutput() {
        return ofYourChoiceOutput;
    }

    /**
     *
     * @return true if the power production chosen do not need any choice
     */
    public boolean easyActive(){
        return getOfYourChoiceOutput()==0 && getOfYourChoiceInput()==0;
    }

    /*Modifier*/
    /**
     *  It's usefull for active production in parallel
     * @param other adder
     * @return return the sum of this and other
     */
    public ProductionPower add(ProductionPower other){
        return new ProductionPower(this.pointsFaithOut + other.pointsFaithOut, this.outputRes.add(other.outputRes), this.inputRes.add(other.inputRes), this.ofYourChoiceInput + other.ofYourChoiceInput, this.ofYourChoiceOutput + other.ofYourChoiceOutput);
    }


    public void active(Player owner) throws OutOfResourcesException, ChoseResourcesException{
            active(owner, new NumberOfResources(), new NumberOfResources());
    }

    public void active(Player owner, NumberOfResources choiceInput, NumberOfResources choiceOutput) throws OutOfResourcesException, ChoseResourcesException{
        if(ofYourChoiceInput != choiceInput.size() || ofYourChoiceOutput !=choiceOutput.size()){
            throw new ChoseResourcesException(ofYourChoiceInput, ofYourChoiceOutput);
        }

        owner.getDepots().subResource(this.inputRes.add(choiceInput));
        owner.getDepots().addResourceFromProduction(this.outputRes.add(choiceOutput));

        for(int i = 0; i< this.pointsFaithOut; i++){
            owner.getFaithTrack().addPoint();
        }

    }


    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof ProductionPower))
            return false;

        ProductionPower other = (ProductionPower) o;

        if(this.pointsFaithOut != other.pointsFaithOut)
            return false;

        if(this.ofYourChoiceInput != other.ofYourChoiceInput)
            return false;

        if(this.ofYourChoiceOutput != other.ofYourChoiceOutput)
            return false;

        if(!this.inputRes.equals(other.inputRes))
            return false;

         return this.outputRes.equals(other.outputRes);
    }


}