package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.server.network.*;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * SettableSerializer class.
 * Implements JsonDeserializer<Settable> interface.
 * Manage starting messages (public or private game) and disconnecting messages.
 */
public class SettableSerializer implements JsonDeserializer<Settable> {

    private static final HashMap<String, Class> setupperName= new HashMap<>();
    private static final String CLASSNAME= "CLASSNAME";
    private static final String INSTANCE= "INSTANCE";

    static {
        setupperName.put(CreateRoomSetupperExt.className, CreateRoomSetupperExt.class);
        setupperName.put(JoinWaitingListSetupperExt.className, JoinWaitingListSetupperExt.class);
        setupperName.put(LinkToRoomSetupperExt.className,LinkToRoomSetupperExt.class);
        setupperName.put(DisconnectConnectionSetupperExt.className, DisconnectConnectionSetupperExt.class);
    }

    /**
     * Method that deserialize a JsonElement.
     * @param json of type JsonElement: the JsonElement that has to be deserialized.
     * @param type of type Type
     * @param context of type JsonDeserializationContext
     * @return of type Settable: the settable deserialized.
     * @throws JsonParseException
     */
    @Override
    public Settable deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = setupperName.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

}
