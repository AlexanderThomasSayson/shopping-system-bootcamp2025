package com.srllc.shopping_system_bootcamp2025.domain.service.impl;

import com.srllc.shopping_system_bootcamp2025.domain.dao.RoleDao;
import com.srllc.shopping_system_bootcamp2025.domain.dao.UserDao;
import com.srllc.shopping_system_bootcamp2025.domain.dto.AuthResponseDto;
import com.srllc.shopping_system_bootcamp2025.domain.dto.LoginDto;
import com.srllc.shopping_system_bootcamp2025.domain.dto.UserRegistrationDto;
import com.srllc.shopping_system_bootcamp2025.domain.entity.Role;
import com.srllc.shopping_system_bootcamp2025.domain.entity.User;
import com.srllc.shopping_system_bootcamp2025.domain.service.AuthService;
import com.srllc.shopping_system_bootcamp2025.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String userRegistration(UserRegistrationDto userRegistrationDto) {
        log.info("Registering new user: {} ", userRegistrationDto.getUserName());
        validateUser(userRegistrationDto);

        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setUserName(userRegistrationDto.getUserName());

        // password encoded
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        // assign the default role
        Role userRole = roleDao.findByRoleName("CUSTOMER");

        //check if default role is assigned on the database
        if(userRole == null){
            log.error("Default role 'ROLE_USER' not found in the database");
            throw new IllegalArgumentException("User role not found. Please check the database setup!");
        }

        user.setRoles(Collections.singleton(userRole));

        // save into the database
        userDao.save(user);

        log.info("User {} registered successfully! with a role '{}' ", user.getUserName(), user.getRoles());
        return "User registered successfully!";
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {

        log.info("Attempting login for user: {} ", loginDto.getUserNameOrEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        log.info("User {} logged-in successfully!", loginDto.getUserNameOrEmail());
        return new AuthResponseDto (token);
    }


    // Validate if email or username already exist.
    private void validateUser(UserRegistrationDto userRegistrationDto){
        if(Boolean.TRUE.equals(userDao.existsByUserName(userRegistrationDto.getUserName()))){
            log.warn("Username: {} is already taken.", userRegistrationDto.getUserName());
            throw new IllegalArgumentException("Username already exist!");
        }

        if(Boolean.TRUE.equals(userDao.existsByEmail(userRegistrationDto.getEmail()))){
            log.warn("Email: {} is already taken.", userRegistrationDto.getEmail());
            throw new IllegalArgumentException("Email already exist!");
        }
    }
}

