package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

public class ProductionPower {

    private int PointsFaithOut;
    private NumberOfResources outputRes;
    private NumberOfResources inputRes;

    /*Default constructor*/
    ProductionPower(int points, NumberOfResources outputRes, NumberOfResources inputRes) {
        this.PointsFaithOut=points;
        this.outputRes=outputRes;
        this.inputRes=inputRes;
    }
    ProductionPower(NumberOfResources outputRes, NumberOfResources inputRes){
        this.outputRes=outputRes;
        this.inputRes=inputRes;
    }

    /*Getter*/
    public int getFaithPointsOut() {
        return this.PointsFaithOut;
    }

    public NumberOfResources getInputRes() {
        return this.inputRes;
    }

    public NumberOfResources getOutputRes() {
        return this.outputRes;
    }

}