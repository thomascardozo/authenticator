package br.com.jaxmund.statelesssimpleapi.service;

import br.com.jaxmund.statelesssimpleapi.dto.AuthUserResponse;
import br.com.jaxmund.statelesssimpleapi.exception.AuthenticationException;
import br.com.jaxmund.statelesssimpleapi.exception.ValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final Integer ONE_DAY_IN_HOURS = 24;

    private static final Integer TOKEN_INDEX = 1;

    private static final String EMPTY_SPACE = " ";

    @Value("${app.token.secret-key}")
    private String secretKey;

    public AuthUserResponse getAuthUserResponse(String token){
        var tokenClaims = getClaims(token);
        var userId = Integer.valueOf((String) tokenClaims.get("id"));
        return new AuthUserResponse(userId, (String) tokenClaims.get("username"));
    };

    private Claims getClaims(String token){

        var accessToken = extractToken(token);
        try {
            return Jwts
                    .parser()
                    .verifyWith(generateSign())
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();

        } catch(Exception e){
            throw new AuthenticationException("Invalid token " + e.getMessage());
        }
    }

    public void validateAccessToken(String token){
        getClaims(token);
    }

    private SecretKey generateSign(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private String extractToken(String token){
        if(ObjectUtils.isEmpty(token)){
            throw new ValidationException("The access was not informed!");
        }
        if (token.contains(EMPTY_SPACE)){
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }
}
