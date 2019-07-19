package com.kodilla.frontend.domain.dto;

import com.kodilla.frontend.view.NavigateBar;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//SINGLETON
public final class UserAccount {
    private static UserAccount userAccountInstance = null;
    private Long id = 0L;

    private UserAccount(){

    }

    public void setInstanceNull(){
        userAccountInstance = null;
    }

    public static UserAccount getInstance() {
        if (userAccountInstance == null) {
            synchronized(UserAccount.class) {
                if (userAccountInstance == null) {
                    userAccountInstance = new UserAccount();
                }
            }
        }
        return userAccountInstance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void signIn(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/signIn")
                .queryParam("userId", this.id)
                .build().encode().toUri();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(url,null);

    }

    public void signOut(){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/signOut")
                .queryParam("userId", this.id)
                .build().encode().toUri();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(url,null);

        setInstanceNull(); // CHECK IT
    }

    public Long getId() {
        return id;
    }
}
