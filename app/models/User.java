package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.google.gson.Gson;

import play.Logger;
import play.db.jpa.Model;
import utils.Constants;

@Entity
public class User extends Model{
	public String displayname;
	public String username;
	
	@ElementCollection
	public List<EngagedQuest> quests;
	
	public int xp;
	public int level;
	
	@ElementCollection
	public List<String> activityLog;
	
	/** 
	 * Similar to a character sheet in an RPG
	 * Other members can freely award stat points to others
	 * Some quests will also boost stats
	 * 
	 * want certain combos of stats to unlock Classes like Final Fantasy Tactics
	 */
	@Embedded
	public Statistics stats;
	
	public User(String _displayname, String _username) {
		displayname = _displayname;
		username = _username;
		quests = new ArrayList<EngagedQuest>();
		stats = new Statistics();
		xp = 0;
		level = 1;
		activityLog = new ArrayList<String>();
	}
	
	public User() {
		this("no display name", "no username");
	}
	
	public boolean eligibleForQuest(Quest search) {
		for(EngagedQuest eq : quests) {
			if(eq.getQuest().equals(search)) {
				//found a user tracked quest that matches
				if(eq.completed) {
					if(search.repeatability == Quest.REPEATABLE.DAILY) {
						if(new GregorianCalendar().getTime().getTime() - eq.completedOn.getTime() < 86400000L) {
							return false;
						}
					} else if(search.repeatability == Quest.REPEATABLE.ONCE) {
						//completed and one time only
						return false;
					}
				} else {
					//already have quest, not completed
					return false;
				}
			} 
		}
		return true;
	}
	
	public void beginQuest(long questId) {
		EngagedQuest eq = new EngagedQuest(questId);
		quests.add(eq);
		save();
	}
	
	public void gainXP(int addXP) {
		xp += addXP;
		save();
		Logger.info("Gained XP: " + addXP);
		checkLevelUp();
	}
	
	public void loseXP(int loseXP) {
		xp -= loseXP;
		save();
		Logger.info("Lost XP: " + loseXP);
		if(xp < 0) {
			levelDown();
		}
	}
	
	public void checkLevelUp() {
		if(xp >= currentLevelXPCap())
			levelUp();
	}
	
	public void levelUp() {
		xp = xp - currentLevelXPCap();
		level++;
		save();
		Logger.info("Leveled up!");
		addToLog("Level up! You are now level " + level);
	}
	
	public void levelDown() {
		xp = currentLevelXPCap() - Math.abs(xp);
		level--;
		save();
	}
	
	public void addStat(Statistics.STATS stat) {
		addStat(stat, 1);
	}
	
	public void addStat(Statistics.STATS stat, int val) {
		stats.addStat(stat, val);
		save();
	}
	
	public void loseStat(Statistics.STATS stat) {
		loseStat(stat, 1);
	}
	
	public void loseStat(Statistics.STATS stat, int val) {
		stats.loseStat(stat, val);
		save();
	}
	
	public void addToLog(String activity) {
		Date now = new GregorianCalendar().getTime();
		activityLog.add(now.toString() + " -- " + activity);
		save();
	}
	
	public int currentLevelXPCap() {
		return Constants.INITIALXPCAP + (level-1) * Constants.XPCAPINCREASE;
	}
	
	public String getAsJson() {
		Gson gson = new Gson();
    	String jsonUser = gson.toJson(this);
    	jsonUser = "{\"xpToLevel\":" + this.currentLevelXPCap() +
    			",\"xpToLevelPercent\":" + (100*this.xp/this.currentLevelXPCap()) +
    			"," + jsonUser.substring(1, jsonUser.length());
    	return jsonUser;
	}
}
