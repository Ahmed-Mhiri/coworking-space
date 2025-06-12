package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.DTOs.SignUpRequest;
import com.coworkingspace.server.models.Freelancer;
import com.coworkingspace.server.models.Intern;
import com.coworkingspace.server.models.Role;
import com.coworkingspace.server.models.User;
import com.coworkingspace.server.repositories.UserRepository;
import com.coworkingspace.server.services.EmailService;
import com.coworkingspace.server.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticationException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;  // add this


    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService,
                           AuthenticationManager authenticationManager) {  // <-- add here
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager; // now this works
    }

    @Override
    public User registerUser(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user;
        if (request.getRole() == Role.FREELANCER) {
            user = new Freelancer();
        } else if (request.getRole() == Role.INTERN) {
            user = new Intern();
        } else {
            throw new RuntimeException("Invalid role");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false); // disabled until email confirmed
        user.setConfirmationToken(UUID.randomUUID().toString());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        // Send confirmation email
        String confirmationLink = "http://your-frontend-url/confirm?token=" + savedUser.getConfirmationToken();
        emailService.sendEmail(savedUser.getEmail(), "Confirm your account",
                "Please confirm your account by clicking this link: " + confirmationLink);

        return savedUser;
    }

    @Override
    public void confirmUser(String confirmationToken) {
        User user = userRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new RuntimeException("Invalid confirmation token"));
        user.setEnabled(true);
        user.setConfirmationToken(null);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No user found with this email."));

        String resetToken = UUID.randomUUID().toString();
        user.setConfirmationToken(resetToken);
        userRepository.save(user);

        String resetLink = "http://localhost:4200/reset-password?token=" + resetToken;
        emailService.sendEmail(email, "Reset your password", "Click here to reset your password: " + resetLink);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token."));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setConfirmationToken(null);
        userRepository.save(user);
    }
    @Override
    public User signIn(String email, String password) {
        try {
            // Authenticate the user with Spring Security
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // If authentication succeeds, fetch and return user details from the database
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        } catch (AuthenticationException e) {
            // Authentication failed (bad credentials, disabled, etc.)
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Override
    public void signOut() {
        // Usually sign out is handled by Spring Security via HttpSecurity.logout()
        // But if you want, you can invalidate tokens or sessions here.
    }
    @Override
    public Optional<User> findByConfirmationToken(String token) {
        return userRepository.findByConfirmationToken(token);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
