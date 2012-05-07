package models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;

@Embeddable
public class Statistics {

    public int creativity, enthusiasm, productivity, socialness, gamer,
            academic;

    public enum STATS {
        CREATIVITY, ENTHUSIASM, PRODUCTIVITY, SOCIALNESS, GAMER, ACADEMIC
    }

    public Statistics() {
        creativity = enthusiasm = productivity = socialness = gamer = academic = 0;
    }

    public Statistics(int c, int e, int p, int s, int g, int a) {
        creativity = c;
        enthusiasm = e;
        productivity = p;
        socialness = s;
        gamer = g;
        academic = a;
    }

    public void addStat(STATS stat, int val) {
        switch (stat) {
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
        default:
            break;
        }
    }

    // LOL faked an impl of a map because JPA doesn't easily support enum as Map
    // key
    public int getStat(STATS stat) {
        switch (stat) {
        case CREATIVITY:
            return creativity;

        case ENTHUSIASM:
            return enthusiasm;

        case PRODUCTIVITY:
            return productivity;

        case SOCIALNESS:
            return socialness;

        case GAMER:
            return gamer;

        case ACADEMIC:
            return academic;

        default:
            return 0;
        }
    }

    public void loseStat(STATS stat, int val) {
        switch (stat) {
        case CREATIVITY:
            creativity -= val;
            break;
        case ENTHUSIASM:
            enthusiasm -= val;
            break;
        case PRODUCTIVITY:
            productivity -= val;
            break;
        case SOCIALNESS:
            socialness -= val;
            break;
        case GAMER:
            gamer -= val;
            break;
        case ACADEMIC:
            academic -= val;
            break;
        default:
            break;
        }
    }

    public Map<STATS, Integer> populatedStatsMap() {
        Map<STATS, Integer> stats = new HashMap<STATS, Integer>();
        for (STATS s : STATS.values()) {
            if (getStat(s) > 0)
                stats.put(s, getStat(s));
        }

        return stats;
    }
}
