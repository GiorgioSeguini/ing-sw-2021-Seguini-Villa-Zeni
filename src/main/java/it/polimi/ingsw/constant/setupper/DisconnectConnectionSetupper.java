package it.polimi.ingsw.constant.setupper;

/**Message sent to menage a disconnection
 * @see it.polimi.ingsw.constant.setupper.SetUp
 */
public class DisconnectConnectionSetupper extends SetUp{
    public static final String className="DisconnectConnectionSetupper";

    @Override
    public String getClassName() {
        return className;
    }

    /**
     * Default constructor.
     * @param playerName
     * @param roomName
     */
    public DisconnectConnectionSetupper(String playerName, String roomName) {
        super(playerName, roomName, 0);
    }
}
