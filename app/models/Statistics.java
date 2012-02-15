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
	
	public void addStat(STATS stat) {
		switch(stat){
			case CREATIVITY:
				creativity++;
				break;
			case ENTHUSIASM:
				enthusiasm++;
				break;
			case PRODUCTIVITY:
				productivity++;
				break;
			case SOCIALNESS:
				socialness++;
				break;
			case GAMER:
				gamer++;
				break;
			case ACADEMIC:
				academic++;
				break;
			default: break;
		}	
	}
}
