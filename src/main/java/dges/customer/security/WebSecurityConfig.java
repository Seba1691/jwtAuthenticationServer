package dges.customer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import dges.customer.security.filter.DelegateRequestMatchingFilter;
import dges.customer.security.filter.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	static final RequestMatcher LOGIN_MATCHER = new AntPathRequestMatcher("/auth/**", "POST");
	static final RequestMatcher OPTIONS_MATCHER = new AntPathRequestMatcher("/auth/**", "OPTIONS");
	static final RequestMatcher SWAGGER = new AntPathRequestMatcher("/swagger-ui.html/**");
	static final RequestMatcher SWAGGER_SITE = new AntPathRequestMatcher("/webjars/**");
	static final RequestMatcher SWAGGER_RESOURCES = new AntPathRequestMatcher("/swagger-resources");
	static final RequestMatcher SWAGGER_API = new AntPathRequestMatcher("/v2/api-docs");
	static final RequestMatcher CONFIGURATION = new AntPathRequestMatcher("/configuration/**");
	
	
	static final RequestMatcher IGNORED_MATCHER = new OrRequestMatcher(OPTIONS_MATCHER, LOGIN_MATCHER, SWAGGER,
			SWAGGER_RESOURCES, SWAGGER_SITE, SWAGGER_API, CONFIGURATION);

	@Bean
	public DelegateRequestMatchingFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter tokenFilter = new JwtAuthenticationTokenFilter();
		return new DelegateRequestMatchingFilter(IGNORED_MATCHER, tokenFilter);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// we don't need CSRF because our token is invulnerable
				.csrf().disable()

				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

				// don't create session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests().requestMatchers(IGNORED_MATCHER).permitAll().anyRequest().authenticated();

		// Custom JWT based security filter
		httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		// disable page caching
		httpSecurity.headers().cacheControl();
	}

}