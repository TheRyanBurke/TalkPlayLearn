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
    		User newuser = new User();
    		newuser.displayname="Ryan Burke";
    		newuser.username="theryanburke";
    		newuser.save();
    	}
    	
    	List<User> users = User.findAll();
        
    	if(Objective.findAll().isEmpty()) {
    		Objective obj = new Objective("Go to google.com", 1);
    		obj.save();
    	}
    
    	
    	if(Quest.findAll().isEmpty()) {
    		Quest quest = new Quest("first quest", "this is your first quest", REPEATABLE.UNLIMITED);
    		quest.objectives.add((Objective) Objective.findAll().get(0));
    		quest.save();
    	}
    	
    	List<Quest> quests = Quest.findAll();
    	
    	
    	
    	render(users, quests);
    }
    
    public static void beginQuest(String userid, String questid) {
    	User current = getCurrentUser();
    	if(current != null && current.id.equals(Long.parseLong(userid))) {
	    	Quest q = Quest.findById(questid);
	    	if(q != null) {
	    		EngagedQuest eq = new EngagedQuest(current, q);
	    		eq.save();
	        	//renderJSON("{\"success\":\"success\"}");
	    	}
    	}
    	//renderJSON("{\"failure\":\"failure\"}");
    	index();
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