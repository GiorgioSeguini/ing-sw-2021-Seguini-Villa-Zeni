package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import it.polimi.ingsw.server.network.CreateRoomSetupperExt;
import it.polimi.ingsw.server.network.JoinWaitingListSetupperExt;
import it.polimi.ingsw.server.network.LinkToRoomSetupperExt;
import it.polimi.ingsw.server.network.Settable;

import java.lang.reflect.Type;
import java.util.HashMap;

public class SettableSerializer implements JsonDeserializer<Settable> {

    private static HashMap<String, Class> setupperName= new HashMap<>();
    private static final String CLASSNAME= "CLASSNAME";
    private static final String INSTANCE= "INSTANCE";

    static {
        setupperName.put(CreateRoomSetupperExt.className, CreateRoomSetupperExt.class);
        setupperName.put(JoinWaitingListSetupperExt.className, JoinWaitingListSetupperExt.class);
        setupperName.put(LinkToRoomSetupperExt.className,LinkToRoomSetupperExt.class);
    }

    @Override
    public Settable deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = setupperName.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

}
