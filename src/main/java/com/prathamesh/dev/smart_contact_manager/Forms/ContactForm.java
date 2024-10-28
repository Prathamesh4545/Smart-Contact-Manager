package com.prathamesh.dev.smart_contact_manager.Forms;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
    private String firstName;
    private String lastName;
    private String name;  // full name (first name + last name)
    private String email;
    private String phoneNumber;
    private String address;
    private String description;
    private boolean favorite;
    private String webSiteLink;
    private String linkedInLink;
    private MultipartFile profileImage;

    public String getName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

}
