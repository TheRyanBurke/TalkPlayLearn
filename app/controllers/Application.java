package controllers;

import static utils.Constants.CURRENT_USER;

import java.util.List;

import models.Objective;
import models.Quest;
import models.Quest.REPEATABLE;
import models.Statistics;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class Application extends Controller {

	
	
    public static void index() {
    	if(session.contains(CURRENT_USER)) {
    		User current = getCurrentUser();
    		if(current != null) {
    			renderArgs.put(CURRENT_USER, current);
    		}
    	}
    	
    	if(User.findAll().isEmpty()){
    		User newuser = new User("Ryan Burke", "theryanburke");
    		newuser.save();
    	}
    	
    	List<User> users = User.findAll();
		
    	if(Quest.findAll().isEmpty()) {
    		Quest quest = new Quest("first quest", "this is your first quest", REPEATABLE.UNLIMITED);
    		quest.rewards.addStat(Statistics.STATS.PRODUCTIVITY, 1);
    		quest.rewards.addStat(Statistics.STATS.SOCIALNESS, 1);
    		quest.save();
    		Logger.info("created a new quest");
    		
    		Objective obj = new Objective("Go to google.com three times", 3, quest, 30, false);
    		obj.save();
    		Objective obj2 = new Objective("Bonus! Do it once with your eyes closed!", 1, quest, 5, true);
    		obj2.save();
    		Logger.info("added obj to quest");
    	} else {
    		Logger.info("objs: " + Quest.<Quest>findAll().get(0).objectives.toString());
    	}
    	
    	List<Quest> quests = Quest.findAll();
    	
    	render(users, quests);
    }
    
    
    
    public static void login(String userid) {
    	session.put(CURRENT_USER, userid);
    	index();
    }
    
    public static void logout() {
    	session.remove(CURRENT_USER);
    	index();
    }
    
    private static User getCurrentUser() {
    	return User.findById(Long.parseLong(session.get(CURRENT_USER)));
    }

}