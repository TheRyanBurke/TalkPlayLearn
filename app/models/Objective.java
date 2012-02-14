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
	
	public Objective(String _description, int _requiredCompletions, Quest q, int _xp) {
		description = _description;
		requiredCompletions = _requiredCompletions;
		qowner = q;
		xp = _xp;
	}
//	
//	public Objective() {
//		this("no description given", 0);
//	}
	
	public String toString() {
		return "{description: " + description + ", requiredCompletions: " + requiredCompletions + "}";
	}
}
