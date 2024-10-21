package com.prathamesh.dev.smart_contact_manager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @SuppressWarnings("null")
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication successful for user {}", authentication.getName());

        // Identify Provider

        var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);

        DefaultOAuth2User oAuthUser = (DefaultOAuth2User) authentication.getPrincipal();

        logger.info(oAuthUser.getName());
        oAuthUser.getAttributes().forEach((key, value) -> {
            logger.info("{} = {}", key, value);
        });
        // logger.info(oAuthUser.getAuthorities().toString());

        User user = new User();
        user.setUserID(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEamilVerified(true);
        user.setEnable(true);

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            // google
            // create user and save in db
            user.setEmail(oAuthUser.getAttribute("email").toString());
            user.setName(oAuthUser.getAttribute("name").toString());
            user.setProfilePic(oAuthUser.getAttribute("picture").toString());
            user.setProvider(providers.GOOGLE);
            user.setProviderID(oAuthUser.getName());
            user.setPassword("Temporary Password");
            user.setAbout("This Account Is Create Using Google...");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            // github
            // create user and save in db
            String email = oAuthUser.getAttribute("email") != null ? oAuthUser.getAttribute("email").toString() : oAuthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oAuthUser.getAttribute("avatar_url").toString();
            String name = oAuthUser.getAttribute("login").toString();
            String providerUserId = oAuthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderID(providerUserId);
            user.setProvider(providers.GITHUB); 
            user.setPassword("Temporary Password");
            user.setAbout("This Account Is Create Using GitHub...");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {
            // linkedin
            // create user and save in db
            
            
        } else {
            logger.info("Unknown provider");
        }

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
        userRepo.save(user);
        logger.info("User Saved" + user);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}
