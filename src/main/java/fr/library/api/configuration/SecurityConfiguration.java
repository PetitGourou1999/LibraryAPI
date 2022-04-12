package fr.library.api.configuration;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(keycloakAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		
		http.csrf().disable();
		http.cors().and();
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/books/**").hasAnyAuthority("simple-user","simple-admin");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/books/**").hasAnyAuthority("simple-admin");
		http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/books/**").hasAnyAuthority("simple-admin");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/books/**").hasAnyAuthority("simple-admin");
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/authors/**").hasAnyAuthority("simple-user","simple-admin");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/authors/**").hasAnyAuthority("simple-admin");
		http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/authors/**").hasAnyAuthority("simple-admin");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/authors/**").hasAnyAuthority("simple-admin");
		
		// http.authorizeRequests().antMatchers( "/users/**").hasAnyAuthority("admin");
		// http.authorizeRequests().anyRequest().permitAll();
	}

}
