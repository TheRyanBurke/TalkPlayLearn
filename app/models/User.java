package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;
import utils.Constants;
import utils.Constants.STATS;

@Entity
public class User extends Model{
	public String displayname;
	public String pictureURL;
	public String googleId;
	
	@OneToMany(mappedBy = "quest")
	public Set<EngagedQuest> quests;
	
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
	@ElementCollection
	public Map<String, Integer> stats;
	
	public User(String _googleId) {
		googleId = _googleId;
		displayname = "no name set";
		pictureURL = "/public/img/profile.png";
		quests = new HashSet<EngagedQuest>();
		stats = new HashMap<String, Integer>();// new Statistics();
		populateStats();
		xp = 0;
		level = 1;
		activityLog = new ArrayList<String>();
	}
	
	public User() {
		this("noGoogleId");
	}
	
	private void populateStats() {
		stats.put("Creativity", 0);
		stats.put("Enthusiasm", 0);
		stats.put("Academic", 0);
		stats.put("Productivity", 0);
		stats.put("Gamer", 0);
		stats.put("Socialness", 0);
	}
	
	public boolean eligibleForQuest(Quest search) {
		for(EngagedQuest eq : quests) {
			if(eq.quest.equals(search)) {
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
		EngagedQuest eq = new EngagedQuest(questId, this.id);
		eq.create();
		quests.add(eq);
		save();
	}
	
	public void tickObjective(long engagedQuestId, long objectiveId, boolean uptick) {
		EngagedQuest eq = EngagedQuest.findById(engagedQuestId);
    	if(eq != null) {
    		//don't add xp on objective completion, wait for quest completion
    		if(uptick)
    			/*gainXP(*/eq.incrementObjectiveProgress(objectiveId);
    		else
    			/*loseXP(*/eq.decrementObjectiveProgress(objectiveId);
    		save();
    	}		
	}
	
	public void completeQuest(long engagedQuestId) {
		EngagedQuest eq = EngagedQuest.findById(engagedQuestId);
		if(eq != null && eq.allObjectivesCompleted() && !eq.completed) {
			gainXP(eq.completeQuest());
			addToLog("Completed quest: " + eq.quest.title);
			for(Entry<String, Integer> entry : eq.quest.reward.entrySet()) {
				addStat(entry.getKey(), entry.getValue());
			}
		}
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
	
	public void addStat(String stat) {
		addStat(stat, 1);
	}
	
	public void addStat(String stat, int val) {
		stats.put(stat, stats.get(stat) + val);
		save();
	}
	
	public void loseStat(String stat) {
		loseStat(stat, 1);
	}
	
	public void loseStat(String stat, int val) {
		stats.put(stat, stats.get(stat) - val);
		save();
	}
	
	public void addToLog(String activity) {
		Date now = new GregorianCalendar().getTime();
		activityLog.add(0, now.toString() + " -- " + activity); //add to head
		save();
	}
	
	public int currentLevelXPCap() {
		return Constants.INITIALXPCAP + (level-1) * Constants.XPCAPINCREASE;
	}
	
}
