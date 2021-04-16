package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest{

    @Test
    void goodSetup(){
        FaithTrack curr = new FaithTrack();
        assertEquals(curr.getFaithPoints(), 0);

        for(int i=0; i<100; i++) {
            curr.addPoint();
        }
        assertEquals(curr.getFaithPoints(), 24);


    }

    @Test
    void testAdd(){
        FaithTrack curr = new FaithTrack();
        assertEquals(curr.getFaithPoints(), 0);

        for(int i=0; i<5; i++)
            curr.addPoint();

        assertEquals(curr.getFaithPoints(), 5);
    }

}