package com.prathamesh.dev.smart_contact_manager.Helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
    public static void removeMessage(){
        try{
            System.out.println("Removing msg from Session");
            @SuppressWarnings("null")
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("message");
        }catch(Exception e){
            System.out.println("Error in session helper :"+e);
            e.printStackTrace();
        }
        
    }
}
