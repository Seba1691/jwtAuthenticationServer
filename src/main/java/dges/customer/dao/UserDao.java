package dges.customer.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import dges.customer.model.User;

@Component
public class UserDao {

	private Map<String, User> users;

	@PostConstruct
	public void init() {
		users = new HashMap<String, User>();
	}

	public void addUser(User user) {
		users.put(user.getUsername(), user);
	}

	public User getUser(String username) {
		return users.get(username);
	}

	public boolean exists(String username) {
		return getUser(username) != null;
	}

	public boolean deleteUser(String username) {
		return users.remove(username) != null;
	}
}
