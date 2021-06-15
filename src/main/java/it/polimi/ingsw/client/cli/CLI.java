package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.message.LastMessage;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.setupper.*;

import java.io.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CLI implements Runnable, UI {
    private final Client client;
    private GameClient game;
    Scanner in = new Scanner(System.in);
    ArrayList<CliInterface> moves;

    private boolean moveHandled;
    private final Object locker = new Object();
    private boolean connectionAccepted = false;


    public CLI(Client client) {
        this.client = client;
        this.moves = new ArrayList<>();
    }

    @Override
    public void run() {
        String name = null;
        SetUp setupper;
        int x;

        this.setMoveHandled(false);
        System.out.println("Inserisci offline per giocare in single player offline");
        String temp = in.nextLine();
        if(temp.equals("offline")) {
            System.out.println("Inserisci il tuo nome e premi INVIO");
            name = in.nextLine();
            client.startOffline(name);
        }
        else{
            try {
                client.setOnline();
            } catch (IOException e) {
                e.printStackTrace();
            }
            do {
                System.out.println("Benvenuto in Maestro del Rinascimento - Gioco made by CranioGames");
                System.out.println("Puoi decidere di creare una partita privata o di entrare in lista d'attesa per una partita pubblica. Come vuoi procedere? ");
                System.out.println("\t1. Partita privata.");
                System.out.println("\t2. Partita pubblica.");
                x = in.nextInt();
                in.nextLine();
                if (x < 1 || x > 2) {
                    System.out.println("Indice non valido!");
                }
            } while (x < 1 || x > 2);

            if(x==1){
                RoomGame();
            }else {
                PublicGame();
            }
        }

        while(client.isActive()){
            synchronized (locker){
                while(!isMoveHandled()) {
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                print();
                this.setMoveHandled(false);
                locker.notifyAll();
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
            moves.add(new CliPrint());

            if(game.getMe().getErrorMessage()!= ErrorMessage.NoError){
                System.out.println(game.getMe().getErrorMessage());
            }

            MoveType move;
            do {
                int index;
                int i = 0;
                ArrayList<CliInterface> ablemoves = new ArrayList<>();
                System.out.println("----------------------------------------------------------");
                System.out.println("È il tuo turno "+ game.getMe().getUserName()+"! Sei all'interno della stanza "+client.getRoomName());
                System.out.println("Cosa desideri fare?");
                do {
                    ablemoves.clear();
                    i = 1;
                    for (CliInterface clitype : moves) {
                        if (clitype.canPerform(game)) {
                            System.out.println(i + ". " + clitype.getName());
                            i++;
                            ablemoves.add(clitype);
                        }

                    }

                    index = in.nextInt();

                    if (index < 1 || index > i - 1) {
                        System.out.println("Indice non valido!");
                    }

                } while (index < 1 || index > i - 1);

                CliInterface cliInterface = ablemoves.get(index - 1);
                move = cliInterface.updateCLI(game, in);
            }while (move==null);
            send(move);
            if(move instanceof MoveEndTurn){
                System.out.println("Vuoi per caso uscire dal gioco? ");
                int index = 0;
                do{
                    System.out.println("\t 1. SI, voglio uscire dal gioco.");
                    System.out.println("\t 2. NO.");
                    index=in.nextInt();
                    in.nextLine();
                    if(index<1||index>2) System.out.println("Indice non valido.");
                }while (index<1|| index>2);

                if (index==1){
                    send(new DisconnectConnectionSetupper(game.getMe().getUserName(), client.getRoomName()));
                }
            }
        }
        else{
            clearScreen();
            System.out.println("E' il turno di :" + game.getCurrPlayer().getUserName());
        }
    }

    
    private void RoomGame(){
        int x;
        String name;
        String room=null;
        SetUp setupper = null;
        System.out.println("Ottimo! Hai deciso di giocare una partita privata!");
        int numOfPlayers = 0;
        do {
            do {
                System.out.println("Puoi decidere se creare una nuova stanza di gioco o unirti ad una esistente. Come vogliamo procedere? ");
                System.out.println("\t1. Crea nuova stanza. ");
                System.out.println("\t2. Unisciti ad una stanza.");
                x = in.nextInt();
                in.nextLine();
                if(x<1||x>2) System.out.println("Indice non valido");
            }while (x<1||x>2);
            if(x==1){
                System.out.println("Ok! Creiamo una nuova stanza!");
            }else{
                System.out.println("Ok! Uniamoci ad una stanza!");
            }

            System.out.print("Inserisci il tuo nome: ");
            name= in.nextLine();
            System.out.print("Inserisci il nome della stanza: ");
            room= in.nextLine();
            if(x==1){
                do{
                    System.out.print("Quanti giocatori vuoi unire alla stanza? (da 1 a 4 giocatori): ");
                    numOfPlayers=in.nextInt();
                    in.nextLine();
                    if(numOfPlayers<1||numOfPlayers>4) System.out.println("Indice non valido");
                }while(numOfPlayers<1 || numOfPlayers>4);
                setupper= new CreateRoomSetupper(name, room, numOfPlayers);
            }
            else{
                setupper= new LinkToRoomSetupper(name, room);
            }
            send(setupper);
            synchronized (locker) {
                while (!isMoveHandled()) {
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                setMoveHandled(false);
            }
            if (!getActive()) {
                if(x==1){
                    System.out.println("Ops! Qualcosa è andato storto! Sembra che il nome della stanza sia già in uso.\n");
                }
                else {
                    System.out.println("Ops! Qualcosa è andato storto! Verifica i seguenti problemi:");
                    System.out.println("\t1. La stanza nella quale vuoi accedere ha una partita già in corso");
                    System.out.println("\t2. La stanza che stai cercando è inesistente");
                    System.out.println("\t3. Il nome con la quale stai provando ad accedere è già stato usato da un altro utente in questa stanza");
                }
                in.nextLine();      //non so perchè ma senza non va
            }
        }while(!getActive());

        if(x==1){
            System.out.println("Ottimo "+name+"! Ti stiamo inserendo in una partita da "+numOfPlayers+" giocatori.\nRimani in attesa, la partita inizierà tra breve!\n");
            System.out.println("RICORDA! Per invitare i tuoi amici comunicagli il nome della stanza! --> "+room);
        }
        else {
            System.out.println("Ottimo "+name+"! Sei stato inserito all'interno della stanza "+room+"\nRimani in attesa, la partita inizierà tra breve!\n");
        }


    }
    
    private void PublicGame(){
        String name= null;
        int x= 0;
        do {
            do{
                System.out.println("Inserisci il tuo nome e premi INVIO");
                name = in.nextLine();
                System.out.println("Con quanti avversari vuoi giocare?\n 1. Da solo \n 2. Un avversario\n 3. Due avversari\n 4. Tre avversari\n");
                System.out.println("--> Digita il numero dell'opzione che preferisci e premi INVIO");
                x = in.nextInt();
                in.nextLine();
                if (x < 1 || x > 4) {
                    System.out.println("Indice non valido!");
                }
            }while (x < 1 || x > 4);
            send(new JoinWaitngListSetupper(name,x));
            synchronized (locker) {
                while (!isMoveHandled()) {
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                setMoveHandled(false);
            }
            if (!getActive()) {
                System.out.println("Ops! Qualcosa è andato storto! Il tuo nome è già in uso!");
                in.nextLine();      //non so perchè ma senza non va
            }
        }while(!getActive());
        System.out.println("Ottimo "+name+"! Ti stiamo inserendo in una partita da "+x+" giocatori.\nRimani in attesa, la partita inizierà tra breve!");
    }

    private void send(MoveType move) {
        client.sendMove(move);
    }

    private void send(SetUp setupper){
        client.sendSetupper(setupper);
    }

    private void clearScreen(){
        for (int i=0; i<100; i++){
            System.out.println();
        }
    }

    @Override
    public void update() {
        synchronized (locker) {
            while(isMoveHandled()) {
                try {
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setMoveHandled(true);
            locker.notifyAll();
        }
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
