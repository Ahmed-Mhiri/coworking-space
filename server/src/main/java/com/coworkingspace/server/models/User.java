package com.coworkingspace.server.models;// User.java (abstract base class)
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    protected Role role;
    // Only applicable to freelancers/interns
    protected boolean enabled = true; // default true for admin

    @Column(name = "confirmation_token")
    private String confirmationToken;  // For email verification

    @Column(name = "password_reset_token")
    private String passwordResetToken;  // Separate token for reset password

    @Column(name = "password_reset_token_expiry")
    private LocalDateTime passwordResetTokenExpiry;  // Expiry date/time for token

}
