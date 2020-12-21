package com.nubari.diary.controllers;

import com.nubari.diary.DTOs.AuthToken;
import com.nubari.diary.DTOs.UserDto;
import com.nubari.diary.DTOs.UserLoginDTO;
import com.nubari.diary.models.User;
import com.nubari.diary.security.jwt.TokenProvider;
import com.nubari.diary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("authenticate")
    public ResponseEntity<?> generateToken(@RequestBody UserLoginDTO userLoginDTO) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getUsername(),
                        userLoginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return new ResponseEntity<>(new AuthToken(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
        try {
            User registeredUser = userService.save(userDto);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
