package com.prathamesh.dev.smart_contact_manager.Service.Implementation;

import java.util.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prathamesh.dev.smart_contact_manager.Entities.User;
import com.prathamesh.dev.smart_contact_manager.Helper.AppConstants;
import com.prathamesh.dev.smart_contact_manager.Helper.ResourceNotFoundException;
import com.prathamesh.dev.smart_contact_manager.Repositories.UserRepo;
import com.prathamesh.dev.smart_contact_manager.Service.UserService;

import ch.qos.logback.classic.Logger;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserID(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set user role

        //set provider
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

        User user2 = userRepo.findById(user.getUserID()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setProfilePic(user.getProfilePic());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setEnable(user.isEnable());
        user2.setEamilVerified(user.isEamilVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderID(user.getProviderID());

        return Optional.ofNullable(userRepo.save(user2));
    }

    @Override
    public void deleteuser(String id) {
        User user2 = userRepo.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String id) {
        User user2 = userRepo.findById(id).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
       return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(null);
    }

    
}
