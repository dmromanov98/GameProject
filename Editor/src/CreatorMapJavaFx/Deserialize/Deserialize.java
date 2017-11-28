package CreatorMapJavaFx.Deserialize;

import Map.MapWrap;
import Physics.CollisionSpace;
import Physics.Rectangle;
import Wraps.Wrap;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Deserialize implements JsonDeserializer<MapWrap> {
    @Override
    public MapWrap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        MapWrap map = new MapWrap();
        JsonObject JsonObj = jsonElement.getAsJsonObject();
        JsonArray array = JsonObj.getAsJsonArray("objects");

        for (JsonElement obj : array) {
            Wrap wr = jsonDeserializationContext.deserialize(obj, Wrap.class);
            map.objects.add(wr);
        }

        JsonObject collisions = JsonObj.getAsJsonObject("collideAreas");

        map.collideAreas = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : collisions.entrySet()) {
            JsonObject obj = entry.getValue().getAsJsonObject();
            JsonArray collisionArray = obj.getAsJsonArray("collisions");
            CollisionSpace cs = new CollisionSpace();
            for (JsonElement element : collisionArray) {
                Rectangle rect = jsonDeserializationContext.deserialize(element, Rectangle.class);
                cs.addArea(rect);
            }
            map.collideAreas.put(entry.getKey(), cs);
        }

        return map;
    }
}
