package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.server.model.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest{

    @Test
    void choseResources(){
        ChoseResourcesException e = new ChoseResourcesException(3, 2);
        assertEquals(ErrorMessage.ChooseResourceError, e.getErrorMessage());
        assertEquals(3, e.getOfYourChoiceInput());
        assertEquals(2, e.getOfYourChoiceOutput());
    }

    @Test
    void HaveToChoose(){
        HaveToChooseException e = new HaveToChooseException();
        assertEquals(ErrorMessage.HaveToChooseError, e.getErrorMessage());
    }

    @Test
    void NoMoreLeaderCardAlive(){
        NoMoreLeaderCardAliveException e = new NoMoreLeaderCardAliveException();
        assertEquals(ErrorMessage.NoMoreLeaderCardAliveError, e.getErrorMessage());
    }

    @Test
    void NoSpace(){
        NoSpaceException e = new NoSpaceException();
        assertEquals(ErrorMessage.NoSpaceError, e.getErrorMessage());
    }

    @Test
    void OutOfResources(){
        OutOfResourcesException e = new OutOfResourcesException();
        assertEquals(ErrorMessage.OutOfResourcesError, e.getErrorMessage());
    }

    @Test
    void UnableToFill(){
        UnableToFillException e = new UnableToFillException();
        assertEquals(ErrorMessage.UnableToFillError, e.getErrorMessage());
    }

}