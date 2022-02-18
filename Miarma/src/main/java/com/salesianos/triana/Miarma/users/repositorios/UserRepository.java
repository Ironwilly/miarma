package com.salesianos.triana.Miarma.users.repositorios;

import com.salesianos.triana.Miarma.users.model.Roles;
import com.salesianos.triana.Miarma.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByRol (Roles rol);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);

}