package model.gsonmodels;

import com.google.gson.*;
import javafx.scene.paint.Color;
import java.lang.reflect.Type;

public class ColorDeserializer implements JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String hexCode = json.getAsString();
        return Color.web(hexCode);
    }
}