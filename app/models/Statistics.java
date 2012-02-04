package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Statistics extends Model {
	
	public int creativity, enthusiasm, productivity, socialness, gamer, academic;
	
	@OneToOne
	public User owner;
	
	public Statistics() {
		creativity = 0;
		enthusiasm = 0;
		productivity = 0;
		socialness = 0;
		gamer = 0;
		academic = 0;
	}
}
