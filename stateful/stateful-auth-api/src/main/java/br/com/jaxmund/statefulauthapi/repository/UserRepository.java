package br.com.jaxmund.statefulauthapi.repository;


import br.com.jaxmund.statefulauthapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
