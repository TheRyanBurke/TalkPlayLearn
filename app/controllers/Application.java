package controllers;

import play.*;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import models.*;
import models.Quest.REPEATABLE;

public class Application extends Controller {

	private static String CURRENT_USER = "currentUser";
	
    public static void index() {
    	if(session.contains(CURRENT_USER)) {
    		User current = getCurrentUser();
    		if(current != null) {
    			renderArgs.put(CURRENT_USER, current);
    			List<EngagedQuest> engagedQuests = EngagedQuest.find("owner=?", current).fetch();
    			renderArgs.put("engagedQuests", engagedQuests);
    		}
    	}
    	
    	if(User.findAll().isEmpty()){
    		User newuser = new User("Ryan Burke", "theryanburke");
    		newuser.save();
    	}
    	
    	List<User> users = User.findAll();
		
    	if(Quest.findAll().isEmpty()) {
    		Quest quest = new Quest("first quest", "this is your first quest", REPEATABLE.UNLIMITED);
    		quest.save();
    		Logger.info("created a new quest");
    		
    		Objective obj = new Objective("Go to google.com", 1, quest);
    		obj.save();
    		Logger.info("added obj to quest");
    	} else {
    		Logger.info("objs: " + Quest.<Quest>findAll().get(0).objectives.toString());
    	}
    	
    	List<Quest> quests = Quest.findAll();
    	
    	render(users, quests);
    }
    
    public static void beginQuest(String userid, String questid) {
    	User current = getCurrentUser();
    	if(current != null && current.id.equals(Long.parseLong(userid))) {
	    	Quest q = Quest.findById(Long.parseLong(questid));
	    	if(q != null) {
	    		Logger.info("quest title: " + q.title + " objs: " + q.objectives.toString());
	    		EngagedQuest eq = new EngagedQuest(current, q);
	    		eq.save();
	    	}
    	}
    	index();
    }
    
    /**
     * 
     * @param engagedQuestId
     * @param objectiveIndex - the index of the objective in the EngagedQuest.objectiveProgress[]
     */
    public static void uptickObjectiveCompletionCount(String engagedQuestId, String objectiveIndex) {
    	Logger.info("hit uptick: " + engagedQuestId + ", "  + objectiveIndex);
    }
    
    public static void downtickObjectiveCompletionCount(String engagedQuestId, String objectiveId) {
    	
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