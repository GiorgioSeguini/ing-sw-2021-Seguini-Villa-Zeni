package it.polimi.ingsw.model.exception;

public class PopesInspectionException extends Exception{
    private final int index;

    public PopesInspectionException(int index){
        this.index=index;
    }

    public int getIndex(){
        return index;
    }

}
