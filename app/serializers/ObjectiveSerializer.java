package serializers;

import java.lang.reflect.Type;

import models.Objective;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ObjectiveSerializer implements JsonSerializer<Objective> {
	  public JsonElement serialize(Objective src, Type typeOfSrc, JsonSerializationContext context) {
	    return new JsonPrimitive(new Gson().toJson(src));
	  }
}