package com.prathamesh.dev.smart_contact_manager.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Entities.User;

public interface ContactService {

    // save contact
    Contact saveContact(Contact contact);

    // update contact
    Contact updateContact(Contact contact);

    // get contact
    List<Contact> getAll();

    // delete contact
    void deleteContact(String id);

    // get contact by ID
    Contact getContactById(String id);

    // search contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user);

    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,User user);

    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,User user);

    // get contact by User ID
    List<Contact> getContactByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection);
}
