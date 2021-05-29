package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.move.MoveType;

import java.io.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CLI implements Runnable, UI {
    private final Client client;
    private GameClient game;
    Scanner in = new Scanner(System.in);
    DataOutputStream socket;
    ArrayList<CliInterface> moves;

    private boolean moveHandled;
    private boolean connectionAccepted= false;


    public CLI(Client client, DataOutputStream writer) {
        this.client = client;
        this.socket = writer;
        this.moves = new ArrayList<>();
    }

    @Override
    public void run() {
        String name;
        int x;
        do {
            this.setMoveHandled(false);
            System.out.println("Inserisci il tuo nome e premi INVIO");
            name = in.nextLine();
            do {
                System.out.println("Con quanti avversari vuoi giocare?\n 1. Da solo \n 2. Un avversario\n 3. Due avversari\n 4. Tre avversari\n");
                System.out.println("--> Digita il numero dell'opzione che preferisci e premi INVIO");
                x = in.nextInt();
                if (x < 1 || x > 4) {
                    System.out.println("Indice non valido!");
                }
            } while (x < 1 || x > 4);
            try {
                socket.writeUTF(name);
                socket.flush();
                socket.writeInt(x);
                socket.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!isMoveHandled()){
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!getActive()){
                System.out.println("Nome già in uso, per favore scegli un altro nome\n");
                in.nextLine();      //non so perchè ma senza non va
            }
        }while (!getActive());
        System.out.println("Ottimo "+name+"! Ti stiamo inserendo in una partita da "+x+" giocatori.\nRimani in attesa, la partita inizierà tra breve!");
        while(client.isActive()){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isMoveHandled()){
                print();
                this.setMoveHandled(false);
            }
        }
    }

    public void print(){
        this.game = client.getSimpleGame();
        if(game==null) return;

        int myID = game.getMyID();
        if(game.isMyTurn()){
            moves.clear();
            moves.add(new CliChoseInitialResources(myID));
            moves.add(new CliChoseLeaderCard(myID));
            moves.add(new CliMoveActiveProduction(myID));
            moves.add(new CliMoveBuyDevCard(myID));
            moves.add(new CliMoveChoseResources(myID));
            moves.add(new CliMoveDiscardResources(myID));
            moves.add(new CliMoveEndTurn(myID));
            moves.add(new CliMoveLeader(myID));
            moves.add(new CliMoveTypeMarket(myID));
            moves.add(new CliMoveWhiteConversion(myID));

            if(game.getMe().getErrorMessage()!= ErrorMessage.NoError){
                System.out.println(game.getMe().getErrorMessage());
            }

            int index;
            int i=0;
            ArrayList<CliInterface> ablemoves= new ArrayList<>();
            System.out.println("----------------------------------------------------------");
            System.out.println("È il tuo turno!");
            System.out.println("Cosa desideri fare?");
            do {
                ablemoves.clear();
                i=1;
                for (CliInterface clitype: moves){
                    if(clitype.canPerform(game)){
                        System.out.println(i+". "+clitype.getName());
                        i++;
                        ablemoves.add(clitype);
                    }

                }

                index=in.nextInt();

                if(index<1 || index>i-1){
                    System.out.println("Indice non valido!");
                }

            }while(index<1 || index>i-1);

            CliInterface cliInterface = ablemoves.get(index - 1);
            MoveType move = cliInterface.updateCLI(game, in);
            send(move);
            //ho tolto l'ultimo if perchè ho sempre almeno una mossa da fare che è la endturn
            //altrimenti qui mettiamo un if che riempe in automato la endturn
        }
        else{
            clearScreen();
            System.out.println("E' il turno di :" + game.getCurrPlayer().getUserName());
        }
    }


    private void send(MoveType move) {
        try {
            String s = StarterClient.toJson(move, MoveType.class);
            System.out.println(s); // TODO: 5/22/21 aggiunto ora 
            //out.reset();
            socket.writeUTF(s);
            socket.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void clearScreen(){
        for (int i=0; i<100; i++){
            System.out.println();
        }
    }

    @Override
    public void update() {
        setMoveHandled(true);
    }

    private synchronized boolean isMoveHandled() {
        return moveHandled;
    }

    private synchronized void setMoveHandled(boolean moveHandled) {
        this.moveHandled = moveHandled;
    }

    @Override
    public synchronized void setActive() {
        connectionAccepted = true;
    }

    public synchronized boolean getActive(){
        return connectionAccepted;
    }
}
