package br.com.jaxmund.statelessauthapi.controller;

import br.com.jaxmund.statelessauthapi.dto.AuthRequest;
import br.com.jaxmund.statelessauthapi.dto.TokenDTO;
import br.com.jaxmund.statelessauthapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthControler {

    private AuthService authService;

    @PostMapping("login")
    public TokenDTO login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("token/validate")
    public TokenDTO tokenValidate(@RequestHeader String accessToken) {
        return authService.validateToken(accessToken);
    }

}
