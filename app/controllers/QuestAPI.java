package controllers;

import java.util.List;

import models.Objective;
import models.Quest;
import play.mvc.Controller;
import serializers.QuestSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QuestAPI extends Controller {

    public static void getQuests() {
        List<Quest> quests = Quest.all().fetch();
        GsonBuilder gsonb = new GsonBuilder();
        gsonb.registerTypeAdapter(Quest.class, new QuestSerializer());
        Gson gson = gsonb.create();

        renderJSON(gson.toJson(quests));
    }

    public static void create(String quest) {
        Gson gson = new Gson();

        Quest q = gson.fromJson(quest, Quest.class);
        q.create();

        for (Objective o : q.objectives) {
            o.qowner = q;
            o.create();
        }

        q.save();

        renderJSON(getJSON((Quest) Quest.findById(q.id)));
    }

    private static String getJSON(Quest q) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Quest.class,
                new QuestSerializer()).create();
        return gson.toJson(q);
    }

}
