package models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;

@Embeddable
public class Statistics{
	
	public enum STATS {
		CREATIVITY, ENTHUSIASM, PRODUCTIVITY, SOCIALNESS, GAMER, ACADEMIC
	}
	
	Map<STATS, Integer> stats;
	
	public Statistics() {
		stats = new HashMap<STATS, Integer>();
		for(STATS s : STATS.values()) {
			stats.put(s, 0);
		}
	}
}
