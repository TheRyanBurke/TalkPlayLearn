package serializers;

import java.lang.reflect.Type;

import models.Quest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class QuestSerializer implements JsonSerializer<Quest> {
	@Override
	  public JsonElement serialize(Quest src, Type typeOfSrc, JsonSerializationContext context) {
		  
		  Gson gson = new GsonBuilder()
		  	.addSerializationExclusionStrategy(new ObjectiveExclusionStrategy())
		  	.create();
		  String json = gson.toJson(src); 
				  
//		  json = "{\"totalXP\":"+ src.totalXP() +
//				  ",\"totalXPWithBonus\":"+ src.totalXPWithBonus() +
//				  "," + json.substring(1, json.length());
		  
		  JsonObject jso = new JsonParser().parse(json).getAsJsonObject();
		  jso.add("totalXP", new JsonPrimitive(src.totalXP()));
		  jso.add("totalXPWithBonus", new JsonPrimitive(src.totalXPWithBonus()));
		  
	    return jso;
	  }
}