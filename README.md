# LibraryAPI

## Présentation

Cette API réalisée à l'aide de framework Springboot permet de faire la liaison entre une base de données MySQL et un applicatid Angular par l'intermédiaire d'appels REST.

Nous respectons ici le modèle MVC :
- Le Controller permet de définir les différents appels
- Le Service fait l'intermédiaire entre le Controller et le Repository et la majorité du métier (traitements sur les données) y est implémenté
- Le Repository qui sert à intéragir avec la base de données et réaliser des opérations CRUD

Différentes classes représentent les entités que nous manipulons :
- Les classes Form qui correspodent au format du corps JSON des appels REST faits dans l'application
- Les classes DTO qui sont un format pivot entre les Form et le Models (entités JPA)
- Les classes Model qui correspondent aux entités de la base de données et servent à faire le mapping entre les tables et des classes Java à l'aide de JPA et Hibernate

## Configuration

Les appels sont sécurisés grâce à Keycloak. La configuration suivante a été choisie :

```
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
```

Les utilisateurs ne peuvent que consulter les données tandis que les administrateurs peuvent créer, modifier ou supprimer des livres et des auteurs.
Si l'utilisateur n'a pas le rôle adéquat dans Keycloak, lorsque qu'un appel sera réalisé, on recevra le code d'erreur 403 : Forbidden.
