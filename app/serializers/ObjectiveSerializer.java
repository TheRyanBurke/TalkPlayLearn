package serializers;

import java.lang.reflect.Type;

import models.Objective;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ObjectiveSerializer implements JsonSerializer<Objective> {
	  public JsonElement serialize(Objective src, Type typeOfSrc, JsonSerializationContext context) {
		  String json = "{\"description\":"+ src.description +
				  ",\"xp\":"+ src.xp +
				  ",\"requiredCompletions\":" + src.requiredCompletions +
				  ",\"bonus\":" + src.bonus + "}";
	    return new JsonPrimitive(json);
	  }
}