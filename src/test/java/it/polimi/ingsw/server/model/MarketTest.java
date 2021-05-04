package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.Starter;
import it.polimi.ingsw.server.model.Market;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class MarketTest {


    @Test
    void Constructor() {
        ArrayList<MarbleColor> startMarbles = new ArrayList<>();

        try {
            startMarbles = Starter.MarblesParser();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        Market marketTray = new Market(startMarbles);

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                assertNotNull(marketTray.tray[i][j]);
            }
        }
        assertNotNull(marketTray.externalMarble);

    }

    @Test
    void GetterTest() {
        ArrayList<MarbleColor> startMarbles = new ArrayList<>();

        try {
            startMarbles = Starter.MarblesParser();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        Market marketTray = new Market(startMarbles);

        //controlla che non accetta valori di colonna e riga maggiori o uguali di 4 e 3
        try {
            marketTray.getColumn(4);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            marketTray.getRow(3);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            marketTray.getColumn(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            marketTray.getRow(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}

        //Controlla che non accetti input negativi
        try {
            marketTray.getRow(-1);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            marketTray.getColumn(-1);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}

        //controlla che restituiscono array di dimensione 3 e 4 e che non restituisca mai array con elementi nulli
        for(int i=0; i<3; i++) {
            for (MarbleColor x : marketTray.getRow(i)) {
                assertEquals(4,marketTray.getRow(i).length);
                assertNotNull(x);
            }
            assertArrayEquals(marketTray.getRow(i), marketTray.getRow(i)); //controllo che non venga modificata la riga dopo il get
        }                                                      //non so se ha senso controllare questa cosa, idem sotto :')
        for(int i=0; i<4; i++) {
            for (MarbleColor x : marketTray.getColumn(i)) {
                assertEquals(3,marketTray.getColumn(i).length);
                assertNotNull(x);
            }
            assertArrayEquals(marketTray.getColumn(i), marketTray.getColumn(i)); //controllo che non venga modificata la colonna dopo il get
        }
    }

    @Test
    void BuyerTest() {
        ArrayList<MarbleColor> startMarbles = new ArrayList<>();

        try {
            startMarbles = Starter.MarblesParser();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        Market marketTray = new Market(startMarbles);


        //controlla che non accetta valori di colonna e riga maggiori o uguali di 4 e 3
        try {
            marketTray.buyColumn(4);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            marketTray.buyRow(3);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            marketTray.buyColumn(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            marketTray.buyRow(5);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}

        //Controlla che non accetti input negativi
        try {
            marketTray.buyRow(-1);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}
        try {
            marketTray.buyColumn(-1);
            fail();
        }catch (IndexOutOfBoundsException ignored) {}


        ArrayList<MarbleColor> buyedColumn;
        MarbleColor oldExtMarble;
        for(int i=0; i<4; i++){
            oldExtMarble = marketTray.externalMarble;
            buyedColumn = marketTray.buyColumn(i);
            for(MarbleColor x: buyedColumn){
                assertNotNull(x); //ogni elemento restituito dalla buy non è mai nullo
            }
            assertEquals(3, buyedColumn.size()); //controllo sulle dimensioni della lista ritornata dopo il buy
            assertNotNull(marketTray.externalMarble);//externalMarble mai nulla
            assertEquals(marketTray.externalMarble,buyedColumn.get(0)); //controllo sul corretto inserimento dell'external marble
            assertEquals(oldExtMarble,marketTray.getColumn(i)[marketTray.getColumn(i).length-1]); //controllo sul corretto inserimento dell'external marble
        }

        ArrayList<MarbleColor> buyedRow;
        for(int i=0; i<3; i++){
            oldExtMarble = marketTray.externalMarble;
            buyedRow = marketTray.buyRow(i);
            for(MarbleColor x: buyedRow){
                assertNotNull(x); //ogni elemento restituito dalla buy non è mai nullo
            }
            assertEquals(4, buyedRow.size()); //controllo sulle dimensioni della lista ritornata dopo il buy
            assertNotNull(marketTray.externalMarble);//externalMarble mai nulla
            assertEquals(marketTray.externalMarble,buyedRow.get(0)); //controllo sul corretto inserimento dell'external marble
            assertEquals(oldExtMarble,marketTray.getRow(i)[marketTray.getRow(i).length-1]); //controllo sul corretto inserimento dell'external marble
        }

    }


}
