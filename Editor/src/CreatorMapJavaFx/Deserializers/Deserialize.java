package CreatorMapJavaFx.Deserializers;

import Map.MapWrap;
import Map.Map;
import Wraps.Wrap;
import com.google.gson.*;

import java.lang.reflect.Type;

public class Deserialize implements JsonDeserializer<MapWrap> {
    @Override
    public MapWrap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        MapWrap map = new MapWrap();
        JsonObject JsonObj = jsonElement.getAsJsonObject();
        JsonArray array = JsonObj.getAsJsonArray("objects");

        for(JsonElement obj:array){
            Wrap wr = jsonDeserializationContext.deserialize(obj, Wrap.class);
            map.objects.add(wr);
        }

        return map;
    }
}
