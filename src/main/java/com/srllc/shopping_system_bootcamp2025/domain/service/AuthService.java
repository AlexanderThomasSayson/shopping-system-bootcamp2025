package com.srllc.shopping_system_bootcamp2025.domain.service;

import com.srllc.shopping_system_bootcamp2025.domain.dto.AuthResponseDto;
import com.srllc.shopping_system_bootcamp2025.domain.dto.LoginDto;
import com.srllc.shopping_system_bootcamp2025.domain.dto.UserRegistrationDto;

public interface AuthService {

    String userRegistration(UserRegistrationDto userRegistrationDto);

    AuthResponseDto login(LoginDto loginDto);
}
