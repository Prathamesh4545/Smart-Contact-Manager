package com.prathamesh.dev.smart_contact_manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Entities.User;
import com.prathamesh.dev.smart_contact_manager.Forms.ContactForm;
import com.prathamesh.dev.smart_contact_manager.Helper.Helper;
import com.prathamesh.dev.smart_contact_manager.Helper.Message;
import com.prathamesh.dev.smart_contact_manager.Helper.MessageType;
import com.prathamesh.dev.smart_contact_manager.Service.ContactService;
import com.prathamesh.dev.smart_contact_manager.Service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)  
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult,Authentication authentication,HttpSession session){

        // Validate Form Data
        if(bindingResult.hasErrors()){
            session.setAttribute("message", Message.builder()
                    .content("Plesase Correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        // Process Form Data
        String userName = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(userName);

        // process contact pitcher


        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());;
        contact.setUser(user);
        contact.setWebSiteLink(contactForm.getWebSiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contactService.saveContact(contact);
        System.out.println(contactForm);

        //add message
        Message message = Message.builder().content("Add Contact Successfully").type(MessageType.green).build();
        session.setAttribute("message",message);
        return "redirect:/user/contacts/add";
    }
}
