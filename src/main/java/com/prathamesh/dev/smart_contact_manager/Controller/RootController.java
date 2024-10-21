package com.prathamesh.dev.smart_contact_manager.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.prathamesh.dev.smart_contact_manager.Entities.User;
import com.prathamesh.dev.smart_contact_manager.Helper.Helper;
import com.prathamesh.dev.smart_contact_manager.Service.UserService;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ModelAttribute
    public void addLoggedInUserInfo(Model model, Authentication authentication) {

        if(authentication == null){
            return;
        }

        System.out.println("Adding LoggedIn User Information to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User Profile Page Accessed by: " + username);
        User user = userService.getUserByEmail(username);
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedInUser", user);
    }
}
