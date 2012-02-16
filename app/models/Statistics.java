package models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;

@Embeddable
public class Statistics{

	public int creativity, enthusiasm, productivity, socialness, gamer, academic;
	
	public enum STATS {
		CREATIVITY, ENTHUSIASM, PRODUCTIVITY, SOCIALNESS, GAMER, ACADEMIC
	}
	
	public Statistics() {
		creativity = enthusiasm = productivity = socialness = gamer = academic = 0;
	}
	
	public void addStat(STATS stat, int val) {
		switch(stat){
			case CREATIVITY:
				creativity += val;
				break;
			case ENTHUSIASM:
				enthusiasm += val;
				break;
			case PRODUCTIVITY:
				productivity += val;
				break;
			case SOCIALNESS:
				socialness += val;
				break;
			case GAMER:
				gamer += val;
				break;
			case ACADEMIC:
				academic += val;
				break;
			default: break;
		}	
	}
}
