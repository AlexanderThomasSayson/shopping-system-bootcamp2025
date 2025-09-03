package com.srllc.shopping_system_bootcamp2025.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get JWT token from the HTTP Request || Authorization header
        String token = getTokenFromRequest(request);

        // Validate the token:
        // if the token is present
        // if the token is valid
        // if the token is expired or invalid
        if (StringUtils.hasText(token) && jwtTokenProvider.isValidToken(token)) {

            // extract the username from the token.
            String userName = jwtTokenProvider.getUsername(token);

            //  calls the custom user details service includes the username and roles / authorities.
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            // Creates authentication object
            // Store user's identity + roles
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Saves the authenticated user into spring security context.
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }

        // at this point spring security knows who the user is.
        filterChain.doFilter(request, response);

    }




    // Look for the Authorization header
    // Bearer eyskjdakjsfkajsbkajdb.ushkajshkajsnfas.khasbdkjahsbdjahsd
    private  String getTokenFromRequest(HttpServletRequest httpServletRequest){

        // We can see on postman that we have an 'Authorization' so we will get it from the header.
        String bearerToken = httpServletRequest.getHeader("Authorization");

        // Check if the request starts with 'Bearer ' (7) with a space then followed by the token.
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;

    }
}
