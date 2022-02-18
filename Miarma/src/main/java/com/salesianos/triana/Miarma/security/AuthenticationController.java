package com.salesianos.triana.Miarma.security;


import com.salesianos.triana.Miarma.security.dto.JwtUsuarioResponse;
import com.salesianos.triana.Miarma.security.dto.LoginDto;
import com.salesianos.triana.Miarma.security.jwt.JwtProvider;
import com.salesianos.triana.Miarma.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = jwtProvider.generateToken(authentication);


        User user = (User) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(user, jwt));

    }

    @GetMapping("/me")
    public ResponseEntity<?> misDatos(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(convertUserToJwtUserResponse(user, null));
    }

    private JwtUsuarioResponse convertUserToJwtUserResponse(User user, String jwt) {
        return JwtUsuarioResponse.builder()
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .nick(user.getNick())
                .fechNac(user.getFechNaci())
                .avatar(user.getAvatar())
                .token(jwt)
                .build();
    }


}