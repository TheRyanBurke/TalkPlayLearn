package models;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;

import play.Logger;
import play.db.jpa.Model;

@Entity
public class EngagedQuest extends Model {
	
	@ManyToOne
	public Quest quest;
	
	@ManyToOne
	public User user;
	
	@ElementCollection
	@CollectionTable(name="OBJECTIVE_PROGRESS",
					joinColumns=@JoinColumn(name="ENGAGEDQUEST"))
	@Column(name="PROGRESS")
	@MapKeyJoinColumn(name="OBJECTIVE", referencedColumnName="ID")
	public Map<Objective, Integer> progress;
	
	public boolean completed;
	public Date completedOn;
	
	public EngagedQuest(long questid, long userid) {
		quest = Quest.findById(questid);
		user = User.findById(userid);
		completed = false;
		progress = new HashMap<Objective, Integer>();
		populateProgressMap();
	}
	
	private void populateProgressMap() {
		for(Objective o : quest.objectives) {
			progress.put(o, 0);
		}
	}
	
	private void modifyObjectiveProgress(Objective o, int val) {
		progress.put(o, progress.get(o) + val);
		save();
	}
	
	public int incrementObjectiveProgress(long objectiveid) {
		if(!completed) {
			Objective o = Objective.findById(objectiveid);
			
			if(progress.get(o) >= o.requiredCompletions) {
				progress.put(o, o.requiredCompletions);
				return 0;
			} else
				modifyObjectiveProgress(o, 1);
			
			if(progress.get(o) >= o.requiredCompletions) {
				Logger.info("Objective complete!");
				return o.xp;
			}
		} 
		
		return 0;
	}
	
	public int decrementObjectiveProgress(long objectiveid) {
		int xploss = 0;
		if(!completed) {
			Objective o = Objective.findById(objectiveid);
			if(progress.get(o) == o.requiredCompletions) {
				xploss = o.xp;
			}
			modifyObjectiveProgress(o, -1);
		}
		
		return xploss;
	}
	
	public boolean allObjectivesCompleted() {
		for(Objective o : quest.objectives) {
			if(!o.bonus) {
				if(progress.get(o) < o.requiredCompletions) {
					return false;
				}
			}
		}
		Logger.info("All objectives complete!");
		return true;
	}
	
	public int completeQuest() {
		completed = true;
		completedOn = new GregorianCalendar().getTime();
		Logger.info("Quest completed!");
		int xpGained = quest.totalXP();
		for(Objective o : quest.objectives) {
			if(o.bonus && progress.get(o) >= o.requiredCompletions) {
				xpGained += o.xp;
			}
		}
		return xpGained;
	}
	
	public void uncompleteQuest() {
		completed = false;
		completedOn = null;
	}

}


