package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.setupper.DisconnectConnectionSetupper;

import java.util.Scanner;

public class CliDisconnect implements CliInterface{

    private final Client client;

    public CliDisconnect(Client client){
        this.client= client;
    }

    @Override
    public MoveType updateCLI(GameClient game, Scanner in) {
        System.out.println("Vuoi per caso uscire dal gioco? ");
        int index = 0;
        do{
            System.out.println("\t 1. SI, voglio uscire dal gioco.");
            System.out.println("\t 2. NO.");
            index=CLI.ReadFromKeyboard(in);
            if(index<1||index>2) System.out.println("Indice non valido.");
        }while (index<1|| index>2);

        if (index==1){
            client.sendSetupper(new DisconnectConnectionSetupper(game.getMe().getUserName(), client.getRoomName()));
            return new MoveEndTurn(game.getMyID());
        }else return null;
    }

    @Override
    public boolean canPerform(GameClient game) {
        return true;
    }

    @Override
    public String getName() {
        return "Close Game";
    }
}
