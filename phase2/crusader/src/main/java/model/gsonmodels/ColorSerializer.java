package model.gsonmodels;

import com.google.gson.*;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;

public class ColorSerializer implements JsonSerializer<Color> {
    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext context) {
        String hexCode = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        return new JsonPrimitive(hexCode);
    }
}