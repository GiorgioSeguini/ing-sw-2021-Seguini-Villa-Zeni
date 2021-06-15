package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.FaithTrack;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.ArrayList;
import java.util.Scanner;

public class CliPrint implements CliInterface{
    public static String className="PrintGame";
    private static ArrayList<String> prints;

    static{
        prints= new ArrayList<>();
        prints.add("Risorse totali");
        prints.add("Tracciato Fede");
        prints.add("Mie LeaderCard");
        prints.add("Carte Sviluppo Acquistate");
        prints.add("DashBoard");
        prints.add("Market");
        prints.add("Lo stato di un altro giocatore");
    }

    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        int index;
        boolean end=false;
        do {
            for(int i=0; i<100;i++){
                System.out.println("");
            }
            showPrints();
            do {
                index=stdin.nextInt();
                if(index<0 || index>prints.size()){
                    System.out.println("Indice non valido. Riprova");
                }
            }while (index<0||index>prints.size());

            switch (index){
                case 1: printTotalRes(game); break;
                case 2: printFaithTrack(game); break;
                case 3: printLeaderCards(game); break;
                case 4: printOwnedDevCard(game); break;
                case 5: printDashBoard(game); break;
                case 6: printMarket(game); break;
                case 7: printPlayer(game); break;
                default: break;
            }

            System.out.println("\nVuoi terminare la stampa? \n\t1. SI\n\t2. NO");
            index=stdin.nextInt();
            if (index==1){
                end=true;
            }
        }while (end==false);
        return null;
    }


    @Override
    public boolean canPerform(GameClient game) {
        PlayerStatus status= game.getMe().getStatus();
        if(status.equals(PlayerStatus.NeedToStore) || game.getMe().getStatus().equals(PlayerStatus.NeedToChoseRes)){
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return className;
    }

    private void showPrints(){
        System.out.println("Sei entrato nella sezione di stampa. Cosa vuoi stampare?");
        int i=0;
        for (String print: prints){
            i++;
            System.out.println("\t"+i+". "+print);
        }
    }

    private void printPlayer(GameClient game) {
        Scanner scanner= new Scanner(System.in);
        ArrayList<Player> enemies= new ArrayList<>();
        System.out.println("Hai deciso di stampare un altro giocatore. Che giocatore vuoi stampare? ");
        int i;
        do{
            i=1;
            for(Player player: game.getPlayers()){
                if(!player.equals(game.getMe())){
                    System.out.println("\t"+i+". "+player.getUserName());
                    enemies.add(player);
                    i++;
                }
            }
            i=scanner.nextInt();
            if(i<0 || i>game.getPlayers().size()-1){
                System.out.println("Indice non valido.");
            }
        }while (i<0 || i>game.getPlayers().size()-1);
        System.out.println(enemies.get(i-1));
    }

    private void printMarket(GameClient game) {
        System.out.println("MARKET");
        System.out.println(game.getMarketTray());
    }

    private void printDashBoard(GameClient game) {
        System.out.println("DASHBOARD");
        System.out.println(game.getDashboard());
    }

    private void printOwnedDevCard(GameClient game) {
        System.out.println("MIE CARTE SVILUPPO");
        if(game.getMe().getPersonalBoard().getOwnedDevCards().size()!=0) {
            for (DevelopmentCard card : game.getMe().getPersonalBoard().getOwnedDevCards()) {
                System.out.println(card);
            }

            System.out.println("\nCARTE ATTIVE");
            for (DevelopmentCard card : game.getMe().getPersonalBoard().getActiveOwnedDevCards()) {
                System.out.println(card);
            }
        }
        else {
            System.out.println("Non hai ancora carte Sviluppo");
        }
    }

    private void printLeaderCards(GameClient game) {
        System.out.println("LEADER CARD");
        int i=0;
        for(LeaderCard card: game.getMe().getPersonalBoard().getLeaderCards()){
            i++;
            System.out.println(i+".");
            System.out.println(card);
        }
        if(i==0) System.out.println("Non hai Carte Leader al momento");
    }

    private void printFaithTrack(GameClient game) {
        System.out.println("TRACCIATO FEDE");

        FaithTrack track= game.getMe().getFaithTrack();
        int[] popes=track.getPopesFavorPosition();
        int[] popesint=track.getPopesFavorInitialPositionPosition();
        System.out.print("Posizioni Favori Papali: ");
        for(int x: popes){
            System.out.print("\t"+x);
        }
        System.out.print("\nInizio Posizioni Favori Papali: ");
        for(int x: popesint){
            System.out.print("\t"+x);
        }
        System.out.println("");
        for (Player player: game.getPlayers()){
            if(player.equals(game.getMe())){
                System.out.println("Miei punti ");
            }
            else{
                System.out.println("Punti giocatore "+player.getUserName());
            }
            System.out.println(player.getFaithTrack());
        }
    }

    private void printTotalRes(GameClient game) {
        System.out.println("RISORSE TOTALI");
        System.out.println(game.getMe().getDepots().getStrongBox());
        System.out.println("\n"+game.getMe().getDepots().getWareHouseDepots());
    }
}
