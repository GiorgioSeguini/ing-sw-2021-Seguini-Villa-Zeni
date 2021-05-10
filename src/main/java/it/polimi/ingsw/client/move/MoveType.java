package it.polimi.ingsw.client.move;

public abstract class MoveType {

    private final int idPlayer;
    private final String ClassName;

    public MoveType(int idPlayer, String className){
        this.idPlayer = idPlayer;
        this.ClassName=className;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public String getClassName() {
        return ClassName;
    }

    @Override
    public boolean equals(Object other){
        if(other==this)
            return true;

        if(!(other instanceof MoveType))
            return false;

        if(this.idPlayer!= ((MoveType) other).getIdPlayer()){
            return false;
        }

        return this.ClassName.equals(((MoveType) other).getClassName());
    }

}
