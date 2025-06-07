package dev.amirgol.biterate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Security configuration for the BiteRate application.
     *
     * Notable changes:
     * - Added SWAGGER_WHITELIST constant containing all Swagger-related paths
     * - Updated security configuration to permit access to Swagger UI and OpenAPI endpoints
     * - Maintained existing JWT authentication for API endpoints
     *
     * Whitelisted URLs:
     * - /swagger-ui.html: Main Swagger UI page
     * - /swagger-ui/**:  Swagger UI static resources
     * - /api-docs/**:    OpenAPI documentation endpoints
     * - /swagger-resources/**: Swagger resources
     * - /webjars/**:     Web dependencies
     */
    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(SWAGGER_WHITELIST).permitAll()
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauthServer ->
                        oauthServer.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }

}