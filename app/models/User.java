package models;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;

@Entity
public class User extends Model{
	public String displayname;
	public String username;
	
	@OneToMany(mappedBy="owner")
	public List<EngagedQuest> quests;
	
	public int xp;
	
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
	}
	
	public User() {
		this("no display name", "no username");
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
}
