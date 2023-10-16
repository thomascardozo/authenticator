package br.com.jaxmund.statelessauthapi.service;

import br.com.jaxmund.statelessauthapi.dto.AuthRequest;
import br.com.jaxmund.statelessauthapi.dto.TokenDTO;
import br.com.jaxmund.statelessauthapi.exception.ValidationException;
import br.com.jaxmund.statelessauthapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenDTO login(AuthRequest request) {
        var user = userRepository
                .findByUsername(request.username())
                .orElseThrow(() -> new ValidationException("User not found!"));
        var accessToken = jwtService.createToken(user);
        validatePassword(request.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    private void validatePassword(String rawPassword, String encodedPassword){
        if(!passwordEncoder.matches(rawPassword, encodedPassword)){
            throw new ValidationException("The password is not correct!");
        }
    }

    public TokenDTO validateToken (String accessToken) {
        validateExistingToken(accessToken);
        jwtService.validateAccessToken(accessToken);
        return new TokenDTO(accessToken);
    }

    private void validateExistingToken(String accessToken) {
        if(ObjectUtils.isEmpty(accessToken)){
            throw new ValidationException("The access token must be informed!");
        }
    }


}
