package controllers;

import models.Objective;
import play.mvc.Controller;
import serializers.ObjectiveExclusionStrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ObjectiveAPI extends Controller {

    public static void create(String objective) {
        Gson gson = new Gson();
        Objective o = gson.fromJson(objective, Objective.class);
        o.create();
        renderJSON(getJSON(o));
    }

    public static void read() {

    }

    public static void update() {

    }

    public static void delete() {

    }

    private static String getJSON(Objective o) {
        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(
                new ObjectiveExclusionStrategy()).create();
        return gson.toJson(o);
    }
}
