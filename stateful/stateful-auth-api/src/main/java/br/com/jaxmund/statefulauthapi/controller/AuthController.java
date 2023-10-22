package br.com.jaxmund.statefulauthapi.controller;

import br.com.jaxmund.statefulauthapi.dto.AuthRequest;
import br.com.jaxmund.statefulauthapi.dto.AuthUserResponse;
import br.com.jaxmund.statefulauthapi.dto.TokenDTO;
import br.com.jaxmund.statefulauthapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public TokenDTO login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("token/validate")
    public TokenDTO tokenValidate(@RequestHeader String accessToken) {
        return authService.validateToken(accessToken);
    }

    @PostMapping("logout")
    public HashMap<String, Object> logout(@RequestHeader String accessToken){
        authService.logout(accessToken);

        var response = new HashMap<String, Object>();
        var ok = HttpStatus.OK;

        response.put("status", ok.name());
        response.put("code", ok.value());

        return response;
    }

    @GetMapping("user")
    public AuthUserResponse getAuthUserResponse(@RequestHeader String accessToken){
        return authService.getAuthenticatedUser(accessToken);
    }

}
