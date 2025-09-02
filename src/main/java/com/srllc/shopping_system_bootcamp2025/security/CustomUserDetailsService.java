package com.srllc.shopping_system_bootcamp2025.security;

import com.srllc.shopping_system_bootcamp2025.domain.dao.UserDao;
import com.srllc.shopping_system_bootcamp2025.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {

        // Find user by UserName or Email
        // Authentication failure if no username or email found.
        User user = userDao.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email :" + userNameOrEmail));


        // convert roles into authorities
        // role -> ADMIN -> @PreAuthorize = ROLE_ADMIN
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toSet());

        // Username --> login from username or email
        // Password --> stored in your DB (HASHED for preparation)
        // Authorities --> user roles, converted into spring security format.
        return new org.springframework.security.core.userdetails.User(
                userNameOrEmail,
                user.getPassword(),
                authorities
        );
    }
}

