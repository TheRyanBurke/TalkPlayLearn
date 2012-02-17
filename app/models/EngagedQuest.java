package models;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.Statistics.STATS;
import play.Logger;
import play.db.jpa.Model;

@Entity
public class EngagedQuest extends Model{
	@ManyToOne
	public User owner;
	
	@OneToOne
	public Quest quest;

	public int[] objectiveProgress;
	
	public boolean completed;
	public Date completedOn;
	
	public EngagedQuest(User _owner, Quest _quest) {
		owner = _owner;
		quest = _quest;
		completed = false;
		if(null == quest)
			Logger.info("quest is null!");
		else
			Logger.info("quest title: " + quest.title);
		if(null == quest.objectives)
			Logger.info("quest objectives is null!");
		objectiveProgress = new int[quest.objectives.size()];
		for(int i = 0; i < objectiveProgress.length; i++) {
			objectiveProgress[i] = 0;
		}
	}
	
	public void incrementObjectiveProgress(int objectiveIndex) {
		if(!completed) {
			objectiveProgress[objectiveIndex]++;
			this.save();
			if(objectiveProgress[objectiveIndex] == quest.objectives.get(objectiveIndex).requiredCompletions) {
				Logger.info("Objective complete!");
			}
			if(allObjectivesCompleted())
				completeQuest();
		} else if(quest.objectives.get(objectiveIndex).bonus) {
			objectiveProgress[objectiveIndex]++;
			this.save();
			if(objectiveProgress[objectiveIndex] == quest.objectives.get(objectiveIndex).requiredCompletions) {
				Logger.info("Post quest complete bonus Objective complete!");
				owner.gainXP(quest.objectives.get(objectiveIndex).xp);
			}
		}
	}
	
	public void decrementObjectiveProgress(int objectiveIndex) {
//		completed = false;
//		completedOn = null;
		
		if(objectiveProgress[objectiveIndex] == quest.objectives.get(objectiveIndex).requiredCompletions) {
			if(completed && !quest.objectives.get(objectiveIndex).bonus) {
				uncompleteQuest();
			} else if(completed && quest.objectives.get(objectiveIndex).bonus) {
				owner.loseXP(quest.objectives.get(objectiveIndex).xp);
			}
		}
		if(--objectiveProgress[objectiveIndex] < 0) {
			objectiveProgress[objectiveIndex] = 0;
		}
		this.save();
	}
	
	public boolean allObjectivesCompleted() {
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
	
	public void completeQuest() {
		completed = true;
		completedOn = new GregorianCalendar().getTime();
		this.save();
		for(int i = 0; i < objectiveProgress.length; i++) {
			if(objectiveProgress[i] >= quest.objectives.get(i).requiredCompletions)
				owner.gainXP(quest.objectives.get(i).xp);
		}
		for(STATS s : STATS.values()) {
			owner.addStat(s, quest.rewards.getStat(s));
		}
		Logger.info("Quest completed!");
	}
	
	public void uncompleteQuest() {
		completed = false;
		completedOn = null;
		for(STATS s : STATS.values()) {
			owner.loseStat(s, quest.rewards.getStat(s));
		}
		for(int i = 0; i < objectiveProgress.length; i++) {
			if(objectiveProgress[i] >= quest.objectives.get(i).requiredCompletions)
				owner.loseXP(quest.objectives.get(i).xp);
		}
	}
	
	public String toString() {
		return "I'm an EngagedQuest object!";
	}
	
}
