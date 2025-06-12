package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.SignUpRequest;
import com.coworkingspace.server.models.User;

import java.util.Optional;

public interface UserService {

    User registerUser(SignUpRequest signUpRequest);

    void confirmUser(String confirmationToken);

    User findByEmail(String email);

    void initiatePasswordReset(String email);

    void resetPassword(String token, String newPassword);
    // Add sign-in method
    User signIn(String email, String password);

    // Add sign-out if you want to handle something here (usually handled by Spring Security)
    void signOut();
    Optional<User> findByConfirmationToken(String token);
    User saveUser(User user);
}
