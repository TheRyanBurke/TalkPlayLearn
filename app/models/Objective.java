package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Objective extends Model {
	public String description;
	public int requiredCompletions;
	
	public Objective(String _description, int _requiredCompletions) {
		description = _description;
		requiredCompletions = _requiredCompletions;
	}
}
