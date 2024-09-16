package com.example.restapispringsecurity.service;


import com.example.restapispringsecurity.config.JwtUtilities;
import com.example.restapispringsecurity.dto.JwtTokenDto;
import com.example.restapispringsecurity.dto.LoginUserDto;
import com.example.restapispringsecurity.dto.RegisterUserDto;
import com.example.restapispringsecurity.dto.RoleName;
import com.example.restapispringsecurity.models.Role;
import com.example.restapispringsecurity.models.User;
import com.example.restapispringsecurity.repository.RoleRepository;
import com.example.restapispringsecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;
    private final AuthenticationManager authenticationManager;


    // this function is only used for new users to create there account
    public ResponseEntity<String> register(RegisterUserDto registerUserDto, RoleName roleName) {
        // verify if there is no user with this given email
        Optional<User> foundUser = userRepository.findByEmail(registerUserDto.getEmail());
        if (foundUser.isPresent()) {
            return new ResponseEntity<>( "The email already exists", HttpStatus.CONFLICT);
        }
        // verify the existence of the given Role, otherwise we need to register it
        Role foundRole = roleRepository.findByRoleName(roleName);
        if (foundRole == null) {
            Role newRole = Role.builder()
                    .roleName(roleName)
                    .build();
            foundRole = roleRepository.save(newRole);
        }
        List<Role> roleSet = new ArrayList<>();
        roleSet.add(foundRole);
        // create the new user with  given information
        User newUser = User.builder()
                .firstName(registerUserDto.getFirstName())
                .lastName(registerUserDto.getLastName())
                .email(registerUserDto.getEmail())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .roleSet(roleSet)
                .build();
        // save the new user in the database
        userRepository.save(newUser);
        // generating a jwt token for this user
        String token = jwtUtilities.generateJwtToken(newUser);
        return new ResponseEntity<>("you are registred successfully", HttpStatus.OK);
    }

    public ResponseEntity<JwtTokenDto> login(LoginUserDto loginUserDto) {

        // create an authentication token using email and password
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDto.getEmail(),
                loginUserDto.getPassword()
        );
        System.out.println(usernamePasswordAuthenticationToken);
        // searching the email and password in database and verify if there are correct or ot
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // Set the authentication object in the security context to provide global access to the authenticated user's details
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generation of jwt token
        User user = userRepository.findByEmail(loginUserDto.getEmail()).orElse(null);
        if(user == null){
            return new ResponseEntity<>(new JwtTokenDto("","Error in finding user"), HttpStatus.NOT_FOUND);
        }
        String token = jwtUtilities.generateJwtToken(user);
        return new ResponseEntity<>(new JwtTokenDto(token, ""), HttpStatus.OK);
    }
}
