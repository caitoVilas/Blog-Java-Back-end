package com.caito.blogjava.service.implementation;

import com.caito.blogjava.constatnts.ConstantExeptionMessages;
import com.caito.blogjava.dto.NewUser;
import com.caito.blogjava.entity.User;
import com.caito.blogjava.repository.UserRepository;
import com.caito.blogjava.service.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserName(String userName) throws NotFoundException {
        return userRepository.findByUserName(userName).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND.concat(userName)
        ));
    }

    @Override
    public NewUser ceateUser(NewUser newUser) {
        return null;
    }

    @Override
    public NewUser findById(Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
