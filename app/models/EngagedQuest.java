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
	
	// TODO: fix this. just need to store counter per objective
	public Map<Long, Integer> objectiveProgress;
	
	public EngagedQuest(User _owner, Quest _quest) {
		owner = _owner;
		quest = _quest;
		objectiveProgress = new HashMap<Long, Integer>();
		for(Objective o : quest.objectives) {
			objectiveProgress.put(o.id, 0);
		}
	}
	
	/**
	 * 
	 * @param objId
	 * @return true if successfully incremented
	 */
	public boolean incrementObjectiveProgress(Long objId) {
			if(objectiveProgress.containsKey(objId)) {
				Integer count = objectiveProgress.get(objId);
				objectiveProgress.put(objId, ++count);
				this.save();
				return true;
			}
		return false;
	}
}
