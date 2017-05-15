package dges.customer.service;

import dges.customer.model.User;

public interface UserService {

	public void addUser(User user);

	public User getUser(String username);

	public boolean exists(String username);

	public boolean deleteUser(String username);
}
