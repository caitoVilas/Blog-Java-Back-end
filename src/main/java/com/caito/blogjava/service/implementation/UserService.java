package com.caito.blogjava.service.implementation;

import com.caito.blogjava.components.PaginationComponent;
import com.caito.blogjava.constatnts.ConstantExeptionMessages;
import com.caito.blogjava.dto.ChangePassword;
import com.caito.blogjava.dto.NewUser;
import com.caito.blogjava.dto.UserResponse;
import com.caito.blogjava.entity.Role;
import com.caito.blogjava.entity.User;
import com.caito.blogjava.enums.RoleName;
import com.caito.blogjava.exceptions.customs.BadRequestException;
import com.caito.blogjava.repository.RoleRepository;
import com.caito.blogjava.repository.UserRepository;
import com.caito.blogjava.service.IUserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private PaginationComponent paginationComponent;

    @Override
    public User findByUserName(String userName) throws NotFoundException {
        return userRepository.findByUserName(userName).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND.concat(userName)
        ));
    }

    @Override
    public UserResponse ceateUser(NewUser newUser, MultipartFile file) throws IOException {
        if (newUser.getName() == null || newUser.getName() == "") {
            throw new BadRequestException(ConstantExeptionMessages.MSG_USER_NAME_EMPTY);
        }
        if (newUser.getUserName() == null ||newUser.getUserName() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_USER_USERNAME_EMPTY);
        }
        if (newUser.getEmail() == null || newUser.getEmail() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_USER_EMAIL_EMPTY);
        }
        if (userRepository.existsByEmail(newUser.getEmail())){
            throw new BadRequestException(ConstantExeptionMessages.MSG_USER_EMAIL_EXIST);
        }
        if (newUser.getPassword() == null || newUser.getPassword() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_USER_PASSWORD_EMPTY);
        }

        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(newUser, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (file != null){
            Map result = cloudinaryService.upload(file);
            user.setImageID((String) result.get("public_id"));
            user.setImageURL((String) result.get("url"));
        }
        Set<Role> roles = new HashSet<>();
        if (newUser.getRoles().contains("admin")){
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN).get());
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).get());
            user.setRoles(roles);
        }else {
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).get());
            user.setRoles(roles);
        }

        userRepository.save(user);
        ModelMapper mapper1 = new ModelMapper();
        UserResponse response = mapper1.map(user, UserResponse.class);
        return response;
    }

    @Override
    public UserResponse findById(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND));
        ModelMapper mapper = new ModelMapper();
        UserResponse response = mapper.map(user, UserResponse.class);
        return response;
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException, IOException {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND));
        if (user.getImageID() != null){
            cloudinaryService.delete(user.getImageID());
            user.setImageID(null);
            user.setImageURL(null);
            userRepository.save(user);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> ListAllUsers() {
        List<User> users = userRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        List<UserResponse> response = Arrays.asList(mapper.map(users,UserResponse[].class));
        return response;
    }

    @Override
    public UserResponse updateUser(Long id, NewUser newUser) throws NotFoundException {
        User oldUser = userRepository.findById(id).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND));
        if (newUser.getName() != null && newUser.getName() != ""){
            oldUser.setName(newUser.getName());
        }
        if (newUser.getUserName() != null && newUser.getUserName() != ""){
            oldUser.setUserName(newUser.getUserName());
        }
        if (newUser.getEmail() != null && newUser.getEmail() != ""){
            oldUser.setEmail(newUser.getEmail());
        }
        if (newUser.getPassword() != null && newUser.getPassword() != ""){
            oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        Set<Role> roles = new HashSet<>();
        if(newUser.getRoles().contains("admin")){
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN).get());
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).get());
        }else {
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_USER).get());
        }
        oldUser.setRoles(roles);
        userRepository.save(oldUser);

        ModelMapper mapper = new ModelMapper();
        UserResponse response = mapper.map(oldUser, UserResponse.class);

        return response;
    }

    @Override
    public UserResponse uploadImage(MultipartFile file, Long id) throws NotFoundException, IOException {

        if (file == null) {
            throw new BadRequestException(ConstantExeptionMessages.MSG_FILE_EMPTY);
        }
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND));
        if (user.getImageID() != null){
            cloudinaryService.delete(user.getImageID());
            user.setImageID(null);
            user.setImageURL(null);
        }
        Map result = cloudinaryService.upload(file);
        user.setImageURL((String) result.get("url"));
        user.setImageID((String) result.get("public_id"));
        userRepository.save(user);
        ModelMapper mapper = new ModelMapper();
        UserResponse response = mapper.map(user, UserResponse.class);
        return response;
    }

    @Override
    public String getAllPagination(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return paginationComponent.paginationResponse(users.map(this::userToDto));
    }

    @Override
    public UserResponse getByUserName(String userName) throws NotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(()-> new  NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND.concat(userName)));
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse changePass(ChangePassword changePassword) throws NotFoundException {
        User user = userRepository.findById(changePassword.getId()).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_USER_NOT_FOUND));
        if (changePassword.getOldPassword() != user.getPassword()){
            throw new BadRequestException(ConstantExeptionMessages.MSG_USER_PASSWORD_DISTINCT);
        }
        if (changePassword.getNewPassword().isEmpty()){
            throw new BadRequestException(ConstantExeptionMessages.MSG_USER_PASSWORD_EMPTY);
        }

        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));

        userRepository.save(user);
        ModelMapper mapper = new ModelMapper();

        return mapper.map(user, UserResponse.class);
    }

    private UserResponse userToDto(User user){
        ModelMapper mapper = new ModelMapper();
        UserResponse map = mapper.map(user, UserResponse.class);
        return map;
    }
}
