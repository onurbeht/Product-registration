package productRegistration.main.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import productRegistration.main.entities.user.AuthenticationDTO;
import productRegistration.main.entities.user.User;
import productRegistration.main.entities.user.RegisterDTO;
import productRegistration.main.entities.user.UserRepository;
import productRegistration.main.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

//POST
    @PostMapping("/register")       //Get the data form the body and valid them
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        //Check if user already exists
        if(userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        //Encode password
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        //Create a new user
        User newUser = new User(data.login(), encryptedPassword, data.role());

        //Save the new user
        userRepository.save(newUser);



        return ResponseEntity.ok().build();

    }
    @PostMapping("/login")       //Get the data form the body and valid them
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        //Spring security, check for me, if the user exists, using the userRepository
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        //Authenticate the user
        var auth = authenticationManager.authenticate(usernamePassword);

        //generate and return the token to user
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);

    }
}
