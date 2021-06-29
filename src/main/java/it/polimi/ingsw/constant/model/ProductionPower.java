package it.polimi.ingsw.constant.model;

/**
 * ProductionPower class.
 * Superclass of ProductionPowerExt (server).
 */
public class ProductionPower {

    private final int pointsFaithOut;
    private final NumberOfResources outputRes;
    private final NumberOfResources inputRes;
    private final int ofYourChoiceInput;
    private final int ofYourChoiceOutput;


    /**
     * Instantiates a new Production power.
     *
     * @param points of type int: the points earned with this production.
     * @param outputRes of type NumberOfResources: the output preset resources.
     * @param inputRes of type NumberOfResources: the input preset resources.
     * @param ofYourChoiceInput of type NumberOfResources: resources of your choice in input.
     * @param ofYourChoiceOutput of type NumberOfResources: resources of your choice in output.
     */
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

    /**
     * Instantiates a new Production power with preset input and output.
     *
     * @param points of type int: the points earned with this production.
     * @param outputRes of type NumberOfResources: the output preset resources.
     * @param inputRes of type NumberOfResources: the input preset resources..
     */
    public ProductionPower(int points, NumberOfResources outputRes, NumberOfResources inputRes){
        this(points, outputRes, inputRes, 0, 0);
    }

    /**
     * Instantiates a new Production power with preset input and output and without points earned.
     * @param outputRes of type NumberOfResources: the output preset resources.
     * @param inputRes of type NumberOfResources: the input preset resources..
     */
    public ProductionPower(NumberOfResources outputRes, NumberOfResources inputRes){
        this(0, outputRes, inputRes, 0, 0);
    }

    /**
     * Instantiates an empty Production power.
     */
    public ProductionPower(){
        this(0, new NumberOfResources(), new NumberOfResources(), 0, 0);
    }

    /**
     * Gets faith points out.
     *
     * @return of type int: the faith points out.
     */
    /*Getter*/
    public int getFaithPointsOut() {
        return this.pointsFaithOut;
    }

    /**
     * Gets input resources.
     *
     * @return of type NumberOfResources: the input resources.
     */
    public NumberOfResources getInputRes() {
        return this.inputRes;
    }

    /**
     * Gets output resources.
     *
     * @return of type NumberOfResources: the output resources.
     */
    public NumberOfResources getOutputRes() {
        return this.outputRes;
    }

    /**
     * Gets the resources of your choice in input.
     *
     * @return of type int: the number of the resources of your choice in input.
     */
    public int getOfYourChoiceInput() {
        return ofYourChoiceInput;
    }

    /**
     * Gets the resources of your choice in output.
     *
     * @return of type int: the number of the resources of your choice in output.
     */
    public int getOfYourChoiceOutput() {
        return ofYourChoiceOutput;
    }

    /**
     * Easy active boolean.
     *
     * @return of type boolean: True if the power production chosen do not need any choice. Otherwise False.
     */
    public boolean easyActive(){
        return getOfYourChoiceOutput()==0 && getOfYourChoiceInput()==0;
    }

    /**
     * Sum of production powers.
     * It's usefull for active production in parallel.
     *
     * @param other of type ProductionPower: adder.
     * @return of type ProductionPower: the sum of this and other
     */
    public ProductionPower add(ProductionPower other){
        return new ProductionPower(this.pointsFaithOut + other.pointsFaithOut, this.outputRes.add(other.outputRes), this.inputRes.add(other.inputRes), this.ofYourChoiceInput + other.ofYourChoiceInput, this.ofYourChoiceOutput + other.ofYourChoiceOutput);
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o is equals to this or if o.outputRes is equals to this.outputRes.
     *         False if param o isn't an instance of ProductionPower or o.pointsFaithOut isn't equals to this.pointsFaithOut.
     *         False if o.ofYourChoiceInput isn't equals to this.ofYourChoiceInput.
     *         False if o.ofYourChoiceOutput isn't equals to this.ofYourChoiceOutput.
     *         False if o.inputRes isn't equals to this.inputRes.
     *         False if o.outputRes isn't equals to this.outputRes.
     */
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