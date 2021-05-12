package it.polimi.ingsw.client;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.LeaderCard;
import it.polimi.ingsw.client.modelClient.NumberOfResources;
import it.polimi.ingsw.client.modelClient.PersonalBoard;
import it.polimi.ingsw.client.move.MoveChoseInitialResources;
import it.polimi.ingsw.client.move.MoveChoseLeaderCards;
import it.polimi.ingsw.client.move.MoveType;
import it.polimi.ingsw.client.move.MovetypeMarket;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CLI implements Runnable{
    private final Client client;
    private Game game;
    Scanner in = new Scanner(System.in);
    DataOutputStream socket;
    public CLI(Client client, DataOutputStream writer) {
        this.client = client;
        this.socket = writer;
    }

    @Override
    public void run(){
        this.game = client.getSimpleGame();
        System.out.println("Hello");
        if(game.getStatus()== GameStatus.Initial){
            System.out.println("Fai le tue scelte iniziali");
            if(!game.getPlayerFromID(game.getMyID()).getPersonalBoard().isReady()){
                initialLeaderCard();
            }else if(game.getInitialResources()>game.getPlayerFromID(game.getMyID()).getDepots().getResources().size()){
                initialResources();
            }

        }
        if(game.isMyTurn()){
            System.out.println("It's your turn");
            System.out.println("cosa desideri fare?");
            System.out.println("1: vedere stato" +
                    "2: scegliere una mossa");
            int choice = in.nextInt();
            switch (choice){
                case 1:
                    //TODO
                    break;
                case 2:
                    perforMove();
                    break;
                default:
                    System.out.println("Mossa non valida");
                    break;
            }
        }
    }

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
                market();
                break;
            //TODO
            default:
                System.out.println("Mossa non valida");
                break;
        }
    }

    private void market(){
        System.out.println(game.getMarketTray());
        System.out.println("Vuoi comprare una riga o una colonna?");
        int index = 0;
        String c = in.nextLine();
        switch(c) {
            case "r":
            case "riga":
                index = 0;
                break;
            case "c":
            case "colonna":
                index = 4;
                break;
            default:
                System.out.println("Invalid choice");

        }

        System.out.println("Inserisci l'indice desiderato");
        int temp = in.nextInt();
        if(temp<0 || temp>7){
            System.out.println("Invalid choice");
        }
        index+=temp;
        MoveType move = new MovetypeMarket(game.getMyID(), index);
        send(move);
    }

    private void initialLeaderCard(){
        System.out.println("Scegli "+ PersonalBoard.MAX_LEAD_CARD + " carte leader tra le seguenti:");
        for(LeaderCard card : game.getLeaderCards()){
            System.out.println(card);
        }
        ArrayList<Integer> choice = new ArrayList<>();
        while(choice.size() < PersonalBoard.MAX_LEAD_CARD){
            int index = in.nextInt();
            if(index>0 && index<game.getLeaderCards().size()){
                choice.add(game.getLeaderCards().get(index).getId());
            }else{
                System.out.println("Indice non valido");
            }
        }
        MoveType move = new MoveChoseLeaderCards(game.getMyID(), choice);
        send(move);
    }

    private void initialResources(){
        System.out.println("Scegli le risorse iniziali:");
        NumberOfResources resources= new NumberOfResources();

        for(ResourceType type : ResourceType.values()){
            System.out.println(type);
        }
        while(resources.size()< game.getInitialResources()){
            int index = in.nextInt();
            if(index>0 && index-1 < ResourceType.values().length){
                resources.add(ResourceType.values()[index], 1);
            }
        }
        MoveType move = new MoveChoseInitialResources(game.getMyID(), resources);
        send(move);
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
