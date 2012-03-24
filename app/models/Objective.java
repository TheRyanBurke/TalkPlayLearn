package models;

import java.lang.reflect.Type;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import play.db.jpa.Model;

@Entity
public class Objective extends Model {
	
	@ManyToOne
	public Quest qowner;
	
	public String description;
	
	public int xp;
	public int requiredCompletions;
	
	public boolean bonus;
	
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

class ObjectiveSerializer implements JsonSerializer<Objective> {
	  public JsonElement serialize(Objective src, Type typeOfSrc, JsonSerializationContext context) {
		  String json = "{\"description\":"+ src.description +
				  ",\"xp\":"+ src.xp +
				  ",\"requiredCompletions\":" + src.requiredCompletions +
				  ",\"bonus\":" + src.bonus + "}";
	    return new JsonPrimitive(json);
	  }
}