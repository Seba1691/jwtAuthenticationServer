package dges.customer.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dges.customer.controller.response.UserDto;
import dges.customer.model.User;
import dges.customer.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;

@RestController
@RequestMapping("/user")
@Api("User")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	ModelMapper modelMapper;

	@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public UserDto getCurrentUser() {
		return modelMapper.map(userService.getUser(getCurrentUsername()), UserDto.class);
	}

	@ApiImplicitParam(name = "Authorization", required = true, paramType = "header")
	@RequestMapping(value = "/current", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteCurrentUser() {
		userService.deleteUser(getCurrentUsername());
	}

	@ApiImplicitParam(name = "Authorization", required = true, paramType = "header")
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN')")
	public UserDto getUser(@PathVariable String username) {
		return modelMapper.map(userService.getUser(username), UserDto.class);
	}
	
	@ApiImplicitParam(name = "Authorization", required = true, paramType = "header")
	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public void removeUser(@PathVariable String username) {
		userService.deleteUser(username);
	}

	private String getCurrentUsername() {
		return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
}
