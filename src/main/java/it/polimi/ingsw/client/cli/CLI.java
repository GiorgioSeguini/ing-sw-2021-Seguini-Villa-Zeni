package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.message.ConnectionMessage;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

import java.util.*;

/**
 * CLI class.
 * Implements Runnable and UI interfaces.
 */
@SuppressWarnings("ALL")
public class CLI implements Runnable, UI {
    private final Client client;
    private GameClient game;
    final Scanner in = new Scanner(System.in);
    final ArrayList<CliInterface> moves;

    private boolean moveHandled;
    private final Object locker = new Object();
    private boolean connectionAccepted = false;

    private boolean printLorenzoMove=false;

    /**
     * Instantiates a new Cli.
     *
     * @param client of type Client: the client.
     */
    public CLI(Client client) {
        this.client = client;
        this.moves = new ArrayList<>();
    }

    /**
     * Manage the cli's beginning and the setting of a game.
     * Manage all the cli.
     */
    @Override
    public void run() {
        String name = null;
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
            if(!client.setOnline()){
                System.out.println("Oh no! Non siamo riusciti a contattare il server\n Riprova più tardi o controlla la tua connessione internet");
                return;
            }
            do {
                System.out.println("Benvenuto in Maestro del Rinascimento - Gioco made by CranioGames");
                System.out.println("Puoi decidere di creare una partita privata o di entrare in lista d'attesa per una partita pubblica. Come vuoi procedere? ");
                System.out.println("\t1. Partita privata.");
                System.out.println("\t2. Partita pubblica.");
                x = ReadFromKeyboard(in);
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

    /**
     * Print all the necessary information for a palyer.
     */
    public void print(){
        this.game = client.getSimpleGame();
        if(game==null) return;
        int myID = game.getMyID();
        if(game.getStatus()== GameStatus.Ended){
            rankShow();
        }
        else {
            if (game.isMyTurn()) {
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
                moves.add(new CliDisconnect(client));

                if (game.getMe().getErrorMessage() != ErrorMessage.NoError) {
                    System.out.println(game.getMe().getErrorMessage());
                }
                if (game != null && game.isSinglePlayer() && game.getSoloGame().getRevealed() != null && printLorenzoMove) {
                    System.out.println("MOSSA DI LORENZO");
                    System.out.println(game.getSoloGame().getRevealed().getTextToPrint());
                }

                MoveType move;
                do {
                    int index;
                    int i = 0;
                    ArrayList<CliInterface> ablemoves = new ArrayList<>();
                    System.out.println("----------------------------------------------------------");
                    System.out.println("È il tuo turno " + game.getMe().getUserName() + "! Sei all'interno della stanza " + client.getRoomName());
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

                        index = ReadFromKeyboard(in);

                        if (index < 1 || index > i - 1) {
                            System.out.println("Indice non valido!");
                        }

                    } while (index < 1 || index > i - 1);

                    CliInterface cliInterface = ablemoves.get(index - 1);
                    move = cliInterface.updateCLI(game, in);
                } while (move == null);
                send(move);
                printLorenzoMove = false;
                if (move instanceof MoveEndTurn) {
                    printLorenzoMove = true;
                }
            } else {
                clearScreen();
                System.out.println("E' il turno di :" + game.getCurrPlayer().getUserName());
            }
        }
    }

    /**
     * Print the end game. Show the winner and the rank.
     */
    private void rankShow() {
        System.out.println("IL GIOCO È TERMINATO!");
        if(game.getPlayers().size()>1) {
            System.out.println("\n Il vincitore è: " + game.getWinner().getUserName());

            System.out.println("\nClassifica");
            HashMap<String, Integer> rank = new HashMap<>();
            for (Player player : game.getPlayers()) {
                rank.put(player.getUserName(), player.getVictoryPoints());
            }

            rank = sortByValue(rank);
            for (int i = 1; i <= rank.size(); i++) {
                String name = (String) rank.keySet().toArray()[rank.size() - i];
                System.out.println("\t" + i + ". " + name + "\t punteggio: " + rank.get(name));
            }
        }
        else{
            try {
                System.out.println("\n HAI VINTO " + game.getWinner().getUserName()+"!");
            } catch (NullPointerException e) {
                System.out.println("HAI PERSO! LORENZO TI HA BATTUTO");
            }
        }

    }

    /**
     * Sorts hashmaps according to their values.
     * @param hm of type HashMap<String, Integer>: the hashmap.
     * @return of type HashMap<String, Integer>: the hashmap sorted.
     */
    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    /**
     * Manage the choice of the room and allow to create a new one.
     */
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
                x = ReadFromKeyboard(in);
                //in.nextLine();
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
                    numOfPlayers=ReadFromKeyboard(in);
                    //in.nextLine();
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

    /**
     * Manage the joining player and allow to choose the number of opponents the player want to play with.
     */
    private void PublicGame(){
        String name= null;
        int x= 0;
        do {
            do{
                System.out.println("Inserisci il tuo nome e premi INVIO");
                name = in.nextLine();
                System.out.println("Con quanti avversari vuoi giocare?\n 1. Da solo \n 2. Un avversario\n 3. Due avversari\n 4. Tre avversari\n");
                System.out.println("--> Digita il numero dell'opzione che preferisci e premi INVIO");
                x = ReadFromKeyboard(in);
                //in.nextLine();
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

    /**
     * Send a move to the server.
     * @param move of type MoveType: the move
     */
    private void send(MoveType move) {
        client.sendMove(move);
    }

    /**
     * Send a setupper to the server.
     * @param setupper of type SetUp: the setupper.
     */
    private void send(SetUp setupper){
        client.sendSetupper(setupper);
    }

    /**
     * Cleans the screen.
     */
    private void clearScreen(){
        for (int i=0; i<100; i++){
            System.out.println();
        }
    }

    /**
     * Updates the cli.
     */
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

    /**
     * Check if the move is handle.
     * @return of type boolean: movehandle.
     */
    private synchronized boolean isMoveHandled() {
        return moveHandled;
    }

    /**
     * Set moveHandled.
     * @param moveHandled of type boolean: the value to set.
     */
    private synchronized void setMoveHandled(boolean moveHandled) {
        this.moveHandled = moveHandled;
    }

    /**
     * Set a connection on active.
     */
    @Override
    public synchronized void setActive() {
        connectionAccepted = true;
    }

    /**
     * Print the connection message.
     * @param message of type ConnectionMessage: the message.
     */
    @Override
    public void printConnectionMessage(ConnectionMessage message) {
        System.out.println("--------------------------------------------");
        System.out.println(message);
        System.out.println("--------------------------------------------");
    }

    /**
     * Get connectionAccepted.
     *
     * @return of type bollean: True if the connection was accepeted. Otherwise false.
     */
    public synchronized boolean getActive(){
        return connectionAccepted;
    }

    /**
     * Reads an int from the keyboard.
     *
     * @param stdin of type Scanner: the input scanner.
     * @return of type int: the input number.
     */
    public static int ReadFromKeyboard(Scanner stdin){
        boolean check = false;
        String input;
        int INTinput=0;
        do{
            try{
                input = stdin.nextLine();
                INTinput = Integer.parseInt(input);
                check=true;
            }
            catch(Exception e){
                System.out.println("Write an integer number!");
            }

        }while(!check);
        return INTinput;
    }
}
