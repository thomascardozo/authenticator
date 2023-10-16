package br.com.jaxmund.statelesssimpleapi.service;

import br.com.jaxmund.statelesssimpleapi.dto.SimpleResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleService {

    private final JwtService jwtService;
    public SimpleResponse getData(String accessToken){
        jwtService.validateAccessToken(accessToken);
        var authUser = jwtService.getAuthUserResponse(accessToken);
        var ok = HttpStatus.OK;
        return new SimpleResponse(ok.name(), ok.value(), authUser);
    }


}
