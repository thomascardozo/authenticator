package br.com.jaxmund.statefulsimpleapi.service;

import br.com.jaxmund.statefulsimpleapi.client.TokenClient;
import br.com.jaxmund.statefulsimpleapi.dto.AuthUserResponse;
import br.com.jaxmund.statefulsimpleapi.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TokenService {

    private final TokenClient tokenClient;

    public void validateToken(String token) {

        try {
            log.info("Sending request to token validation {}", token);
            var response =  tokenClient.tokenValidate(token);
            log.info("Token is valid {}", response.accessToken());
        } catch (Exception ex){
            throw new AuthenticationException("Auth error " + ex.getMessage());
        }
    }

    public AuthUserResponse getAuthenticatedUser(String token){
        try {
            log.info("Sending request to auth user {}", token);
            var response = tokenClient.getAuthenticatedUser(token);
            log.info("Auth user found: {} and token {}", response.toString(), token);
            return response;
        } catch (Exception ex){
            throw  new AuthenticationException("Auth error to get authenticated user " + ex.getMessage());
        }
    }

}
