package dges.customer.security.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import dges.customer.model.Role;
import dges.customer.model.User;
import dges.customer.security.JwtTokenUtil;

public class JwtAuthenticationTokenFilter extends GenericFilterBean {

	@Autowired
	private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

	final static private String TOKEN_HEADER = "Bearer";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String authHeader = httpRequest.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith(TOKEN_HEADER)) {
			throw new ServletException("Missing or invalid Authorization header.");
		}

		final String authToken = authHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(authToken);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			List<Role> roles = jwtTokenUtil.getAuthoritiesFromToken(authToken);
			UserDetails userDetails = new User(username, roles);

			if (jwtTokenUtil.validateToken(authToken)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}