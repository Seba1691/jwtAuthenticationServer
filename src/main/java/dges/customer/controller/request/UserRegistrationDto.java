package dges.customer.controller.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dges.customer.model.Role;

public class UserRegistrationDto {

	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private List<Role> authorities;

	public UserRegistrationDto() {
	}

	public UserRegistrationDto(Long id, String username, String firstname, String lastname, String email,
			String password, List<Role> authorities, boolean enabled,
			Date lastPasswordResetDate) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Role> getAuthorities() {
		return authorities;
	}
}
