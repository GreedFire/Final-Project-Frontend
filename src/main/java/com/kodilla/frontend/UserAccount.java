package com.kodilla.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

//SINGLETON
public final class UserAccount {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccount.class);
    private static UserAccount userAccountInstance = null;
    private Long id = 0L;

    private UserAccount() {

    }

    public static UserAccount getInstance() {
        if (userAccountInstance == null) {
            synchronized (UserAccount.class) {
                if (userAccountInstance == null) {
                    userAccountInstance = new UserAccount();
                }
            }
        }
        return userAccountInstance;
    }

    public static boolean isInstanceNull() {
        boolean result = true;
        if (userAccountInstance != null)
            result = false;
        return result;
    }

    public void setInstanceNull() {
        userAccountInstance = null;
    }

    public void signIn() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(UrlGenerator.userSignInURL(this.id), null);
    }

    public void signOut() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(UrlGenerator.userSignOutURL(this.id), null);
        LOGGER.info("Logged out user with id " + this.id);
        setInstanceNull();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
