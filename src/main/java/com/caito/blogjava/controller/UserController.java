package com.caito.blogjava.controller;

import com.caito.blogjava.dto.NewUser;
import com.caito.blogjava.dto.UserResponse;
import com.caito.blogjava.service.implementation.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody NewUser newUser){
        UserResponse response = userService.ceateUser(newUser);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<UserResponse>(userService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws NotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity( HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<List<UserResponse>>(userService.ListAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id,
                                                @RequestBody NewUser newUser) throws NotFoundException {
        return new ResponseEntity<UserResponse>(userService.updateUser(id,newUser),
                HttpStatus.OK);
    }
}
