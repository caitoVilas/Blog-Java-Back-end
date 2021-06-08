package com.caito.blogjava.service;

import com.caito.blogjava.dto.NewUser;
import com.caito.blogjava.dto.UserResponse;
import com.caito.blogjava.entity.User;
import javassist.NotFoundException;

import java.util.List;

public interface IUserService {
    public User findByUserName(String userName) throws NotFoundException;
    public UserResponse ceateUser(NewUser newUser);
    public UserResponse findById(Long id) throws NotFoundException;
    public void deleteUser(Long id) throws NotFoundException;
    public List<UserResponse> ListAllUsers();
    public UserResponse updateUser(Long id, NewUser newUser) throws NotFoundException;
}
