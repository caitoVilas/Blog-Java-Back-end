package com.caito.blogjava.controller;

import com.caito.blogjava.dto.JwtDto;
import com.caito.blogjava.dto.LoginUser;
import com.caito.blogjava.security.jwt.JwtUtil;
import com.caito.blogjava.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginUser loginUser){
        Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUser.getUserName(), loginUser.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(userDetails);
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
    }
}
