package CreatorMapJavaFx.Deserializers;

import Main.Transform;
import Map.MapWrap;
import Wraps.BackgroundWrap;
import Wraps.DecalWrap;
import Wraps.Wrap;
import com.google.gson.*;

import java.lang.reflect.Type;

public class DeserializeObject implements JsonDeserializer<Wrap> {
    @Override
    public Wrap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Wrap wrap = null;
        JsonObject JsonObj = jsonElement.getAsJsonObject();
        String wrapName = JsonObj.get("texName").getAsString();
        String wName = wrapName.substring(0, wrapName.lastIndexOf('\\'));
        JsonElement element = JsonObj.get("transform");

        if(wName.equals("decals")){

            Transform transform = jsonDeserializationContext.deserialize(element, Transform.class);

            //transform.layer = .99f;

            wrap = new DecalWrap(transform,wrapName);

        }else if (wName.equals("backgrounds")){
            wrap = new BackgroundWrap(wrapName,JsonObj.get("layer").getAsFloat());
            //wrap = new BackgroundWrap(wrapName,.99f);
        }else if(wName.equals("sprites")){

            //TODO: SPRITES DESERIALIZER

        }

        return wrap;
    }
}
