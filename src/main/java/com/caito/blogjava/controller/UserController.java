package com.caito.blogjava.controller;

import com.caito.blogjava.constatnts.ConstantExeptionMessages;
import com.caito.blogjava.constatnts.ConstantsSwagger;
import com.caito.blogjava.dto.ChangePassword;
import com.caito.blogjava.dto.NewUser;
import com.caito.blogjava.dto.UserResponse;
import com.caito.blogjava.exceptions.customs.BadRequestException;
import com.caito.blogjava.service.implementation.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@Api(value = ConstantsSwagger.MSG_SW_USERS_API_VALUE, tags = ConstantsSwagger.MSG_SW_USERS_API_TAG)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_USERS_CREATE, response = UserResponse.class)
    public ResponseEntity<?> createUser(@RequestBody NewUser newUser, MultipartFile file) throws IOException {
        UserResponse response = userService.ceateUser(newUser, file);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_USERS_GETONE, response = UserResponse.class)
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<UserResponse>(userService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_USERS_DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws NotFoundException, IOException {
        userService.deleteUser(id);
        return new ResponseEntity( HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = ConstantsSwagger.MSG_SW_USERS_LIST_ALL)
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<List<UserResponse>>(userService.ListAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_USERS_UPDATE, response = UserResponse.class)
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id,
                                                @RequestBody NewUser newUser) throws NotFoundException {
        return new ResponseEntity<UserResponse>(userService.updateUser(id,newUser),
                HttpStatus.OK);
    }

    @PostMapping("/up-image")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_USERS_UPLOAD_IMAGE)
    public ResponseEntity<UserResponse> uploadImage(@RequestParam MultipartFile file,
                                                    @RequestParam Long id) throws NotFoundException, IOException {
        return new ResponseEntity<UserResponse>(userService.uploadImage(file, id), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_USERS_LIST_PAGEABLE)
    public ResponseEntity<String> getAllPageable(@PageableDefault(page = 0, size = 10)Pageable pageable){
        return new ResponseEntity<String>(userService.getAllPagination(pageable), HttpStatus.OK);
    }

    @GetMapping("/user/{userName}")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_GET_USERS_BY_USERMANE)
    public ResponseEntity<UserResponse> getByUserName(@PathVariable("userName") String userName) throws NotFoundException {

        return new ResponseEntity<UserResponse>(userService.getByUserName(userName), HttpStatus.OK);
    }

    @PutMapping("/chanePass")
    @ApiOperation(value = ConstantsSwagger.MSG_SW_USERS_CHANGE_PASSWORD)
    public ResponseEntity<UserResponse> changePass(@RequestBody ChangePassword changePassword) throws NotFoundException {

        return new ResponseEntity<UserResponse>(userService.changePass(changePassword), HttpStatus.OK);
    }

}
