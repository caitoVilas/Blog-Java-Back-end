package com.caito.blogjava.service;

import com.caito.blogjava.dto.NewUser;
import com.caito.blogjava.dto.UserResponse;
import com.caito.blogjava.entity.User;
import javassist.NotFoundException;
import org.aspectj.apache.bcel.generic.MULTIANEWARRAY;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    public User findByUserName(String userName) throws NotFoundException;
    public UserResponse ceateUser(NewUser newUser, MultipartFile file) throws IOException;
    public UserResponse findById(Long id) throws NotFoundException;
    public void deleteUser(Long id) throws NotFoundException, IOException;
    public List<UserResponse> ListAllUsers();
    public UserResponse updateUser(Long id, NewUser newUser) throws NotFoundException;
    public UserResponse uploadImage(MultipartFile file, Long id) throws NotFoundException, IOException;
    public String getAllPagination(Pageable pageable);
    public NewUser getByUserName(String userName) throws NotFoundException;
}
