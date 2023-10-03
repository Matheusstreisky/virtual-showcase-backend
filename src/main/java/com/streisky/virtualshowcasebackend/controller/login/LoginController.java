package com.streisky.virtualshowcasebackend.controller.login;

import com.streisky.virtualshowcasebackend.config.security.token.TokenDTO;
import com.streisky.virtualshowcasebackend.config.security.token.TokenService;
import com.streisky.virtualshowcasebackend.dto.account.AccountDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid AccountDTO accountDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountDTO.login(), accountDTO.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String token = tokenService.generate(authentication);

        return ResponseEntity.ok(new TokenDTO(token));
    }
}
