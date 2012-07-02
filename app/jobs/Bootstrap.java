package jobs;

import models.Quest;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		if (Quest.count() == 0)
			Fixtures.loadModels("quests.yml");
		// if(User.count() == 0)
		// Fixtures.loadModels("users.yml");
	}

}
