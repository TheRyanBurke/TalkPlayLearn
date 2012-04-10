package controllers;

import java.util.List;

import models.Quest;
import models.Statistics;
import play.mvc.Controller;
import serializers.QuestSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class QuestController extends Controller {
	
	public static void getQuests() {
		List<Quest> quests = Quest.all().fetch();
		GsonBuilder gsonb = new GsonBuilder();
		gsonb.registerTypeAdapter(Quest.class, new QuestSerializer());
		Gson gson = gsonb.create();
		
		renderJSON(gson.toJson(quests));
	}
	
	public static void newQuest() {
		render();
	}
	
	public static void create(String quest, String rewards, String objectives) {
		Gson gson = new Gson();
		Quest q = gson.fromJson(quest, Quest.class);
		
		q.rewards = gson.fromJson(rewards, Statistics.class);
		
		q.objectives = gson.fromJson(objectives, classOfT);
	}
	
	private static String getJSON(Quest q) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Quest.class, new QuestSerializer())
				.create();
		return gson.toJson(q);
	}

}
