package com.srllc.shopping_system_bootcamp2025.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "First name is required!")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters.")
    @Schema(example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters.")
    @Schema(example = "Doe")
    private String lastName;

    @NotBlank(message = "Username is required!")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores.")
    @Schema(example = "john_doe123")
    private String userName;

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format!")
    @Schema(example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
    )
    @Schema(example = "P@ssw0rd123")
    private String password;

}
