package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.LeaderStatus;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {

    private DepotsAbility depotsability1 = new DepotsAbility(ResourceType.Coins);
    private WhiteAbility whiteAbility = new WhiteAbility(ResourceType.Coins);

    LeaderCard leaderCard1 = new LeaderCard(new Requirements(new NumberOfResources(1,1,0,0)), depotsability1, 5);
    LeaderCard leaderCard2 = new LeaderCard(new Requirements(new NumberOfResources(0,0,0,5)), whiteAbility, 2);

    LeaderCard[] leaderCards = new LeaderCard[] {leaderCard1,leaderCard2};

    PersonalBoard personalBoard = new PersonalBoard(leaderCards);

    Player player= new Player("Fabio", personalBoard, 0,new NumberOfResources(1,1,0,5));

    @Test
    void Constructor(){
        assertSame(LeaderStatus.onHand, leaderCard1.getStatus());
        if (leaderCard1.getVictoryPoints()<0) fail();
        assertNotNull(leaderCard1.getAbility());
        assertNotNull(leaderCard1.getRequirements());
    }

    LeaderCard[] ownedLeaderCard = new LeaderCard[2];
    @Test
    void SetPlayedTest(){
        try {
            ownedLeaderCard = personalBoard.getLeaderCards();
        } catch (NoMoreLeaderCardAliveException e) {
            e.printStackTrace();
        }
        ownedLeaderCard[0].setPlayed(player);
    }

    @Test
    void SetDiscardTest(){
        leaderCard2.setDiscard(player);
    }
}
