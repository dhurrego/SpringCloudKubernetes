package org.deivurca.springcloud.ms.cursos.services;

import org.deivurca.springcloud.ms.cursos.models.Curso;
import org.deivurca.springcloud.ms.cursos.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listarTodo();
    Optional<Curso> listarPorId(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);
    void eliminarCursoUsuarioPorUsuarioId(Long usuarioId);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
}
