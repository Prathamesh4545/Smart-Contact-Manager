package com.prathamesh.dev.smart_contact_manager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {

    //user dashboard page
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    //user profile page
    @RequestMapping(value = "/profile")
    public String userProfile() {
        return "user/profile";
    }
    
    
    //user add contacts page

    //user view contacts

    //user edit contact

    //user delete contact

    //user search contact
}
