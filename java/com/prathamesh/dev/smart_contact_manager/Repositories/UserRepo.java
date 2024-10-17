package com.prathamesh.dev.smart_contact_manager.Repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prathamesh.dev.smart_contact_manager.Entities.User;

public interface UserRepo extends JpaRepository<User,String>{
    
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
}
