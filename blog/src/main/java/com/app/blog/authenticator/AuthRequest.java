package com.app.blog.authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.app.blog.models.Profile;

import reactor.core.publisher.Mono;

@Service
//@Scope("prototype")
public class AuthRequest {

    @Autowired
    private AuthConfig authConfig;

    // call jwt server api

    public boolean verifyToken(String token) {

        try {

            Mono<Profile> response = authConfig.webClient().get()

                    .uri("/profile")
                    .header("Authorization", token, null)
                    .retrieve()
                    .bodyToMono(Profile.class);

            if (response.block() != null) {
                Profile user = response.block();
                System.out.println("User : " + user);
                return true;
            }
            return false;


        } catch (Exception e) {
            System.out.println("Error In Authenticating request : " + e);
            return false;
        }

    }

    public Profile getProfile(String token) {

        try {

            Mono<Profile> response = authConfig.webClient().get()

                    .uri("/profile")
                    .header("Authorization", token, null)
                    .retrieve()

                    .bodyToMono(Profile.class);

            if (response.block() != null) {
                Profile user = response.block();
//                System.out.println("User : " + user);
                return user;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error In Authencating request : " + e);
            return null;
        }
    }
}
