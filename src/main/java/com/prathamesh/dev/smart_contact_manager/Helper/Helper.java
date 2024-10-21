package com.prathamesh.dev.smart_contact_manager.Helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.var;

public class Helper {

    @SuppressWarnings("null")
    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {
                // login using google
                System.out.println("Getting Email From Google");
                username = oauth2User.getAttribute("email");

            } else if (clientId.equalsIgnoreCase("github")) {
                // login using github
                System.out.println("Getting Email From Github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }
            // login using linkedIN
            return username;

        } else {
            // login using manual format
            System.out.println("Getting Data From Local Database");
            return authentication.getName();
        }
    }
}
