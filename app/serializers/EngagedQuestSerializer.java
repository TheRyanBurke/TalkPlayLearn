package serializers;

import java.lang.reflect.Type;

import models.EngagedQuest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EngagedQuestSerializer implements JsonSerializer<EngagedQuest> {
	@Override
	  public JsonElement serialize(EngagedQuest src, Type typeOfSrc, JsonSerializationContext context) {
		  Gson gson = new Gson();
		  String json = gson.toJson(src);
		  json = "{\"allObjectivesCompleted\":" + src.allObjectivesCompleted() +
				  "," + "\"quest\":" + gson.toJson(src.getQuest()) +
	    			"," + json.substring(1, json.length());
	    return new JsonPrimitive(src.toString());
	  }
}