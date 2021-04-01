package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.model.exception.ChoseResourcesException;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.PopesInspectionException;

public class ProductionPower {

    private int PointsFaithOut;
    private NumberOfResources outputRes;
    private NumberOfResources inputRes;
    private int OfYourChoiceInput;
    private int OfYourChoiceOutput;


    /*Default constructor*/
    public ProductionPower(int points, NumberOfResources outputRes, NumberOfResources inputRes, int ofYourChoiceInput, int ofYourChoiceOutput){
        this.PointsFaithOut=points;
        this.outputRes=outputRes;
        this.inputRes=inputRes;
        this.OfYourChoiceInput = ofYourChoiceInput;
        this.OfYourChoiceOutput = ofYourChoiceOutput;
    }
    public ProductionPower(int points, NumberOfResources outputRes, NumberOfResources inputRes){
        new ProductionPower(points, outputRes, inputRes, 0, 0);
    }
    public ProductionPower(NumberOfResources outputRes, NumberOfResources inputRes){
        new ProductionPower(0, outputRes, inputRes, 0, 0);
    }
    public ProductionPower(){
        new ProductionPower(0, new NumberOfResources(), new NumberOfResources(), 0, 0);
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

    /**
     *  It's usefull for active production in parallel
     * @param other adder
     * @return return the sum of this and other
     */
    public ProductionPower add(ProductionPower other){
        return new ProductionPower(this.PointsFaithOut + other.PointsFaithOut , this.outputRes.add(other.outputRes), this.inputRes.add(other.inputRes), this.OfYourChoiceInput + other.OfYourChoiceInput, this.OfYourChoiceOutput + other.OfYourChoiceOutput);
    }

/* L'idea è che il controller sommi tutte le production power che il giocatore vuole attivare --> un solo controllo sul deposito,
poi il controller chiama la active con un solo argomento, se va a buon fine è super felice,
se torno l'eccezione della scelta( dobbiamo decidere il nome), il controllore interagisce con il giocatore e dopo chiama la funzione ative con 3 argomenti che fa quello che deve fare
 */

    public void active(Player owner) throws OutOfResourcesException, ChoseResourcesException, PopesInspectionException {
            active(owner, new NumberOfResources(), new NumberOfResources());
    }

    public void active(Player owner, NumberOfResources choiceInput, NumberOfResources choiceOutput) throws OutOfResourcesException, ChoseResourcesException, PopesInspectionException {
        if(OfYourChoiceInput != choiceInput.size() || OfYourChoiceOutput !=choiceOutput.size()){
            throw new ChoseResourcesException(OfYourChoiceInput,OfYourChoiceOutput);
        }

        owner.getDepots().subResource(this.inputRes.add(choiceInput));
        owner.getDepots().addResourceFromProduction(this.outputRes.add(choiceOutput));

        PopesInspectionException temp = null;
        for(int i=0; i< this.PointsFaithOut; i++){
            try{
                owner.getFaithTrack().addPoint();
            }catch(PopesInspectionException e){
                temp = e;
            }
        }

        if(temp != null) throw temp;
    }

}