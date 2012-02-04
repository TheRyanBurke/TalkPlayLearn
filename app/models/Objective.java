package models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Objective implements Serializable {
	public String description;
	public int requiredCompletions;
	
	public Objective(String _description, int _requiredCompletions) {
		description = _description;
		requiredCompletions = _requiredCompletions;
	}
	
	public Objective() {
		this("no description given", 0);
	}
}
