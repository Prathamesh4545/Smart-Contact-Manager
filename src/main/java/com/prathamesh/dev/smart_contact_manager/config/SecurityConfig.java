package com.prathamesh.dev.smart_contact_manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.prathamesh.dev.smart_contact_manager.Service.SecurityCustomUserDetailService;


@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenicationSuccessHandler handler;


      // Constants
      private static final String LOGIN_URL = "/login";
      private static final String DASHBOARD_URL = "/user/profile";
      private static final String AUTHENTICATE_URL = "/authenticate";
      private static final String EMAIL_PARAMETER = "email";
      private static final String PASSWORD_PARAMETER = "password";

    // configuration of authentication provide for spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // configuration
        http.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home","/register","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default login
        // http.formLogin(Customizer.withDefaults());

        http.formLogin(formLogin -> {
            formLogin.loginPage(LOGIN_URL);
            formLogin.loginProcessingUrl(AUTHENTICATE_URL);
            formLogin.successForwardUrl(DASHBOARD_URL);
            formLogin.failureForwardUrl(LOGIN_URL + "?error=true");
            formLogin.usernameParameter(EMAIL_PARAMETER);
            formLogin.passwordParameter(PASSWORD_PARAMETER);
            // formLogin.failureHandler(new AuthenticationFailureHandler() {

            //     @Override
            //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            //             AuthenticationException exception) throws IOException, ServletException {
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
            //     }
                
            // });
            // formLogin.successHandler(new AuthenticationSuccessHandler() {

            //     @Override
            //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            //             Authentication authentication) throws IOException, ServletException {
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
            //     }
                
            // });
        });

        http.csrf(AbstractHttpConfigurer::disable);
        http.logout(logoutform -> {
            logoutform.logoutUrl("/logout");
            logoutform.logoutSuccessUrl("/login?logout=true");
        });

        // oauth configurations
        // http.oauth2Login(Customizer.withDefaults());
        
        http.oauth2Login(oauth2Login -> {
            oauth2Login.loginPage("/login");
            oauth2Login.successHandler(handler);
        });


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
