package Server;

import com.google.gson.*;

import java.lang.reflect.Type;

public class Deserialize implements JsonDeserializer<Client> {

    @Override
    public Client deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject JsonObj = jsonElement.getAsJsonObject();
        JsonArray array;
        Client client = new Client();
        client.setNickName(JsonObj.get("NickName").getAsString());
        client.setStatus(JsonObj.get("Status").getAsString());
        array = JsonObj.getAsJsonArray("Pos");
        int[] pos = new int[2];
        pos[0] = array.get(0).getAsInt();
        pos[1] = array.get(1).getAsInt();
        client.setPos(pos);

        return client;
    }
}
