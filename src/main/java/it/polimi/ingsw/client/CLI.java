package it.polimi.ingsw.client;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.move.MoveType;
import it.polimi.ingsw.client.move.MovetypeMarket;
import it.polimi.ingsw.client.parser.StarterClient;

import java.io.*;

import java.util.Scanner;

public class CLI {
    private Game game;
    Scanner in = new Scanner(System.in);
    DataOutputStream socket;
    public CLI(Game game, DataOutputStream writer) {
        this.game = game;
        this.socket = writer;
    }

    public void print(Game game){
        this.game=game;
        System.out.println("Hello");
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
        String s = StarterClient.toJson(move, MoveType.class);
        send(s);
    }

    private void send(String s) {
        try {
            //out.reset();
            socket.writeUTF(s);
            socket.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
