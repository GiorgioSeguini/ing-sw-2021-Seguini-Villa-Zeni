package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.PersonalBoard;
import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * CliChoseLeaderCard class.
 * Implements CliInterface.
 * Manage the chose leader card move on the cli.
 */
public class CliChoseLeaderCard implements CliInterface {

    private final MoveChoseLeaderCards move;

    /**
     * Instantiates a new Cli chose leader card.
     *
     * @param myId of type int: the player's id.
     */
    public CliChoseLeaderCard(int myId) {
        this.move = new MoveChoseLeaderCards(myId);
    }

    /**
     * Update cli.
     * @param game of type GameClient: the game.
     * @param in of type Scanner:  the input scanner.
     * @return of type MoveType: the move.
     */
    @Override
    public MoveType updateCLI(GameClient game, Scanner in){
        System.out.println("LISTA DELLE CARTE");
        int t=1;
        for(LeaderCard card : game.getLeaderCards()){
            System.out.println("CARTA "+t+"\n"+card);
            t++;
        }
        System.out.println("---> Ricorda! Per scegliere digita il numero della carta che vuoi selezionare e poi premi INVIO");
        System.out.println("Scegli "+ PersonalBoard.MAX_LEAD_CARD + " carte leader tra le seguenti:");

        ArrayList<Integer> choice = new ArrayList<>();
        while(choice.size() < PersonalBoard.MAX_LEAD_CARD){
            int index = CLI.ReadFromKeyboard(in);
            if(index>0 && index<=game.getLeaderCards().size() && !choice.contains(game.getLeaderCards().get(index-1).getId())){
                choice.add(game.getLeaderCards().get(index-1).getId());

            }else{
                System.out.println("Indice non valido");
            }
        }
        move.setIndexLeaderCards(choice);
        return move;
    }

    /**
     * @see MoveType#canPerform(Game)
     */
    @Override
    public boolean canPerform(GameClient game) {
        return move.canPerform(game);
    }

    /**
     *
     * @see MoveType#getClassName()
     */
    @Override
    public String getName() {
        return move.getClassName();
    }
}
