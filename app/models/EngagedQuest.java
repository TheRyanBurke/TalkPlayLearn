package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import models.Statistics.STATS;
import play.Logger;

public class EngagedQuest{
	
	public long questid;

	public List<Integer> objectiveProgress;
	
	public boolean completed;
	public Date completedOn;
	
	public Quest getQuest() {
		return Quest.findById(questid);
	}
	
	public EngagedQuest(long _questid) {
		questid = _questid;
		completed = false;
		objectiveProgress = new ArrayList<Integer>();
		int count = getQuest().objectives.size();
		while(count > 0) {
			objectiveProgress.add(0);
			count--;
		}
	}
	
	private void modifyObjectiveProgress(int index, int val) {
		objectiveProgress.set(index, objectiveProgress.get(index) + val);
	}
	
	public void incrementObjectiveProgress(int objectiveIndex) {
		Quest q = getQuest();
		if(!completed) {
			modifyObjectiveProgress(objectiveIndex, 1);
			
			if(objectiveProgress.get(objectiveIndex) == q.objectives.get(objectiveIndex).requiredCompletions) {
				Logger.info("Objective complete!");
			}
			if(allObjectivesCompleted())
				completeQuest();
		} else if(q.objectives.get(objectiveIndex).bonus) {
			modifyObjectiveProgress(objectiveIndex, 1);
			if(objectiveProgress.get(objectiveIndex) == q.objectives.get(objectiveIndex).requiredCompletions) {
				Logger.info("Post quest complete bonus Objective complete!");
				//owner.gainXP(q.objectives.get(objectiveIndex).xp);
			}
		}
	}
	
	public void decrementObjectiveProgress(int objectiveIndex) {
		Quest quest = getQuest();
		if(objectiveProgress.get(objectiveIndex) == quest.objectives.get(objectiveIndex).requiredCompletions) {
			if(completed && !quest.objectives.get(objectiveIndex).bonus) {
				uncompleteQuest();
			} else if(completed && quest.objectives.get(objectiveIndex).bonus) {
				//owner.loseXP(quest.objectives.get(objectiveIndex).xp);
			}
		}
		modifyObjectiveProgress(objectiveIndex, -1);
		if(objectiveProgress.get(objectiveIndex) < 0) {
			objectiveProgress.set(objectiveIndex, 0);
		}
		
	}
	
	public boolean allObjectivesCompleted() {
		Quest quest = getQuest();
		for(Integer i : objectiveProgress) {
			if(!quest.objectives.get(i).bonus) {
				if(objectiveProgress.get(i) != quest.objectives.get(i).requiredCompletions) {
					return false;
				}
			}
		}
		Logger.info("All objectives complete!");
		return true;
	}
	
	public void completeQuest() {
		completed = true;
		completedOn = new GregorianCalendar().getTime();
		Logger.info("Quest completed!");
	}
	
	public void uncompleteQuest() {
		completed = false;
		completedOn = null;
	}
	
	public String toString() {
		return "I'm an EngagedQuest object!";
	}
	
}
