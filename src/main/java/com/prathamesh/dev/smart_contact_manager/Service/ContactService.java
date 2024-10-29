package com.prathamesh.dev.smart_contact_manager.Service;

import java.util.List;

import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Entities.User;

public interface ContactService {

    //save contact
    Contact saveContact(Contact contact);
    //update contact
    Contact updateContact(Contact contact);
    //get contact
    List<Contact> getAll();
    //delete contact
    void deleteContact(String id);
    //get contact by ID
    Contact getContactById(String id);
    //search contact
    // List<Contact> searchContact(String name,String email,String phoneNumber);
    //get contact by User ID
    List<Contact> getContactByUserId(String userId);
    List<Contact> getByUser(User user);
}
