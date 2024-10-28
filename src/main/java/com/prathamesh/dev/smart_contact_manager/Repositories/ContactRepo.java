package com.prathamesh.dev.smart_contact_manager.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String>{
    
    //Find the contact by user
    //custom finder method
    List<Contact> findByUser(User user);

    //custom query method
    @Query("Select c from Contact c where c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    // List<Contact> searchContact(String name, String email, String phoneNumber);
}
