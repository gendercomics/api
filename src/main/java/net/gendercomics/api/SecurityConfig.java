package net.gendercomics.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,   "/comics*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.PUT,    "/comics*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.DELETE, "/comics*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.POST,   "/persons*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.PUT,    "/persons*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.DELETE, "/persons*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.POST,   "/publishers*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.PUT,    "/publishers*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.DELETE, "/publishers*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.POST,   "/roles*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.PUT,    "/roles*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.DELETE, "/roles*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.POST,   "/keywords*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.PUT,    "/keywords*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.DELETE, "/keywords*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.POST,   "/texts*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.PUT,    "/texts*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.DELETE, "/texts*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.POST,   "/relations*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.PUT,    "/relations*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.DELETE, "/relations*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.POST,   "/migration*").hasRole("migration")
                        .requestMatchers(HttpMethod.POST,   "/files*").hasRole("crud_comics")
                        .requestMatchers(HttpMethod.GET,    "/images*").hasRole("crud_comics")
                        .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Keycloak stores roles in realm_access.roles; prefix with ROLE_ to match hasRole() checks
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            if (realmAccess == null || !realmAccess.containsKey("roles")) {
                return Collections.emptyList();
            }
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) realmAccess.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        });
        // Preserve existing principal.getName() == preferred_username behaviour
        converter.setPrincipalClaimName("preferred_username");
        return converter;
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
