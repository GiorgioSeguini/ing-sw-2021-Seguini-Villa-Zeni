package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.ChoseResourcesException;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionPowerTest {

    @Test
    void SimplerProduction(){

        NumberOfResources input = new NumberOfResources(0, 0, 3, 0);
        NumberOfResources output = new NumberOfResources(3, 3, 3, 3);
        ProductionPower p = new ProductionPower(output, input);

        assertEquals(p.getFaithPointsOut(), 0);
        assertEquals(input, p.getInputRes());
        assertEquals(output, p.getOutputRes());
        assertEquals(0, p.getOfYourChoiceInput());
        assertEquals(0, p.getOfYourChoiceOutput());

        Player owner = new Player("Pippo");

        try {
            owner.getDepots().addResourcesFromMarket(input);
        } catch (UnableToFillException e) {
            fail();
        }

        try {
            p.active(owner);
        } catch (OutOfResourcesException | ChoseResourcesException e) {
            fail();
        }

        assertEquals(output, owner.getDepots().getResources());

        p = p.add(p);

        try{
            p.active(owner);
            fail();
        }catch (OutOfResourcesException e) {
            assertTrue(true);
        } catch (ChoseResourcesException ignored) {
            fail();
        }

        owner.getDepots().addResourceFromProduction(input);
        owner.getDepots().addResourceFromProduction(input);

        try {
            p.active(owner);
        } catch (OutOfResourcesException | ChoseResourcesException e) {
            fail();
        }

        assertEquals(output.add(output).add(output), owner.getDepots().getResources());
    }

    @Test
    void FaithPoint(){

        NumberOfResources input = new NumberOfResources(1, 1, 1, 0);
        NumberOfResources output = new NumberOfResources(0, 3, 3, 3);

        try{
            new ProductionPower(-1, input, output);
            fail();
        }catch(ArithmeticException ignored){}

        int faithPoints = 5;
        ProductionPower p = new ProductionPower(faithPoints, output, input);

        assertEquals(faithPoints, p.getFaithPointsOut());
        assertEquals(input, p.getInputRes());
        assertEquals(output, p.getOutputRes());
        assertEquals(0, p.getOfYourChoiceInput());
        assertEquals(0, p.getOfYourChoiceOutput());

        Player owner = new Player("Pippo");

        try {
            owner.getDepots().addResourcesFromMarket(input);
        } catch (UnableToFillException e) {
            fail();
        }

        try {
            p.active(owner);
        } catch (OutOfResourcesException | ChoseResourcesException e) {
            fail();
        }

        assertEquals(output, owner.getDepots().getResources());
        assertEquals(faithPoints, owner.getFaithTrack().getFaithPoints());

        p = p.add(p);

        try{
            p.active(owner);
            fail();
        }catch (OutOfResourcesException e) {
            assertTrue(true);
        } catch (ChoseResourcesException ignored) {
            fail();
        }

        owner.getDepots().addResourceFromProduction(input);
        owner.getDepots().addResourceFromProduction(input);

        try {
            p.active(owner);
        } catch (OutOfResourcesException | ChoseResourcesException e) {
            fail();
        }

        assertEquals(output.add(output).add(output), owner.getDepots().getResources());
        assertEquals(3*faithPoints, owner.getFaithTrack().getFaithPoints());

    }


    @Test
    void ofYourChoice(){
        NumberOfResources input = new NumberOfResources(3, 0, 1, 0);
        NumberOfResources output = new NumberOfResources(0, 3, 3, 3);

        try{
            new ProductionPower(0, input, output, -1, 0);
            fail();
        }catch(ArithmeticException ignored){}

        try{
            new ProductionPower(0, input, output, 0, -1);
            fail();
        }catch(ArithmeticException ignored){}

        int faithPoints = 5;
        int choiceInput = 1;
        int choiceOutput = 2;
        NumberOfResources choice1 = new NumberOfResources(0, 1, 0, 0);
        NumberOfResources choice2 = new NumberOfResources(0, 1, 1, 0);
        ProductionPower p = new ProductionPower(faithPoints, output, input, choiceInput, choiceOutput);

        assertEquals(faithPoints, p.getFaithPointsOut());
        assertEquals(input, p.getInputRes());
        assertEquals(output, p.getOutputRes());
        assertEquals(choiceInput, p.getOfYourChoiceInput());
        assertEquals(choiceOutput, p.getOfYourChoiceOutput());

        Player owner = new Player("Pippo");

        try {
            owner.getDepots().addResourcesFromMarket(input.add(choice1));
        } catch (UnableToFillException e) {
            fail();
        }
        try {
            p.active(owner);
            fail();
        } catch (OutOfResourcesException e) {
            fail();
        }catch (ChoseResourcesException e){
            assertTrue(true);
        }

        try {
            p.active(owner,choice1, choice2);

        } catch (OutOfResourcesException | ChoseResourcesException e) {
            fail();
        }

        assertEquals(output.add(choice2), owner.getDepots().getResources());
        assertEquals(faithPoints, owner.getFaithTrack().getFaithPoints());

        p = p.add(p);

        try{
            p.active(owner);
            fail();
        }catch (OutOfResourcesException e) {
            fail();
        } catch (ChoseResourcesException ignored) {
            assertTrue(true);
        }

        owner.getDepots().addResourceFromProduction(input.add(choice1));
        owner.getDepots().addResourceFromProduction(input.add(choice1));

        try {
            p.active(owner, choice1.add(choice1), choice2.add(choice2));
        } catch (OutOfResourcesException | ChoseResourcesException e) {
            fail();
        }

        assertEquals(output.add(output).add(output).add(choice2).add(choice2).add(choice2), owner.getDepots().getResources());
        assertEquals(3*faithPoints, owner.getFaithTrack().getFaithPoints());

    }

    @Test
    void equals(){
        NumberOfResources input = new NumberOfResources(3, 0, 1, 0);
        NumberOfResources input1 = new NumberOfResources();
        NumberOfResources output = new NumberOfResources(0, 3, 3, 3);
        NumberOfResources output1 = new NumberOfResources();

        ProductionPower power = new ProductionPower(0, input, output, 0, 0);

        assertEquals(power, power);
        assertEquals(new ProductionPower(), new ProductionPower());
        assertEquals(power, new ProductionPower(0, input, output, 0, 0));
        assertNotEquals(power, new Object());
        assertNotEquals(power, new ProductionPower(1, input, output, 0, 0));
        assertNotEquals(power, new ProductionPower(0, input1, output, 0, 0));
        assertNotEquals(power, new ProductionPower(0, input, output1, 0, 0));
        assertNotEquals(power, new ProductionPower(0, input, output, 1, 0));
        assertNotEquals(power, new ProductionPower(0, input, output, 0, 1));

        assertTrue(power.easyActive());

        assertFalse(new ProductionPower(0, input, output, 1, 0).easyActive());
        assertFalse(new ProductionPower(0, input, output, 0, 1).easyActive());
        assertFalse(new ProductionPower(0, input, output, 1, 1).easyActive());
    }


}