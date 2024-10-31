package com.prathamesh.dev.smart_contact_manager.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.prathamesh.dev.smart_contact_manager.Entities.Contact;
import com.prathamesh.dev.smart_contact_manager.Entities.User;
import com.prathamesh.dev.smart_contact_manager.Forms.ContactForm;
import com.prathamesh.dev.smart_contact_manager.Forms.ContactSearchForm;
import com.prathamesh.dev.smart_contact_manager.Helper.AppConstants;
import com.prathamesh.dev.smart_contact_manager.Helper.Helper;
import com.prathamesh.dev.smart_contact_manager.Helper.Message;
import com.prathamesh.dev.smart_contact_manager.Helper.MessageType;
import com.prathamesh.dev.smart_contact_manager.Service.ContactService;
import com.prathamesh.dev.smart_contact_manager.Service.ImageService;
import com.prathamesh.dev.smart_contact_manager.Service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.val;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    // add contact page: handler
    @RequestMapping("/add")
    public String addContactPage(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult,
            Authentication authentication, HttpSession session) {

        // Validate Form Data
        if (bindingResult.hasErrors()) {
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

        String fileURL = imageService.uploadimage(contactForm.getContactImg());

        // image process
        logger.info("File Info : {}", contactForm.getContactImg().getOriginalFilename());

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        ;
        contact.setUser(user);
        contact.setWebSiteLink(contactForm.getWebSiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setPicture(fileURL);
        contactService.saveContact(contact);
        System.out.println(contactForm);

        // add message
        Message message = Message.builder().content("Add Contact Successfully").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/user/contacts/add";
    }

    // view contacts
    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {

        // load all user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contact> pageContacts = contactService.getByUser(user, page, size, sortBy, direction);
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    // Search Handler
    @RequestMapping(value = "/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {
        logger.info("Searching with field {} and keyword {}", contactSearchForm.getField(),
                contactSearchForm.getValue());
        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        // Input validation
        String trimmedField = contactSearchForm.getField().trim();
        if (trimmedField.isEmpty() || contactSearchForm.getValue().isEmpty()) {
            model.addAttribute("error", "Field and keyword must not be empty.");
            return "user/search"; // Return to the search page with an error
        }
        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            model.addAttribute("error", "Invalid sort direction.");
            return "user/search"; // Return to the search page with an error
        }

        // Handle search logic with case sensitivity
        Page<Contact> pageContacts = searchContacts(trimmedField, contactSearchForm.getValue(), size, page, sortBy,
                direction, user);

        model.addAttribute("ContactSearchForm", contactSearchForm);
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }

    private Page<Contact> searchContacts(String field, String value, int size, int page, String sortBy,
            String direction, User user) {
        switch (field) {
            case "name":
                return contactService.searchByName(value, size, page, sortBy, direction, user);
            case "email":
                return contactService.searchByEmail(value, size, page, sortBy, direction, user);
            case "phone":
                return contactService.searchByPhoneNumber(value, size, page, sortBy, direction, user);
            default:
                throw new IllegalArgumentException("Invalid search field: " + field);
        }
    }

    // delete contact
    @RequestMapping("/delete/{contactID}")
    public String deleteContact(@PathVariable("contactID") String contactID) {
        // Fetch the contact to get the image filename
        Contact contact = contactService.getContactById(contactID);
        String imageName = contact.getPicture(); // Assuming 'getPicture()' returns the image filename

        // Delete the contact
        contactService.deleteContact(contactID);
        logger.info("contactID {} deleted", contactID);

        // Delete the image file
        if (imageName != null) {
            Path imagePath = Paths.get("src/main/resources/static/upload/directory/" + imageName);
            try {
                Files.deleteIfExists(imagePath);
                logger.info("Image file {} deleted", imageName);
            } catch (IOException e) {
                logger.error("Error deleting image file {}: {}", imageName, e.getMessage());
            }
        }

        return "redirect:/user/contacts";
    }

    // update contact from view
    @RequestMapping("/view/{contactID}")
    public String updateContactFromView(@PathVariable("contactID") String contactID, Model model) {
        var contact = contactService.getContactById(contactID);

        if (contact == null) {
            logger.error("Contact with ID {} not found", contactID);
            model.addAttribute("errorMessage", "Contact not found.");
            return "error/contact_not_found";
        }

        ContactForm contactForm = new ContactForm();

        // Split the name into first and last name
        String[] nameParts = contact.getName().split(" ", 2); // Split into 2 parts to handle cases with multiple spaces
        contactForm.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
        contactForm.setLastName(nameParts.length > 1 ? nameParts[1] : ""); // Handle case where there's no last name

        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebSiteLink(contact.getWebSiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactID", contactID);

        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactID}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactID") String contactID, @ModelAttribute ContactForm contactForm,
            Model model) {
        // Fetch existing contact to preserve the existing picture if no new picture is
        // provided
        Contact existingContact = contactService.getContactById(contactID);
        var con = new Contact();
        con.setId(contactID);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebSiteLink(contactForm.getWebSiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // Process image
        MultipartFile imageFile = contactForm.getContactImg();
        if (imageFile != null && !imageFile.isEmpty()) {
            // Assuming you have an image service to handle file saving
            String imagePath = imageService.uploadimage(imageFile);
            con.setPicture(imagePath);
            // Delete the old image
            String oldImageName = existingContact.getPicture();
            if (oldImageName != null) {
                Path oldImagePath = Paths.get("src/main/resources/static/upload/directory/" + oldImageName);
                try {
                    Files.deleteIfExists(oldImagePath);
                    logger.info("Old image file {} deleted", oldImageName);
                } catch (IOException e) {
                    logger.error("Error deleting old image file {}: {}", oldImageName, e.getMessage());
                }
            }
        } else {
            con.setPicture(existingContact.getPicture());
        }

        // Update contact
        var updatedContact = contactService.updateContact(con);
        logger.info("Updated contact {}", updatedContact);

        model.addAttribute("message", Message.builder().content("Contact Updated").type(MessageType.green).build());
        return "redirect:/user/contacts/view/" + contactID;
    }

}
