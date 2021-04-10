package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    @Test
    void Constructor() {

        MarbleColor[] startMarbles = new MarbleColor[]{MarbleColor.Red, MarbleColor.Purple, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Blue};
        Market tray = new Market(startMarbles);


    }

    @Test
    void GetterTest() {
        MarbleColor[] startMarbles = new MarbleColor[]{MarbleColor.Red, MarbleColor.Purple, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Blue};
        Market tray = new Market(startMarbles);
        //controlla che non accetta valori di colonna e riga maggiori o uguali di 4 e 3
        try {
            tray.getColumn(4);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.getRow(3);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.getColumn(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.getRow(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}

        //Controlla che non accetti input negativi
        try {
            tray.getRow(-1);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.getColumn(-1);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}

        //controlla che restituiscono array di dimensione 3 e 4 e che non restituisca mai array con elementi nulli
        for(int i=0; i<3; i++) {
            for (MarbleColor x : tray.getRow(i)) {
                assertEquals(4,tray.getRow(i).length);
                assertNotNull(x);
            }
            assertArrayEquals(tray.getRow(i), tray.getRow(i)); //controllo che non venga modificata la riga dopo il get
        }                                                      //non so se ha senso controllare questa cosa, idem sotto :')
        for(int i=0; i<4; i++) {
            for (MarbleColor x : tray.getColumn(i)) {
                assertEquals(3,tray.getColumn(i).length);
                assertNotNull(x);
            }
            assertArrayEquals(tray.getColumn(i), tray.getColumn(i)); //controllo che non venga modificata la colonna dopo il get
        }
    }

    @Test
    void BuyerTest() {
        MarbleColor[] startMarbles = new MarbleColor[]{MarbleColor.Red, MarbleColor.Purple, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Red, MarbleColor.Blue};
        Market tray = new Market(startMarbles);
        //controlla che non accetta valori di colonna e riga maggiori o uguali di 4 e 3
        try {
            tray.buyColumn(4);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.buyRow(3);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.buyColumn(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.buyRow(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}

        //Controlla che non accetti input negativi
        try {
            tray.buyRow(-1);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            tray.buyColumn(-1);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}

         //controllo sulle dimensioni della lista ritornata dopo il buy
        assertEquals(3, tray.buyColumn(1).size());
        //controllo sul corretto inserimento dell'external marble
        assertEquals(tray.getColumn(1)[2], MarbleColor.Blue);
        //controllo sulle dimensioni della lista ritornata dopo il buy
        assertEquals(4, tray.buyRow(0).size());
        //controllo sul corretto inserimento dell'external marble
        assertEquals(tray.getRow(0)[3], MarbleColor.Purple);

    }
}
