package it.polimi.ingsw.constant.setupper;

public class JoinWaitngListSetupper extends SetUp{

    public static final String className="JoinWaitingListSetupper";

    public JoinWaitngListSetupper(String playerName, int numOfPlayer) {
        super(playerName, null, numOfPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }
}
