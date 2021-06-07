package com.caito.blogjava.service;

import com.caito.blogjava.dto.NewUser;
import com.caito.blogjava.entity.User;
import javassist.NotFoundException;

public interface IUserService {
    public User findByUserName(String userName) throws NotFoundException;
    public NewUser ceateUser(NewUser newUser);
    public NewUser findById(Long id);
    public void deleteUser(Long id);
}
