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
    public static void uptickObjectiveCompletionCount(long engagedQuestId, int objectiveIndex) {
    	Logger.info("hit uptick: " + engagedQuestId + ", "  + objectiveIndex);
    	
    	EngagedQuest eq = EngagedQuest.findById(engagedQuestId);
    	if(eq != null) {
    		eq.incrementObjectiveProgress(objectiveIndex);
    	}
    	
    	index();
    }
    
    public static void downtickObjectiveCompletionCount(long engagedQuestId, int objectiveIndex) {
    	EngagedQuest eq = EngagedQuest.findById(engagedQuestId);
    	if(eq != null) {
    		eq.decrementObjectiveProgress(objectiveIndex);
    	}
    	
    	index();
    }
    
    public static void awardPoint(long userId, Statistics.STATS stat, String reason) {
    	Logger.info("awarding point to " + userId + " +1 " + stat + " for " + reason);
    	User u = User.findById(userId);
    	if(u != null) {
    		Logger.info("user not null");
    		u.addStat(stat);
    		u.addToLog(getCurrentUser().displayname + " awarded you +1 " + stat + " for " + reason);
    	}
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