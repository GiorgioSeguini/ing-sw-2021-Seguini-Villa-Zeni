package it.polimi.ingsw.constant.setupper;

public class DisconnectConnectionSetupper extends SetUp{
    public static final String className="DisconnectConnectionSetupper";

    @Override
    public String getClassName() {
        return className;
    }

    public DisconnectConnectionSetupper(String playerName, String roomName) {
        super(playerName, roomName, 0);
    }
}
