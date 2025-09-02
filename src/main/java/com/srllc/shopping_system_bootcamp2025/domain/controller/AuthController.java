package com.srllc.shopping_system_bootcamp2025.domain.controller;

import com.srllc.shopping_system_bootcamp2025.common.utils.ApiResponse;
import com.srllc.shopping_system_bootcamp2025.common.utils.DefaultResponse;
import com.srllc.shopping_system_bootcamp2025.domain.dto.AuthResponseDto;
import com.srllc.shopping_system_bootcamp2025.domain.dto.LoginDto;
import com.srllc.shopping_system_bootcamp2025.domain.dto.UserRegistrationDto;
import com.srllc.shopping_system_bootcamp2025.domain.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Operations for managing authentication and registration.")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User Registration", description = "This endpoint allows new registration of a user.")
    @PostMapping("/register")
    public ApiResponse<String> registration(UserRegistrationDto userRegistrationDto){
        String register = authService.userRegistration(userRegistrationDto);
        return DefaultResponse.displayCreatedObject(register);
    }

    @Operation(summary = "Authentication", description = "This endpoint authenticates a user by username or email.")
    @PostMapping("login")
    public ApiResponse<AuthResponseDto> login(LoginDto loginDto){
        AuthResponseDto responseDto = authService.login(loginDto);
        return DefaultResponse.displayLoginSuccess(responseDto);
    }
}
