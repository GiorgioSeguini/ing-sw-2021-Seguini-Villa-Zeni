package it.polimi.ingsw.model.exception;

public class HaveToChooseException extends Exception{

    int whitemarbles=0;

    public HaveToChooseException(){
    }
    public HaveToChooseException(int whitemarbles){
        this.whitemarbles=whitemarbles;
    }

    public int getWhitemarbles() {
        return whitemarbles;
    }
}
