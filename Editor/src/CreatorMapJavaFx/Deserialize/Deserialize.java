package CreatorMapJavaFx.Deserialize;

import Map.MapWrap;
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

//        JsonObject jsonObject = JsonObj.getAsJsonObject("collideAreas");
//        System.out.println(jsonObject);
//        JsonObject collisions = jsonElement.getAsJsonObject();
//        array = collisions.getAsJsonArray("collideAreas");
//
//        for(JsonElement obj:array){
//
//            //TODO: collideAreasDESERIALIZATION
//        }

        return map;
    }
}