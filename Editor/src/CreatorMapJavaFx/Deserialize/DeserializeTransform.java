package CreatorMapJavaFx.Deserialize;

import Main.Transform;
import com.google.gson.*;

import java.lang.reflect.Type;

public class DeserializeTransform implements JsonDeserializer<Transform> {
    @Override
    public Transform deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Transform transform = new Gson().fromJson(jsonElement, Transform.class);
        return transform;
    }
}
