package controllers;

import java.util.List;

import models.User;
import play.mvc.Controller;

public class UserController extends Controller{
	
	public static void viewUser(long userid) {
    	User user = User.findById(userid);
    	render(user);
    }
	
	public static void viewUsers() {
		List<User> users = User.findAll();
		render(users);
	}
	
	public static void getUser(long userid) {
		User user = User.findById(userid);
		renderJSON(user);
	}

}
