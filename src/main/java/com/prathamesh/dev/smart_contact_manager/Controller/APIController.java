package com.prathamesh.dev.smart_contact_manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Service.ContactService;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    ContactService contactService;

    // get contact of user
    @GetMapping("/contact/{contactID}")
    public Contact getContact(@PathVariable String contactID) {
        return contactService.getContactById(contactID);
    }
}
