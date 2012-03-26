package serializers;

import java.lang.reflect.Type;

import models.Objective;
import models.Quest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QuestSerializer implements JsonSerializer<Quest> {
	  public JsonElement serialize(Quest src, Type typeOfSrc, JsonSerializationContext context) {
		  
		  Gson gson = new GsonBuilder()
		  	.addSerializationExclusionStrategy(new ObjectiveExclusionStrategy())
		  	.create();
		  String json = gson.toJson(src); 
				  
		  json = "{\"totalXP\":"+ src.totalXP() +
				  ",\"totalXPWithBonus\":"+ src.totalXPWithBonus() +
				  "," + json.substring(1, json.length());
	    return new JsonPrimitive(json);
	  }
}