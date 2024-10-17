package com.prathamesh.dev.smart_contact_manager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.prathamesh.dev.smart_contact_manager.Entities.User;
import com.prathamesh.dev.smart_contact_manager.Entities.providers;
import com.prathamesh.dev.smart_contact_manager.Helper.AppConstants;
import com.prathamesh.dev.smart_contact_manager.Repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
                logger.info("Authentication successful for user {}", authentication.getName());
                DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
                // logger.info(user.getName());
                // user.getAttributes().forEach((key,value) -> {
                //     logger.info("{} = {}",key, value);
                // });
                // logger.info(user.getAuthorities().toString());

                String email = user.getAttribute("email").toString();
                String name = user.getAttribute("name").toString();
                String picture = user.getAttribute("picture").toString();

                // create user and save in db

                User user1 = new User();
                user1.setEmail(email);
                user1.setName(name);
                user1.setProfilePic(picture);
                user1.setPassword("password");
                user1.setUserID(UUID.randomUUID().toString());
                user1.setProvider(providers.GOOGLE);
                user1.setEnable(true);
                user1.setEamilVerified(true);
                user1.setProviderID(user.getName());
                user1.setRoleList(List.of(AppConstants.ROLE_USER));
                user1.setAbout("This Account Is Create Using Google...");

                User user2 = userRepo.findByEmail(email).orElse(null);
                if (user2 == null) {
                    userRepo.save(user1);
                    logger.info("User Saved" + email);
                }

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");   
          
    }
    
}
