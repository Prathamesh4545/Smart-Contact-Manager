package com.prathamesh.dev.smart_contact_manager.Forms;

import org.springframework.web.multipart.MultipartFile;

import com.prathamesh.dev.smart_contact_manager.Validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Name is required")
    @Size(message = "Min 3 Character is rqeuired", min = 3)
    private String firstName;
    private String lastName;

    private String name; // full name (first name + last name)

    @Email(message = "Invalid Email Address [example@gmail.com]")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone No is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    private String description;
    private boolean favorite;
    private String webSiteLink;
    private String linkedInLink;

    @ValidFile
    private MultipartFile contactImg;

    public String getName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

}
