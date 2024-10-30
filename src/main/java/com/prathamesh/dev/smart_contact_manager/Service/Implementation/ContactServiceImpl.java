package com.prathamesh.dev.smart_contact_manager.Service.Implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Entities.User;
import com.prathamesh.dev.smart_contact_manager.Helper.ResourceNotFoundException;
import com.prathamesh.dev.smart_contact_manager.Repositories.ContactRepo;
import com.prathamesh.dev.smart_contact_manager.Service.ContactService;


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

    @Override
    public Page<Contact> getByUser(User user,int page,int size,String sortBy,String direction) {
        
        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        
       return contactRepository.findByUser(user,pageable);
    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user) {
        Sort sort = order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return contactRepository.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,User user) {
        Sort sort = order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return contactRepository.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order,User user) {
                Sort sort = order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
                var pageable = PageRequest.of(page,size,sort);
                return contactRepository.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }

    
}
