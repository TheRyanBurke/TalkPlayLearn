package models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class EngagedQuest extends Model{
	@OneToOne
	public User owner;
	
	@OneToOne
	public Quest quest;

	public int[] objectiveProgress;
	
	public EngagedQuest(User _owner, Quest _quest) {
		owner = _owner;
		quest = _quest;
		objectiveProgress = new int[quest.objectives.size()];
		for(int i = 0; i < objectiveProgress.length; i++) {
			objectiveProgress[i] = 0;
		}
	}
	
	public void incrementObjectiveProgress(int objectiveIndex) {
		objectiveProgress[objectiveIndex]++;
	}
}
