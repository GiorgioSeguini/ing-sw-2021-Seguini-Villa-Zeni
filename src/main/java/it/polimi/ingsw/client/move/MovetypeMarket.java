package it.polimi.ingsw.client.move;

public class MovetypeMarket extends MoveType {

    private final int indexToBuy;
    public static final String className= "MovetypeMarket";

    public MovetypeMarket(int idPlayer, int indextobuy){
        super(idPlayer);
        this.indexToBuy=indextobuy;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
