package productRegistration.main.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//annotation, to define this class as a configuration class
@Configuration
//Disable the default configs
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    //Create a security filter chain, to validate the requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return  //Disable CSRF
                http.csrf(csrf -> csrf.disable())
                //Define the session as Stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Define which endpoint will be required be ADMIN
                .authorizeHttpRequests(authorize -> authorize
                        //Authorize the POST/auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        //this is not the best option, but is enable, because any user can be ADMIN
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        //To "POST/products" need to be ADMIN
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                        //Any other endpoint, just need o be authenticated
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //Create a method to return a Bcrypt, to encode the passwords
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
