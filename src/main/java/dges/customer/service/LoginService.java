package dges.customer.service;

import dges.customer.controller.response.Token;
import dges.customer.model.User;

public interface LoginService {

	public Token login(String username, String password);
	
	public void register(User user);

}
