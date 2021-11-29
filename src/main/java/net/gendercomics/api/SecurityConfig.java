package net.gendercomics.api;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
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
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/comics*").hasRole("crud_comics")
                .antMatchers(HttpMethod.PUT, "/comics*").hasRole("crud_comics")
                .antMatchers(HttpMethod.DELETE, "/comics*").hasRole("crud_comics")
                .antMatchers(HttpMethod.POST, "/persons*").hasRole("crud_comics")
                .antMatchers(HttpMethod.PUT, "/persons*").hasRole("crud_comics")
                .antMatchers(HttpMethod.DELETE, "/persons*").hasRole("crud_comics")
                .antMatchers(HttpMethod.POST, "/publishers*").hasRole("crud_comics")
                .antMatchers(HttpMethod.PUT, "/publishers*").hasRole("crud_comics")
                .antMatchers(HttpMethod.DELETE, "/publishers*").hasRole("crud_comics")
                .antMatchers(HttpMethod.POST, "/roles*").hasRole("crud_comics")
                .antMatchers(HttpMethod.PUT, "/roles*").hasRole("crud_comics")
                .antMatchers(HttpMethod.DELETE, "/roles*").hasRole("crud_comics")
                .antMatchers(HttpMethod.POST, "/keywords*").hasRole("crud_comics")
                .antMatchers(HttpMethod.PUT, "/keywords*").hasRole("crud_comics")
                .antMatchers(HttpMethod.DELETE, "/keywords*").hasRole("crud_comics")
                .antMatchers(HttpMethod.POST, "/texts*").hasRole("crud_comics")
                .antMatchers(HttpMethod.PUT, "/texts*").hasRole("crud_comics")
                .antMatchers(HttpMethod.DELETE, "/texts*").hasRole("crud_comics")
                .antMatchers(HttpMethod.POST, "/relations*").hasRole("crud_comics")
                .antMatchers(HttpMethod.PUT, "/relations*").hasRole("crud_comics")
                .antMatchers(HttpMethod.DELETE, "/relations*").hasRole("crud_comics")
                .antMatchers(HttpMethod.POST, "/migration*").hasRole("migration")
                .antMatchers(HttpMethod.POST, "/files*").hasRole("crud_comics")
                .antMatchers(HttpMethod.GET, "/images*").hasRole("crud_comics")
                .anyRequest().permitAll();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
