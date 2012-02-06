package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Objective extends Model {
	
	@ManyToOne
	public Quest qowner;
	
	public String description;
	
	
	public int requiredCompletions;
	
	public Objective(String _description, int _requiredCompletions, Quest q) {
		description = _description;
		requiredCompletions = _requiredCompletions;
		qowner = q;
	}
//	
//	public Objective() {
//		this("no description given", 0);
//	}
	
	public String toString() {
		return "{description: " + description + ", requiredCompletions: " + requiredCompletions + "}";
	}
}
