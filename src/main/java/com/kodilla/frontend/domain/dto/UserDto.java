package com.kodilla.frontend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private boolean signedIn;

    public UserDto(String username, String password, String email, String firstname, String lastname, LocalDate birthdate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        signedIn = false;
    }

}
