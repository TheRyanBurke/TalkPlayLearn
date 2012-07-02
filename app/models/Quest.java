package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Quest extends Model {
	public String title;
	@Column(columnDefinition = "VARCHAR(2000)")
	public String description;
	public REPEATABLE repeatability;

	// @OneToMany
	// public List<Quest> prerequirements;
	//
	// @OneToMany
	// public List<Quest> children;

	@OneToMany(mappedBy = "qowner")
	public Set<Objective> objectives;

	// @Embedded
	// public Statistics rewards;

	@ElementCollection
	public Map<String, Integer> reward;

	public enum REPEATABLE {
		ONCE, DAILY, UNLIMITED
	}

	public Quest(String _title, String _description, REPEATABLE _repeat) {
		title = _title;
		description = _description;
		repeatability = _repeat;
		// prerequirements = new ArrayList<Quest>();
		// children = new ArrayList<Quest>();
		objectives = new HashSet<Objective>();
		// rewards = new Statistics();
		reward = new HashMap<String, Integer>();
	}

	public Quest() {
		this("no title given", "no description given", REPEATABLE.ONCE);
	}

	public int totalXP() {
		int total = 0;

		for (Objective o : objectives) {
			if (!o.bonus)
				total += o.xp;
		}

		return total;
	}

	public int totalXPWithBonus() {
		int total = 0;

		for (Objective o : objectives) {
			total += o.xp;
		}

		return total;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public REPEATABLE getRepeatability() {
		return repeatability;
	}

	public void setRepeatability(REPEATABLE repeatability) {
		this.repeatability = repeatability;
	}

	public Set<Objective> getObjectives() {
		return objectives;
	}

	public void setObjectives(Set<Objective> objectives) {
		this.objectives = objectives;
	}

	public Map<String, Integer> getReward() {
		return reward;
	}

	public void setReward(Map<String, Integer> reward) {
		this.reward = reward;
	}

}
