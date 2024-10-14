package org.openapitools.repository;

import org.openapitools.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Suche nach Benutzer anhand der E-Mail
    Optional<User> findByEmail(String email);
}
