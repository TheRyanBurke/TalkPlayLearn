package serializers;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import models.Statistics;
import models.Statistics.STATS;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StatisticsSerializer implements JsonSerializer<Statistics>{

	@Override
	public JsonElement serialize(Statistics arg0, Type arg1,
			JsonSerializationContext arg2) {
		
		JsonArray jso = new JsonArray();
		for(Entry<STATS, Integer> entry : arg0.populatedStatsMap().entrySet()) {
			jso.add(new JsonPrimitive("+" +  entry.getValue() + " " + entry.getKey().toString()));
		}
		
		return jso;
	}

}
