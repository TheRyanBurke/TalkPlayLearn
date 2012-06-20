package serializers;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import models.EngagedQuest;
import models.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UserSerializer implements JsonSerializer<User> {

	@Override
	public JsonElement serialize(User arg0, Type arg1, JsonSerializationContext arg2) {
		Gson gson = new GsonBuilder()
	  		.registerTypeAdapter(EngagedQuest.class, new EngagedQuestSerializer())
	  		.create();
		String json = gson.toJson(arg0);
//		json = "{\"xpToLevel\":" + arg0.currentLevelXPCap() +
//    			",\"xpToLevelPercent\":" + (100*arg0.xp/arg0.currentLevelXPCap()) +
//    			"," + json.substring(1, json.length());
		JsonObject jso = new JsonParser().parse(json).getAsJsonObject();
		jso.add("xpToLevel", new JsonPrimitive(arg0.currentLevelXPCap()));
		jso.add("xpToLevelPercent", new JsonPrimitive(100*arg0.xp/arg0.currentLevelXPCap()));
		
		JsonObject statList = new JsonObject();
		for(Entry<String, Integer> e : arg0.stats.entrySet()) {
			statList.add(e.getKey(), new JsonPrimitive(e.getValue()));
		}
		jso.add("statList", statList);
		
		return jso;
	}

}
