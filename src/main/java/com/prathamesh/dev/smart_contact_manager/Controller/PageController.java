package com.prathamesh.dev.smart_contact_manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.prathamesh.dev.smart_contact_manager.Entities.User;
import com.prathamesh.dev.smart_contact_manager.Forms.UserForm;
import com.prathamesh.dev.smart_contact_manager.Helper.Message;
import com.prathamesh.dev.smart_contact_manager.Helper.MessageType;
import com.prathamesh.dev.smart_contact_manager.Service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }
    // Home Page route
    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Prathamesh");
        model.addAttribute("work", "Software Developer");
        return "home";
    }

    // About Page route
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About page loading");
        return "about";
    }

    // Services Page route
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services page loading");
        return "services";
    }
    // Services Page route
    @RequestMapping("/contact")
    public String contactPage() {
        System.out.println("Contact page loading");
        return "contact";
    }
    // Services Page route
    @RequestMapping("/login")
    public String loginPage() {
        System.out.println("Login page loading");
        return "login";
    }
    // Services Page route
    // Registration Page
    @RequestMapping("/register")
    public String registerPage(Model model) {
        
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        // userForm.setName("Prathamesh");
        // userForm.setPhoneNumber("12345689");
        return "register";
    }

    // Processing Register
    @RequestMapping(value = "/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult,HttpSession session){
        
        if(bindingResult.hasErrors()){
            return "register";
        }

        // UserForm ---> user
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic(userForm.getProfilePic())
        // .build();


        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("img/user-circles-set.png");
    
        userService.saveUser(user);

        //add message
        Message message = Message.builder().content("Register  Successfully").type(MessageType.blue).build();

        session.setAttribute("message",message);

        return "redirect:/register";
    }
}
