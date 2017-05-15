package dges.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dges.customer.dao.UserDao;
import dges.customer.error.exception.UserAlredyExistsException;
import dges.customer.error.exception.UserNotFoundException;
import dges.customer.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	public void addUser(User user) {
		if (userDao.exists(user.getUsername())) {
			throw new UserAlredyExistsException(user.getUsername());
		}
		userDao.addUser(user);
	}

	public User getUser(String username) {
		User user = userDao.getUser(username);
		if (user == null) {
			throw new UserNotFoundException(username);
		}
		return user;
	}

	public boolean exists(String username) {
		return userDao.exists(username);
	}
	
	public boolean deleteUser(String username) {
		if (!userDao.deleteUser(username)) {
			throw new UserNotFoundException(username);
		}
		return true;
	}
}
