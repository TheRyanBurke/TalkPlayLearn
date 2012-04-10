package models;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Embeddable;

import play.Logger;

@Embeddable
public class EngagedQuest{
	
	public long questid;

	public int[] objectiveProgress;
	
	public boolean completed;
	public Date completedOn;
	
	public Quest getQuest() {
		return Quest.findById(questid);
	}
	
	public EngagedQuest(long _questid) {
		questid = _questid;
		completed = false;
		objectiveProgress = new int[getQuest().objectives.size()];
		int count = getQuest().objectives.size();
		for(int i = 0; i < count; i++) {
			objectiveProgress[i] = 0;
		}
	}
	
	private void modifyObjectiveProgress(int index, int val) {
		objectiveProgress[index] += val;
		if(objectiveProgress[index] < 0) {
			objectiveProgress[index] = 0;
		}
	}
	
	public int incrementObjectiveProgress(int objectiveIndex) {
		if(!completed) {
			Quest q = getQuest();
			
			if(objectiveProgress[objectiveIndex] < q.objectives.get(objectiveIndex).requiredCompletions)
				modifyObjectiveProgress(objectiveIndex, 1);
			
			if(objectiveProgress[objectiveIndex] >= q.objectives.get(objectiveIndex).requiredCompletions) {
				Logger.info("Objective complete!");
				return q.objectives.get(objectiveIndex).xp;
			}
		} 
		
		return 0;
	}
	
	public int decrementObjectiveProgress(int objectiveIndex) {
		int xploss = 0;
		if(!completed) {
			Quest q = getQuest();
			if(objectiveProgress[objectiveIndex] == q.objectives.get(objectiveIndex).requiredCompletions) {
				xploss = q.objectives.get(objectiveIndex).xp;
			}
			modifyObjectiveProgress(objectiveIndex, -1);
		}
		
		return xploss;
	}
	
	public boolean allObjectivesCompleted() {
		Quest quest = getQuest();
		for(int i = 0; i < objectiveProgress.length; i++) {
			if(!quest.objectives.get(i).bonus) {
				if(objectiveProgress[i] != quest.objectives.get(i).requiredCompletions) {
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
		Quest q = getQuest();
		int xpGained = q.totalXP();
		for(int i = 0; i < q.objectives.size(); i++) {
			Objective o = q.objectives.get(i);
			if(o.bonus && objectiveProgress[i] >= o.requiredCompletions) {
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


