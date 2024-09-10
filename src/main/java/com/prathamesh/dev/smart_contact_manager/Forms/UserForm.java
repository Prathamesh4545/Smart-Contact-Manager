package com.prathamesh.dev.smart_contact_manager.Forms;


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
    private String name;
    private String email;
    private String password;
    private String about;
    private String phoneNumber;
    private String profilePic;
}

