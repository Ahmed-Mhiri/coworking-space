package com.coworkingspace.server.Controllers;

import com.coworkingspace.server.DTOs.ForgotPasswordRequest;
import com.coworkingspace.server.DTOs.LoginRequest;
import com.coworkingspace.server.DTOs.ResetPasswordRequest;
import com.coworkingspace.server.DTOs.SignUpRequest;
import com.coworkingspace.server.models.Role;
import com.coworkingspace.server.models.User;
import com.coworkingspace.server.services.EmailService;
import com.coworkingspace.server.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailService emailService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        if (signUpRequest.getRole() == Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin cannot sign up.");
        }

        User user = userService.registerUser(signUpRequest);

        String token = user.getConfirmationToken();
        String confirmationUrl = "http://localhost:8080/api/auth/confirm?token=" + token;
        emailService.sendEmail(user.getEmail(), "Confirm your account", "Click to confirm: " + confirmationUrl);

        return ResponseEntity.ok("User registered. Please check your email to confirm.");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Force session creation and store the security context
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );

            User user = userService.findByEmail(loginRequest.getEmail());
            if (!user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account not activated");
            }

            return ResponseEntity.ok(Map.of("message", "Successfully signed in", "sessionId", session.getId()));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<Map<String, String>> signOut(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Signout endpoint called");

        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);

        Map<String, String> resp;

        if (session != null) {
            session.invalidate();
            logger.info("Session invalidated");
            resp = Map.of("message", "Signed out successfully");
        } else {
            logger.info("No session found");
            resp = Map.of("message", "No active session to sign out from");
        }

        logger.info("Returning response: {}", resp);
        return ResponseEntity.ok().body(resp);
    }
    @GetMapping("/confirm")
    public ResponseEntity<?> confirmUser(@RequestParam("token") String token) {
        Optional<User> optionalUser = userService.findByConfirmationToken(token);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        User user = optionalUser.get();
        user.setEnabled(true);
        user.setConfirmationToken(null);
        userService.saveUser(user);

        return ResponseEntity.ok("Account confirmed successfully");
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            userService.initiatePasswordReset(request.getEmail());
            return ResponseEntity.ok("Password reset email sent.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity.ok("Password reset successful.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

}
