package models;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class User extends Model{
	public String displayname;
	public String username;
	
	@OneToMany(mappedBy="owner")
	public Map<String, EngagedQuest> quests;
	
	public int xp;
	
	/** 
	 * Similar to a character sheet in an RPG
	 * Other members can freely award stat points to others
	 * Some quests will also boost stats
	 * 
	 * want certain combos of stats to unlock Classes like Final Fantasy Tactics
	 */
	@OneToOne(mappedBy="owner")
	public Statistics stats;
	
	public User(String _displayname, String _username) {
		displayname = _displayname;
		username = _username;
		stats = new Statistics();
	}
	
	public User() {
		this("no display name", "no username");
	}
}
