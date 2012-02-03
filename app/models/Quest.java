package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Quest extends Model{
	public String title;
	public String description;
	public REPEATABLE repeatability;
	
	@OneToMany
	public List<Quest> prerequirements;
	
	@OneToMany
	public List<Quest> children;
	
	public List<Objective> objectives;
	
	//public Map<Objective, Integer> objectiveProgress;
	
	public enum REPEATABLE {
		ONCE, DAILY, UNLIMITED
	}
	
	public Quest(String _title, String _description, REPEATABLE _repeat) {
		title = _title;
		description = _description;
		repeatability = _repeat;
		prerequirements = new ArrayList<Quest>();
		children = new ArrayList<Quest>();
		objectives = new ArrayList<Objective>();
	}
	
		
}
