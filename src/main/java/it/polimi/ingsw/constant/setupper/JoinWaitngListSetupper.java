package it.polimi.ingsw.constant.setupper;

/**
 * Message sent to joing a public room.
 * @see it.polimi.ingsw.constant.setupper.SetUp
 */
public class JoinWaitngListSetupper extends SetUp{

    public static final String className="JoinWaitingListSetupper";

    /**
     * Default constuctor.
     * Notice: is a pblic room, so the room name is set by the server.
     * @param playerName
     * @param numOfPlayer
     */
    public JoinWaitngListSetupper(String playerName, int numOfPlayer) {
        super(playerName, null, numOfPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }
}
