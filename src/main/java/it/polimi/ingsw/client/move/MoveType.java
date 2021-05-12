package it.polimi.ingsw.client.move;

public abstract class MoveType {

    private final int idPlayer;

    public MoveType(int idPlayer){
        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public abstract String getClassName();

    @Override
    public boolean equals(Object other){
        if(other==this)
            return true;

        if(!(other instanceof MoveType))
            return false;

        return this.idPlayer == ((MoveType) other).getIdPlayer();
        //TODO rivedere
    }

}
