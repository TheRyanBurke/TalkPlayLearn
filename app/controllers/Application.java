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
    	
    	List<User> users = User.findAll();    	
    	List<Quest> quests = Quest.findAll();
    	
    	render(users, quests);
    }
    
    public static void login() {
    	String userid = params.get("userid");
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