package models;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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
	public List<Objective> objectives;
	
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
		objectives = new ArrayList<Objective>();
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

class QuestSerializer implements JsonSerializer<Quest> {
	  public JsonElement serialize(Quest src, Type typeOfSrc, JsonSerializationContext context) {
		  Gson gson = new Gson();
		  String json = gson.toJson(src); 
				  
		  json = "{\"totalXP\":"+ src.totalXP() +
				  ",\"totalXPWithBonus\":"+ src.totalXPWithBonus() +
				  "," + json.substring(1, json.length());
	    return new JsonPrimitive(json);
	  }
}