package br.com.jaxmund.statelessauthapi.repository;

import br.com.jaxmund.statelessauthapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
