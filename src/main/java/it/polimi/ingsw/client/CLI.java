package it.polimi.ingsw.client;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.LeaderCard;
import it.polimi.ingsw.client.modelClient.NumberOfResources;
import it.polimi.ingsw.client.modelClient.PersonalBoard;
import it.polimi.ingsw.client.move.*;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private final Client client;
    private Game game;
    Scanner in = new Scanner(System.in);
    DataOutputStream socket;
    ArrayList<MoveType> moves;

    public CLI(Client client, DataOutputStream writer) {
        this.client = client;
        this.socket = writer;
        this.moves = new ArrayList<>();
    }


    public void run(){
        this.game = client.getSimpleGame();
        if(game==null) return;

        boolean moveAviable = false;
        int myID = game.getMyID();
        if(game.isMyTurn()){
            moves.clear();
            moves.add(new MoveActiveProduction(myID));
            moves.add(new MoveChoseInitialResources(myID));
            moves.add(new MoveChoseLeaderCards(myID));
            moves.add( new MovetypeMarket(myID));
            moves.add(new MoveEndTurn(myID));
            //TODO aggiungere tutti o trovare un modo più efficente

            boolean goodchoice = false;
            int index = -1;
            System.out.println("Hello");
            System.out.println("cosa desideri fare?");
            do {
                for (int i=0; i< moves.size(); i++) {
                    if (moves.get(i).canPerform(game)) {
                        System.out.println((i + 1) + ": " + moves.get(i).getClassName());
                        moveAviable = true;
                    }
                }
                if(!moveAviable)
                    break;
                index = in.nextInt() -1;
                try {
                    if (moves.get(index).canPerform(game))
                        goodchoice = true;
                } catch (IndexOutOfBoundsException ignored) {
                }
                if (!goodchoice) System.out.println("Invalid index!");
            }while(!goodchoice);

            if(moveAviable) {
                MoveType move = moves.get(index);
                move.updateCLI(game, in);
                send(move);
            }
        }
        else{
            System.out.println("E' il turno di :" + game.getCurrPlayer().getUserName());
        }

            /*if(!game.getPlayerFromID(game.getMyID()).getPersonalBoard().isReady()){
                initialLeaderCard();
            }else if(game.getInitialResources()>game.getPlayerFromID(game.getMyID()).getDepots().getResources().size()){
                initialResources();
            }*/
    }

    //da rimuovere
    private void perforMove() {
        System.out.println("Scegli che mossa fare: " +
                "1: attiva carta leader" +
                "2: compra dal mercato" +
                "3: attiva produzione" +
                "4: compra carta sviluppo");
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                //TODO
                break;
            case 2:

                break;
            //TODO
            default:
                System.out.println("Mossa non valida");
                break;
        }
    }


    private void send(MoveType move) {
        try {
            String s = StarterClient.toJson(move, MoveType.class);
            //out.reset();
            socket.writeUTF(s);
            socket.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
