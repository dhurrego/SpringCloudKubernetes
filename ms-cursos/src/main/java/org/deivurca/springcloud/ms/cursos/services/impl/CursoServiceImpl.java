package org.deivurca.springcloud.ms.cursos.services.impl;

import org.deivurca.springcloud.ms.cursos.clients.feign.UsuarioClientRest;
import org.deivurca.springcloud.ms.cursos.models.Curso;
import org.deivurca.springcloud.ms.cursos.models.CursoUsuario;
import org.deivurca.springcloud.ms.cursos.models.Usuario;
import org.deivurca.springcloud.ms.cursos.repositories.CursoRepository;
import org.deivurca.springcloud.ms.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarTodo() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> listarPorId(Long id) {
        Optional<Curso> cursoOptional = repository.findById(id);

        cursoOptional.ifPresent( curso -> {
            if(!curso.getCursoUsuarios().isEmpty()) {
                List<Long> idsUsuarios = curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).collect(Collectors.toList());
                List<Usuario> usuarios = usuarioClientRest.listarUsuariosPorIds(idsUsuarios);
                curso.setUsuarios(usuarios);
            }
        });

        return cursoOptional;
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorUsuarioId(Long usuarioId) {
        repository.eliminarCursoUsuarioPorUsuarioId(usuarioId);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = repository.findById(cursoId);

        if(cursoOptional.isPresent()) {
            Usuario usuarioMs = usuarioClientRest.detalle(usuario.getId());

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMs.getId());

            curso.addCursoUsaurio(cursoUsuario);

            repository.save(curso);

            return Optional.of(usuarioMs);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = repository.findById(cursoId);

        if(cursoOptional.isPresent()) {
            Usuario usuarioNuevoMs = usuarioClientRest.guardar(usuario);

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMs.getId());

            curso.addCursoUsaurio(cursoUsuario);

            repository.save(curso);

            return Optional.of(usuarioNuevoMs);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = repository.findById(cursoId);

        if(cursoOptional.isPresent()) {
            Usuario usuarioMs = usuarioClientRest.detalle(usuario.getId());

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMs.getId());

            curso.removeCursoUsuario(cursoUsuario);

            repository.save(curso);

            return Optional.of(usuarioMs);
        }

        return Optional.empty();
    }
}
