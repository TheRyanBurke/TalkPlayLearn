package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Objective;
import models.Quest;
import play.mvc.Controller;
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
	
	public static void newQuest() {
		render();
	}
	
	public static void create(String quest/*, String rewards, String objectives*/) {
		Gson gson = new Gson();
		
		Quest q = gson.fromJson(quest, Quest.class);
		
		List<Objective> objs = new ArrayList<Objective>();
		objs = q.objectives;
		
		q.objectives = new ArrayList<Objective>();
		
		q.save();
		
		
		for(Objective o : objs) {
			Objective o2 = ((Objective)Objective.findById(o.id));
			o2.qowner = q;
			o2.save();			
		}
		
		
		
		
		
		renderJSON(getJSON((Quest) Quest.findById(q.id)));
	}
	
	private static String getJSON(Quest q) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Quest.class, new QuestSerializer())
				.create();
		return gson.toJson(q);
	}

}
