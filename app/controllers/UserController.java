package controllers;

import static utils.Constants.CURRENT_USER;

import java.util.List;

import models.EngagedQuest;
import models.Quest;
import models.Statistics;
import models.User;
import play.Logger;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class UserController extends Controller{
	
	public static void viewUser(long userid) {
    	User user = User.findById(userid);
    	render(user);
    }
	
	public static void viewUsers() {
		List<User> users = User.findAll();
		render(users);
	}
	
	public static void renderUserJSON(long userid) {
		User user = User.findById(userid);
		renderJSON(user.getAsJson());
	}
	
	public static void beginQuest(String questid) {
    	User current = getCurrentUser();
    	if(current != null) {
	    	Quest q = Quest.findById(Long.parseLong(questid));
	    	if(q != null) {
	    		Logger.info("quest title: " + q.title + " objs: " + q.objectives.toString());
	    		current.beginQuest(q.id);
	    	}
    	}
    	renderJSON("success");
    }
    
    /**
     * 
     * @param questId
     * @param objectiveIndex - the index of the objective in the EngagedQuest.objectiveProgress[]
     */
    public static void uptickObjectiveCompletionCount(int questId, int objectiveIndex, boolean uptick) {
    	Logger.info("hit uptick: " + questId + ", "  + objectiveIndex);
    	
    	User u = getCurrentUser();
    	u.tickObjective(questId, objectiveIndex, uptick);
    	
    	renderJSON("success");
    	
    }
    
    public static void downtickObjectiveCompletionCount(int engagedQuestId, int objectiveIndex) {
    	User u = getCurrentUser();
    	EngagedQuest eq = u.quests.get(engagedQuestId);
    	if(eq != null) {
    		eq.decrementObjectiveProgress(objectiveIndex);
    		u.save();
    	}
    	renderJSON("success");
    	
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
	    	renderUserJSON(Long.parseLong(session.get(CURRENT_USER)));
    	}
    }
    
    

}
