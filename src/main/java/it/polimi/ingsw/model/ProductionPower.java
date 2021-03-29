package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

public class ProductionPower {

    private int PointsFaithOut;
    private NumberOfResources outputRes;
    private NumberOfResources inputRes;
    private int OfYourChoiceInput;
    private int OfYourChoiceOutput;


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

    /**
     *  It's usefull for active production in parallel
     * @param other adder
     * @return return the sum of this and other
     */
    public ProductionPower add(ProductionPower other){
        ProductionPower res = new ProductionPower();
        res.inputRes= this.inputRes.add(other.inputRes);
        res.outputRes = this.outputRes.add(other.outputRes);
        res.PointsFaithOut = this.PointsFaithOut + other.PointsFaithOut;
        res.OfYourChoiceInput = this.OfYourChoiceInput + other.OfYourChoiceInput;
        res.OfYourChoiceOutput = this.OfYourChoiceOutput + other.OfYourChoiceOutput

        return res;
    }

/* L'idea è che il controller sommi tutte le production power che il giocatore vuole attivare --> un solo controllo sul deposito,
poi il controller chiama la active con un solo argomento, se va a buon fine è super felice,
se torno l'eccezione della scelta( dobbiamo decidere il nome), il controllore interagisce con il giocatore e dopo chiama la funzione ative con 3 argomenti che fa quello che deve fare
 */

    public void active(Player owner) throws{        //TODO definire una eccezione e capire bene
            active(owner, new NumberOfResources(), new NumberOfResources());
    }

    public void active(Player owner, NumberOfResources choiceInput, NumberOfResources choiceOutput){
        if(OfYourChoiceInput != choiceInput.size() || OfYourChoiceOutput !=choiceOutput.size()){
            throw //TODO definire un eccezione per la richiesta delle scelte
        }

        owner.getDepots().subResource(this.inputRes.add(choiceInput));
        owner.getDepots().addResourceFromProduction(this.outputRes.add(choiceOutput));

        for(int i=0; i< this.PointsFaithOut; i++)
            owner.getFaithTrack().addPoint();
        //TODO definire la convenzione per i controlli papali
    }

}