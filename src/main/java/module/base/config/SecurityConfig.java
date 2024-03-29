package module.base.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
//@EnableWebSecurity
@Import(KeycloakSpringBootConfigResolver.class)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(keycloakAuthenticationProvider());
    	
    	KeycloakAuthenticationProvider authProvider = new KeycloakAuthenticationProvider();
    	authProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    	
    	auth.authenticationProvider(authProvider);
    }
    
    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        
        http.authorizeRequests().and().logout().logoutUrl("/logout")
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true);
        
        http.csrf().disable();
        
        http.authorizeRequests().anyRequest().permitAll();
        
        /*
        http.authorizeRequests()
        .antMatchers("/admin/*").hasRole("admin")
        .antMatchers("/keyclock/*").hasRole("listAll")
        .antMatchers("/user/*").hasRole("listAll")
        .antMatchers("/oauth2/*").hasRole("listAll")
        .anyRequest().permitAll();
        */
    }
}
