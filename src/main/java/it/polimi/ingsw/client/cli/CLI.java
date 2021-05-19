package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.move.MoveType;

import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CLI implements Runnable{
    private final Client client;
    private GameClient game;
    Scanner in = new Scanner(System.in);
    DataOutputStream socket;
    ArrayList<CliInterface> moves;

    public CLI(Client client, DataOutputStream writer) {
        this.client = client;
        this.socket = writer;
        this.moves = new ArrayList<>();
    }

    @Override
    public void run() {
        while(client.isActive()){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(client.recived){
                update();
                client.recived=false;
            }
        }
    }

    public void update(){
        this.game = client.getSimpleGame();
        if(game==null) return;

        boolean moveAviable = false;
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
            boolean goodchoice = false;
            int index = -1;
            System.out.println("Hello");
            System.out.println("cosa desideri fare?");
            do {
                for (int i=0; i< moves.size(); i++) {
                    if (moves.get(i).canPerform(game)) {
                        System.out.println((i + 1) + ": " + moves.get(i).getName());
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
                CliInterface cliInterface = moves.get(index);
                MoveType move = cliInterface.updateCLI(game, in);
                send(move);
            }
        }
        else{
            System.out.println("E' il turno di :" + game.getCurrPlayer().getUserName());
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
