package com.prathamesh.dev.smart_contact_manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Entities.User;
import com.prathamesh.dev.smart_contact_manager.Forms.ContactForm;
import com.prathamesh.dev.smart_contact_manager.Helper.Helper;
import com.prathamesh.dev.smart_contact_manager.Service.ContactService;
import com.prathamesh.dev.smart_contact_manager.Service.UserService;

import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;
    
    //add contact page: handler
    @RequestMapping("/add")
    public String addContactPage(Model model){
        ContactForm contactForm = new ContactForm();
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)  
    public String saveContact(@ModelAttribute ContactForm contactForm,Authentication authentication){

        // Process Form Data

        String userName = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(userName);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setWebSiteLink(contactForm.getWebSiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contactService.saveContact(contact);
        System.out.println(contactForm);

        return "redirect:/user/contacts/add";
    }
}
