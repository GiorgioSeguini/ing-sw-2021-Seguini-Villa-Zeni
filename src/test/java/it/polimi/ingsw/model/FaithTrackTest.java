package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest{

    @Test
    void goodSetup(){
        FaithTrack curr = new FaithTrack(0);
        assertEquals(curr.getFaithPoints(), 0);
        try{
            new FaithTrack(-5);
            fail();
        }catch(IllegalArgumentException ignored){};

        for(int i=0; i<100; i++) {
            curr.addPoint();
        }
        assertEquals(curr.getFaithPoints(), 24);


    }

    @Test
    void testAdd(){
        FaithTrack curr = new FaithTrack(4);
        assertEquals(curr.getFaithPoints(), 4);

        for(int i=0; i<5; i++)
            curr.addPoint();

        assertEquals(curr.getFaithPoints(), 9);
    }

}