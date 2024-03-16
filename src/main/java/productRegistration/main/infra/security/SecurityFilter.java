package productRegistration.main.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import productRegistration.main.entities.user.UserRepository;
import productRegistration.main.services.TokenService;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Call the method to recov;er the token
        var token = recoverToken(request);

        //check if token is different of null
        if (token != null) {
            //get the login from the token and validate them
            var login = tokenService.validateToken(token);
            //find user in the DB
            UserDetails user = userRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //call the next filter
        filterChain.doFilter(request, response);
    }

    //Method to recover the token
    private String recoverToken(HttpServletRequest request) {
        //get the header
        var authHeader = request.getHeader("Authorization");

        //check if header is null
        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");

    }
}
