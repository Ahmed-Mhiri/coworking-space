package com.coworkingspace.server.Controllers;

import com.coworkingspace.server.models.User;
import com.coworkingspace.server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = authentication.getName();  // assuming email is the principal name
        User user = userService.findByEmail(email);

        // Remove sensitive data if needed (like password)
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }
}
