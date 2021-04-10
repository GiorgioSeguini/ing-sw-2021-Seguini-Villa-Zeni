package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    @Test
    void Constructor() {

        MarbleColor[] startMarbles = new MarbleColor[]{MarbleColor.Red, MarbleColor.Purple, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Blue};
        Market tray = new Market(startMarbles);


        assertEquals(3, tray.buyColumn(1).size());
        assertEquals(tray.getColumn(1)[2], MarbleColor.Blue);
        assertEquals(4, tray.buyRow(0).size());
        assertEquals(tray.getRow(0)[3], MarbleColor.Purple);


    }

    @Test
    void GetterTest() {
        MarbleColor[] startMarbles = new MarbleColor[]{MarbleColor.Red, MarbleColor.Purple, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Blue};
        Market tray = new Market(startMarbles);
        try {
            tray.getColumn(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.getRow(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}

    }
}
