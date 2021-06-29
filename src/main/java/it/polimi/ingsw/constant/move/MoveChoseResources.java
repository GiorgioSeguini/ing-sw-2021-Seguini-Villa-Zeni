package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;

/**
 * The type Move chose of your choice resources for production.
 * If player wants to active a production with some of choice input or output resources will fall in a state that required this move to be performed
 */
public class MoveChoseResources extends MoveType {
    
    /**
     * The constant className.
     */
    public static final String className= "MoveChoseResources";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToChoseRes};

    private NumberOfResources ofYourChoiceInput;
    private NumberOfResources ofYourChoiceOutput;
    /**
     * Instantiates a new Move chose resources.
     *
     * @param idPlayer the id player
     */
    public MoveChoseResources(int idPlayer) {
        super(idPlayer);
    }

    /**
     * @see MoveType#getClassName() 
     */
    @Override
    public String getClassName() {
        return className;
    }

    /**
     * Gets the input of choice resources, default is null.
     *
     * @return NumberOfResources 
     */
    public NumberOfResources getOfYourChoiceInput() {
        return ofYourChoiceInput;
    }

    /**
     * Gets the output of choice resources, , default is null.
     *
     * @return NumberOfResources
     */
    public NumberOfResources getOfYourChoiceOutput() {
        return ofYourChoiceOutput;
    }

    /**
     * Sets the input of choice resources.
     *
     * @param ofYourChoiceInput  the input of choice resources
     */
    public void setOfYourChoiceInput(NumberOfResources ofYourChoiceInput) {
        this.ofYourChoiceInput = ofYourChoiceInput;
    }

    /**
     * Sets the output of choice resources.
     *
     * @param ofYourChoiceOutput the output of choice resources
     */
    public void setOfYourChoiceOutput(NumberOfResources ofYourChoiceOutput) {
        this.ofYourChoiceOutput = ofYourChoiceOutput;
    }

    /**
     * @see MoveType#simpleCheck(Game, PlayerStatus[]) called 
     * @see MoveType#canPerform(Game) 
     */
    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }
}
