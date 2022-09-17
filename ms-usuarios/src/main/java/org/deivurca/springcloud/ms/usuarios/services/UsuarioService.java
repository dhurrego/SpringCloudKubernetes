package org.deivurca.springcloud.ms.usuarios.services;

import org.deivurca.springcloud.ms.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listarTodo();
    List<Usuario> listarUsuariosPorIds(Iterable<Long> ids);
    Optional<Usuario> listarPorId(Long id);
    Optional<Usuario> listarUsuarioPorEmail(String email);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
}
