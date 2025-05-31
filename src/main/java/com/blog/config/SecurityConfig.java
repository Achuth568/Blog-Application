package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomUserDetailService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService userDetailService;
    
    
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            
				
				  .requestMatchers(new AntPathRequestMatcher("/api/users/registerUser")).permitAll()
				  .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				  .requestMatchers(new AntPathRequestMatcher("/v3/api-docs"),
						  new AntPathRequestMatcher("/v3/api-docs"),
						  new AntPathRequestMatcher("/v2/api-docs"),
						  new AntPathRequestMatcher("/v3/api-docs/**"),
						  new AntPathRequestMatcher("/swagger-resources"),
						  new AntPathRequestMatcher("/swagger-resources/**"),
						  new AntPathRequestMatcher("/configuration/ui"),
						  new AntPathRequestMatcher("/configuration/security"),
						  new AntPathRequestMatcher("/swagger-ui/**"),
						  new AntPathRequestMatcher("/webjars/**"),
						  new AntPathRequestMatcher("/swagger-ui.html")
						  ).permitAll()
				 
                .anyRequest()
                .authenticated() 
            .and()
            .formLogin()
            .and()
            .httpBasic();

        http.authenticationProvider(daoAuthenticationProvider());
        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
}






