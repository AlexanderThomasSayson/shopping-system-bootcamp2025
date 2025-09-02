package com.srllc.shopping_system_bootcamp2025.domain.controller;

import com.srllc.shopping_system_bootcamp2025.common.utils.ApiResponse;
import com.srllc.shopping_system_bootcamp2025.common.utils.DefaultResponse;
import com.srllc.shopping_system_bootcamp2025.domain.dto.AuthResponseDto;
import com.srllc.shopping_system_bootcamp2025.domain.dto.LoginDto;
import com.srllc.shopping_system_bootcamp2025.domain.dto.UserRegistrationDto;
import com.srllc.shopping_system_bootcamp2025.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> registration(UserRegistrationDto userRegistrationDto){
        String register = authService.userRegistration(userRegistrationDto);
        return DefaultResponse.displayCreatedObject(register);
    }

    @PostMapping("login")
    public ApiResponse<AuthResponseDto> login(LoginDto loginDto){
        AuthResponseDto responseDto = authService.login(loginDto);
        return DefaultResponse.displayLoginSuccess(responseDto);
    }
}
