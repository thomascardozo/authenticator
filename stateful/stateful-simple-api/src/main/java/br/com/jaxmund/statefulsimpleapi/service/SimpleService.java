package br.com.jaxmund.statefulsimpleapi.service;

import br.com.jaxmund.statefulsimpleapi.dto.SimpleResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class SimpleService {

    private final TokenService tokenService;

    public SimpleResponse getData(String accessToken){
        tokenService.validateToken(accessToken);
        var authUser = tokenService.getAuthenticatedUser(accessToken);
        var ok = HttpStatus.OK;
        return new SimpleResponse(ok.name(),ok.value(), authUser);
    }
}
