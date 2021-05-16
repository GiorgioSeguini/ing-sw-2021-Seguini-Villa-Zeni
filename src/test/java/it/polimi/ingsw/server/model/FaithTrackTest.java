package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.FaithTrackExt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest{

    @Test
    void goodSetup(){
        FaithTrackExt curr = new FaithTrackExt(0);
        System.out.println(curr);
        assertEquals(curr.getFaithPoints(), 0);

        for(int i=0; i<100; i++) {
            curr.addPoint();
        }
        assertEquals(curr.getFaithPoints(), 24);


    }

    @Test
    void testAdd(){
        FaithTrackExt curr = new FaithTrackExt(0);
        assertEquals(curr.getFaithPoints(), 0);

        for(int i=0; i<5; i++)
            curr.addPoint();

        assertEquals(curr.getFaithPoints(), 5);
        assertEquals(-1, curr.inspectionNeed());

        for(int i=0; i<3; i++)
            curr.addPoint();

        assertEquals(curr.getFaithPoints(), 8);
        assertEquals(0, curr.inspectionNeed());

        assertEquals(2, curr.getVictoryPoints());
        curr.popeInspection(0);
        assertEquals(4, curr.getVictoryPoints());

        curr.popeInspection(1);
        assertEquals(4, curr.getVictoryPoints());

        assertFalse(curr.isEnd());

        for(int i=0; i<16; i++)
            curr.addPoint();

        assertTrue(curr.isEnd());

    }


}