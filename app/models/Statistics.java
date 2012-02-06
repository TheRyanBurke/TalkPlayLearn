package models;

import javax.persistence.Embeddable;

@Embeddable
public class Statistics{
	
	public int creativity, enthusiasm, productivity, socialness, gamer, academic;
	
	public Statistics() {
		creativity = 0;
		enthusiasm = 0;
		productivity = 0;
		socialness = 0;
		gamer = 0;
		academic = 0;
	}
}
