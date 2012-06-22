package controllers;

import static utils.Constants.CURRENT_USER;

import java.util.List;

import models.Quest;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

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
        renderArgs.put("users", users);
        renderArgs.put("quests", quests);
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