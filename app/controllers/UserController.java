package controllers;

import static utils.Constants.CURRENT_USER;

import java.util.List;

import models.Quest;
import models.Statistics;
import models.User;
import play.Logger;
import play.mvc.Controller;
import serializers.UserSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserController extends Controller{
	
	public static void viewUser(long userid) {
    	User user = User.findById(userid);
    	render(user);
    }
	
	public static void viewUsers() {
		List<User> users = User.findAll();
		render(users);
	}
	
	public static void getUser(long userid) {
		renderJSON(createUserJSON((User) User.findById(userid)));
	}
	
	private static String createUserJSON(User u) {
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(User.class, new UserSerializer())
			.create();
		return gson.toJson(u);
	}
	
	public static void beginQuest(String questid) {
    	User current = getCurrentUser();
    	if(current != null) {
	    	Quest q = Quest.findById(Long.parseLong(questid));
	    	if(q != null) {
	    		if(current.eligibleForQuest(q))
	    			current.beginQuest(q.id);
	    	}
    	}
    	renderJSON(createUserJSON(current));  
    }
	
	public static void completeQuest(long questid) {
		User current = getCurrentUser();
		if(current != null) {
			current.completeQuest(questid);
		}
		
		renderJSON(createUserJSON(current));
	}
    
    /**
     * 
     * @param questId
     * @param objectiveIndex - the index of the objective in the EngagedQuest.objectiveProgress[]
     */
    public static void tickObjective(int questId, int objectiveIndex, boolean uptick) {
    	Logger.info("hit uptick: " + questId + ", "  + objectiveIndex);
    	
    	User u = getCurrentUser();
    	u.tickObjective(questId, objectiveIndex, uptick);
    	
    	renderJSON(createUserJSON(u));    	
    }
    
    public static void awardPoint(long userId, Statistics.STATS stat, String reason) {
    	Logger.info("awarding point to " + userId + " +1 " + stat + " for " + reason);
    	User u = User.findById(userId);
    	if(u != null) {
    		Logger.info("user not null");
    		u.addStat(stat);
    		u.addToLog(getCurrentUser().displayname + " awarded you +1 " + stat + " for " + reason);
    	}
    	renderJSON("success");
    }
    
    private static User getCurrentUser() {
    	return User.findById(Long.parseLong(session.get(CURRENT_USER)));
    }
    
    public static void getCurrentUserForView() {
    	if(session.get(CURRENT_USER) != null) {
    		User u = User.findById(Long.parseLong(session.get(CURRENT_USER)));
	    	renderJSON(createUserJSON(u));
    	}
    }
    
    

}
