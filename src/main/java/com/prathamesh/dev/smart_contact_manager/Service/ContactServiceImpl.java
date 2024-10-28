package com.prathamesh.dev.smart_contact_manager.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Helper.ResourceNotFoundException;
import com.prathamesh.dev.smart_contact_manager.Repositories.ContactRepo;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepo contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
       String contactID = UUID.randomUUID().toString();
       contact.setId(contactID);
       return contactRepository.save(contact);

    }

    @Override
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public void deleteContact(String id) {
        var contact = contactRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Contact Not Found With Id : "+id));
        contactRepository.delete(contact);;
    }

    @Override
    public Contact getContactById(String id) {
        return contactRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Contact Not Found With Id : "+id));
    }

    // @Override
    // public List<Contact> searchContact(String name,String email,String phoneNumber) {
    //     return contactRepository.searchContact(name,email,phoneNumber);
    // }

    @Override
    public List<Contact> getContactByUserId(String userId) {
        return contactRepository.findByUserId(userId);
    }
    
}
