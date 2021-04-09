package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    @Test
    void Constructor(){
        Market tray = new Market();


        assertEquals(3,tray.buyColumn(1).size());
        assertEquals(4,tray.buyRow(0));
    }
}
