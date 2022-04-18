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

## Envoi de logs

Les logs de l'application sont envoyés vers la stack ELK grâce à la librairie Logback. La configuration se fait à l'aide du fichier logback-spring.xml.
Il nous suffit de rajouter un appender qui envoie les logs vers Logstash, ici sur le port 5000 :

````
<configuration debug="true" scan="true"
	scanPeriod="10 minutes">

	<!-- <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender"> 
		<encoder> <pattern>%green(example-project) - %cyan(%date [%thread] %-5level 
		%logger{36}) - %message : %magenta([DEVICE_ID=%X{device_id}] [DEVICE_NAME=%X{device_name}] 
		[DEVICE_OS=%X{device_os}] [APP_VER=%X{app_version}] [DEVICE_LANG=%X{device_language}] 
		[IP=%X{ip}] [PORT=%X{port}] [USER_AGENT=%X{user_agent}] [AUTH=%X{auth}] [TX_ID=%X{tx_id}] 
		[CLIENT_ID=%X{client_id}]%n) </pattern> </encoder> </appender> -->
	<appender name="STDOUT"
		class="ch.qos.logback.core.ConsoleAppender">
		<encoder>
			<pattern> %d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level
				%logger{36}.%M
				- %msg%n </pattern>
		</encoder>
	</appender>
	<appender name="DAILY_APP_LOG_FILE"
		class="ch.qos.logback.core.FileAppender">
		<file>logs/application.log</file>
		<encoder
			class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
			<Pattern>
				%d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level %logger{36}.%M - %msg%n </Pattern>
		</encoder>
	</appender>
	<appender name="STASH"
		class="net.logstash.logback.appender.LogstashTcpSocketAppender">
		<destination>localhost:5000</destination>
		<!-- You can add multiple destination values -->
		<!-- <destination>100.100.10.100:55525</destination> <destination>100.100.10.101:55525</destination> -->
		<encoder
			class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
			<providers>
				<timestamp />
				<version /> <!-- Logstash json format version, the @version field in the output -->
				<mdc /> <!-- MDC variables on the Thread will be written as JSON fields -->
				<context /> <!--Outputs entries from logback's context -->
				<logLevel />
				<loggerName />
				<pattern> <!-- we can add some custom fields to be sent with all the log entries. make 
						filtering easier in Logstash -->
					<pattern>
						{
						"appName": "angular-api-project"
						}
					</pattern>
				</pattern>
				<threadName />
				<message />
				<logstashMarkers /> <!-- Useful so we can add extra information for specific log lines as Markers -->
				<arguments /> <!--or through StructuredArguments -->
				<stackTrace />
			</providers>
		</encoder>
	</appender>

	<!--<appender name="Sentry"
		class="io.sentry.logback.SentryAppender">
		<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
			<level>WARN</level>
		</filter>
	</appender>-->
	<logger name="fr.library.api" additivity="false" level="info">
		<appender-ref ref="DAILY_APP_LOG_FILE" />
		<appender-ref ref="STDOUT" />
		<appender-ref ref="STASH" />
	</logger>
	<root level="INFO">
		<appender-ref ref="STDOUT" />
		<appender-ref ref="STASH" />
	</root>


	



</configuration>

