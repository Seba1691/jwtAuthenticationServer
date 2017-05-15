package dges.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dges.customer.controller.response.Token;
import dges.customer.error.exception.InvalidCredentialsException;
import dges.customer.model.User;
import dges.customer.security.JwtTokenUtil;

@Component
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserService userService;

	@Override
	public Token login(String username, String password) {
		User user = userService.getUser(username);
		if (validCredentials(user, password)) {
			Token token = new Token();
			token.setToken(new JwtTokenUtil().generateToken(username, user.getAuthorities()));
			return  token;
		}
		else {
			throw new InvalidCredentialsException();
		}
	}
	
	@Override
	public void register(User user) {
		userService.addUser(user);
	}

	public boolean validCredentials(User user, String password) {
		return user.getPassword().equals(password);
	}

}
