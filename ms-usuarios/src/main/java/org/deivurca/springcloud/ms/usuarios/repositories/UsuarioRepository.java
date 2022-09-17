package org.deivurca.springcloud.ms.usuarios.repositories;

import org.deivurca.springcloud.ms.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}