package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.MoveChoose;

import javax.swing.text.View;

public class MoveLeader extends MoveType{

    int move;
    LeaderCard leaderCard;

    public MoveLeader(Player player, View view, boolean isLastMove, int move, LeaderCard leaderCard) {
        super(player, view, MoveChoose.LeaderMove, isLastMove);
        this.move = move;
        this.leaderCard = leaderCard;
    }
}