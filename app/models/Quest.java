package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Quest extends Model{
	public String title;
	public String description;
	public REPEATABLE repeatability;
	
//	@OneToMany
//	public List<Quest> prerequirements;
//	
//	@OneToMany
//	public List<Quest> children;
	
	@OneToMany(mappedBy="qowner")
	public Set<Objective> objectives;
	
	@Embedded
	public Statistics rewards;
	
	public enum REPEATABLE {
		ONCE, DAILY, UNLIMITED
	}
	
	public Quest(String _title, String _description, REPEATABLE _repeat) {
		title = _title;
		description = _description;
		repeatability = _repeat;
//		prerequirements = new ArrayList<Quest>();
//		children = new ArrayList<Quest>();
		objectives = new HashSet<Objective>();
		rewards = new Statistics();
	}
	
	public Quest() {
		this("no title given", "no description given", REPEATABLE.ONCE);
	}
	
	public int totalXP() {
		int total = 0;
		
		for(Objective o : objectives) {
			if(!o.bonus)
				total += o.xp;
		}
		
		return total;
	}
	
	public int totalXPWithBonus() {
		int total = 0;
		
		for(Objective o : objectives) {
			total += o.xp;
		}
		
		return total;		
	}
	
}

