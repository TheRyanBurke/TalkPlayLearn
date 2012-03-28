package serializers;

import java.lang.reflect.Type;

import models.EngagedQuest;
import models.Quest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EngagedQuestSerializer implements JsonSerializer<EngagedQuest> {
	@Override
	  public JsonElement serialize(EngagedQuest src, Type typeOfSrc, JsonSerializationContext context) {
		  Gson gson = new GsonBuilder()
		  	.registerTypeAdapter(Quest.class, new QuestSerializer())
		  	.create();
		  String json = gson.toJson(src);
//		  json = "{\"allObjectivesCompleted\":" + src.allObjectivesCompleted() +
//				  "," + "\"quest\":" + gson.toJson(src.getQuest()) +
//	    			"," + json.substring(1, json.length());
		  String questJson = gson.toJson(src.getQuest());
		  JsonObject questJso = new JsonParser().parse(questJson).getAsJsonObject();
		  JsonObject jso = new JsonParser().parse(json).getAsJsonObject();
		  jso.add("quest", questJso);
		  jso.add("allObjectivesCompleted", new JsonPrimitive(src.allObjectivesCompleted()));
		  
		  JsonArray objArray = questJso.get("objectives").getAsJsonArray();
		  for(int i = 0; i < objArray.size(); i++) {
			  JsonObject j = objArray.get(i).getAsJsonObject();
			  j.add("objectiveProgress", new JsonPrimitive(src.objectiveProgress[i]));
			  j.add("percentComplete", new JsonPrimitive(100*src.objectiveProgress[i]/j.get("requiredCompletions").getAsInt()));			  
		  }
	    return jso;
	  }
}