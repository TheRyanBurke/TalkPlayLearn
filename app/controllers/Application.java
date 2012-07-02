package controllers;

import static utils.Constants.CURRENT_USER;

import java.util.ArrayList;
import java.util.List;

import models.Quest;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import utils.Constants;

public class Application extends Controller {

	@Before
	static void loadCommonStuff() {
		if (session.contains(CURRENT_USER)) {
			User current = getCurrentUser();
			if (current != null) {
				renderArgs.put(CURRENT_USER, current);
			}
		}

		List<User> users = User.findAll();
		List<Quest> quests = Quest.findAll();
		List<String> statTypes = buildStatList();
		renderArgs.put("statTypes", statTypes);
		renderArgs.put("users", users);
		renderArgs.put("quests", quests);
	}

	private static List<String> buildStatList() {
		List<String> stats = new ArrayList<String>();
		stats.add(Constants.CREATIVITY);
		stats.add(Constants.ACADEMIC);
		stats.add(Constants.ENTHUSIASM);
		stats.add(Constants.GAMER);
		stats.add(Constants.PRODUCTIVITY);
		stats.add(Constants.SOCIALNESS);
		return stats;
	}

	public static void index() {
		render();
	}

	public static void login(long userid) {
		session.put(CURRENT_USER, userid);
		index();
	}

	public static void logout() {
		session.remove(CURRENT_USER);
		index();
	}

	private static User getCurrentUser() {
		return User.findById(Long.parseLong(session.get(CURRENT_USER)));
	}

}