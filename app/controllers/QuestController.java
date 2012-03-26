package controllers;

import java.util.List;

import models.Objective;
import models.Quest;
import play.mvc.Controller;
import serializers.ObjectiveSerializer;
import serializers.QuestSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QuestController extends Controller {
	
	public static void getQuests() {
		List<Quest> quests = Quest.all().fetch();
		GsonBuilder gsonb = new GsonBuilder();
		gsonb.registerTypeAdapter(Quest.class, new QuestSerializer());
		Gson gson = gsonb.create();
		
		renderJSON(gson.toJson(quests));
	}

}
