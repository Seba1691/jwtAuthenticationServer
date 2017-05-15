package dges.customer.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dges.customer.controller.request.AuthenticationRequest;
import dges.customer.controller.request.UserRegistrationDto;
import dges.customer.controller.response.Token;
import dges.customer.model.User;
import dges.customer.service.LoginService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/auth")
@Api("Authentication")
//	@CrossOrigin(origins = "http://localhost:8081")
public class AuthenticationController {

	@Autowired
	LoginService loginService;
	@Autowired
	ModelMapper modelMapper;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Token login(@RequestBody AuthenticationRequest login) {
		return loginService.login(login.getUsername(), login.getPassword());
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@RequestBody UserRegistrationDto user) {
		loginService.register(modelMapper.map(user, User.class));
	}

}
