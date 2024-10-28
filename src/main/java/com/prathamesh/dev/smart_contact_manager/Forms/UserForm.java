package com.prathamesh.dev.smart_contact_manager.Forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(message="Min 3 Character is rqeuired",min = 3)
    private String name;
    
    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(message="Min 8 Character is rqeuired", min = 8)
    private String password;
    
    @NotBlank(message = "About is required")
    private String about;

    @Size(min = 8,max = 12, message = "Invalid Phone Number")
    private String phoneNumber;
}

