package CreatorMapJavaFx.Deserialize;

import Physics.Rectangle;
import com.google.gson.*;

import java.lang.reflect.Type;

public class DeserializeRectangles implements JsonDeserializer<Rectangle> {
    @Override
    public Rectangle deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Rectangle rect = new Gson().fromJson(jsonElement, Rectangle.class);
        return rect;
    }
}
