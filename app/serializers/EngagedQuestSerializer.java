package serializers;

import java.lang.reflect.Type;

import models.EngagedQuest;
import models.Objective;
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
	public JsonElement serialize(EngagedQuest src, Type typeOfSrc,
			JsonSerializationContext context) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Quest.class, new QuestSerializer())
				.addSerializationExclusionStrategy(
						new EngagedQuestExclusionStrategy()).create();
		String json = gson.toJson(src);
		// json = "{\"allObjectivesCompleted\":" + src.allObjectivesCompleted()
		// +
		// "," + "\"quest\":" + gson.toJson(src.getQuest()) +
		// "," + json.substring(1, json.length());
		String questJson = gson.toJson(src.quest);
		JsonObject questJso = new JsonParser().parse(questJson)
				.getAsJsonObject();
		JsonObject engagedQuestJso = new JsonParser().parse(json)
				.getAsJsonObject();
		engagedQuestJso.add("quest", questJso);
		String completeLabel = src.completed ? "complete" : "incomplete";
		engagedQuestJso.add("complete", new JsonPrimitive(completeLabel));
		questJso.add("allObjectivesComplete",
				(src.allObjectivesCompleted()) ? new JsonPrimitive(
						"completeEligible") : new JsonPrimitive(
						"completeIneligible"));

		JsonArray objArray = questJso.get("objectives").getAsJsonArray();
		for (int i = 0; i < objArray.size(); i++) {
			JsonObject j = objArray.get(i).getAsJsonObject();
			Objective o = Objective.findById(j.get("id").getAsLong());
			j.add("objectiveProgress", new JsonPrimitive(src.progress.get(o)));
			j.add("percentComplete",
					new JsonPrimitive(100 * src.progress.get(o)
							/ j.get("requiredCompletions").getAsInt()));
		}
		return engagedQuestJso;
	}
}