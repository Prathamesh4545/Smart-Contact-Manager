package com.prathamesh.dev.smart_contact_manager.Service;

import com.prathamesh.dev.smart_contact_manager.Entities.User;
import java.util.*;

public interface UserService {
    
    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteuser(String id);

    boolean isUserExist(String id);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();

    User getUserByEmail(String email);

}
