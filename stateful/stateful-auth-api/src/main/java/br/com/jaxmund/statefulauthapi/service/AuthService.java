package br.com.jaxmund.statefulauthapi.service;

import br.com.jaxmund.statefulauthapi.dto.AuthRequest;
import br.com.jaxmund.statefulauthapi.dto.AuthUserResponse;
import br.com.jaxmund.statefulauthapi.dto.TokenDTO;
import br.com.jaxmund.statefulauthapi.exception.AuthenticationException;
import br.com.jaxmund.statefulauthapi.exception.ValidationException;
import br.com.jaxmund.statefulauthapi.model.User;
import br.com.jaxmund.statefulauthapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public TokenDTO login(AuthRequest request) {
        var user = findByUsername(request.username());
        var accessToken = tokenService.createToken(user.getUsername());
        validatePassword(request.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    public AuthUserResponse getAuthenticatedUser (String accessToken){
        var tokenData = tokenService.getTokenData(accessToken);
        var user = findByUsername(tokenData.username());
        return new AuthUserResponse(user.getId(), user.getUsername());
    }

    public void logout(String accessToken) {
        tokenService.deleteRedisToken(accessToken);
    }

    private User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ValidationException("User not found!"));
    }

    private void validateExistingToken(String accessToken) {
        if(ObjectUtils.isEmpty(accessToken)){
            throw new ValidationException("The access token must be informed!");
        }
    }

    private void validatePassword(String rawPassword, String encodedPassword){
        if(!passwordEncoder.matches(rawPassword, encodedPassword)){
            throw new ValidationException("The password is not correct!");
        }
    }

    public TokenDTO validateToken (String accessToken) {
        validateExistingToken(accessToken);

        var valid = tokenService.validateAccessToken(accessToken);
        if(valid){
            return new TokenDTO(accessToken);
        }
        throw new AuthenticationException("Invalid token!");
    }

}
