package it.polimi.ingsw.client.modelClient;

/*Last Edit: William Zeni*/

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

    @Override
    public String toString(){
        String PP ="";
        PP+="Input Resources" +"\n";
        PP += "\t"+getInputRes();
        PP +="Output Resources" +"\n";
        PP += "\t"+getOutputRes();
        PP+="FaithPoints out: "+getFaithPointsOut() +"\n";
        PP+="YourChoiceIn: "+getOfYourChoiceInput() +"\n";
        PP+="YourChoiceOut: "+getOfYourChoiceOutput() +"\n";
        return PP;
    }


}