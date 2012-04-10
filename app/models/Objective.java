package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Objective extends Model {
	
	@ManyToOne
	public Quest qowner;
	
	public String description;
	
	public int xp;
	public int requiredCompletions;
	
	public boolean bonus;
	
	public Objective() {
		
	}
	
	public Objective(String _description, int _requiredCompletions, Quest q, int _xp, boolean _bonus) {
		description = _description;
		requiredCompletions = _requiredCompletions;
		qowner = q;
		xp = _xp;
		bonus = _bonus;
	}
	
//	
//	public Objective() {
//		this("no description given", 0);
//	}
	
	public String toString() {
		return "{description: " + description + ", requiredCompletions: " + requiredCompletions + "}";
	}
	
	
}

