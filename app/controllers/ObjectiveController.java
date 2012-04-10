package controllers;

import models.Objective;
import models.Statistics;
import play.mvc.Controller;
import serializers.ObjectiveExclusionStrategy;
import serializers.StatisticsSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ObjectiveController extends Controller {
	
	public static void create(String objective) {
		Gson gson = new Gson();
		Objective o = gson.fromJson(objective, Objective.class);
		o.save();
		renderJSON(getJSON(o));
	}
	
	public static void read() {
		
		
	}
	
	public static void update() {
		
	}
	
	public static void delete() {
		
	}
	
	private static String getJSON(Objective o) {
		Gson gson = new GsonBuilder()
	  	.addSerializationExclusionStrategy(new ObjectiveExclusionStrategy())
	  	.registerTypeAdapter(Statistics.class, new StatisticsSerializer())
	  	.create();
		return gson.toJson(o);
	}
}
