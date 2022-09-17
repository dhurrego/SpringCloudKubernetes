package org.deivurca.springcloud.ms.cursos.controllers;

import org.deivurca.springcloud.ms.cursos.models.Curso;
import org.deivurca.springcloud.ms.cursos.models.Usuario;
import org.deivurca.springcloud.ms.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {
    
    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> detalle(@PathVariable Long id) {

        Optional<Curso> cursoOptional = service.listarPorId(id);

        if(cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cursoOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Curso curso, BindingResult result) {

        if(result.hasErrors()) {
            return validarErrores(result);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Curso curso, BindingResult result) {

        if(result.hasErrors()) {
            return validarErrores(result);
        }

        Optional<Curso> cursoOptional = service.listarPorId(id);

        if(cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoActualizar = cursoOptional.get();
        cursoActualizar.setNombre(curso.getNombre());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(cursoActualizar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Curso> cursoOptional = service.listarPorId(id);

        if(cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuarioCurso(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;

        try {
            usuarioOptional = service.asignarUsuario(usuario, cursoId);
        } catch (Exception exception) {
            return ResponseEntity
                    .internalServerError()
                    .body(Collections
                            .singletonMap("mensaje",
                                    String.format("Se produjo un error mientras se asignada el usuario al curso: %s", exception.getMessage())));
        }

        if(usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuarioCurso(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;

        try {
            usuarioOptional = service.crearUsuario(usuario, cursoId);
        } catch (Exception exception) {
            return ResponseEntity
                    .internalServerError()
                    .body(Collections
                            .singletonMap("mensaje",
                                    String.format("Se produjo un error mientras se creaba el usuario y se asignaba al curso: %s", exception.getMessage())));
        }

        if(usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuarioCurso(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;

        try {
            usuarioOptional = service.eliminarUsuario(usuario, cursoId);
        } catch (Exception exception) {
            return ResponseEntity
                    .internalServerError()
                    .body(Collections
                            .singletonMap("mensaje",
                                    String.format("Se produjo un error mientras desasignaba al curso: %s", exception.getMessage())));
        }

        if(usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/desasignar-usuario/{usuarioId}")
    public ResponseEntity<Void> eliminarCursoUsuarioPorUsuarioId(@PathVariable Long usuarioId) {
        service.eliminarCursoUsuarioPorUsuarioId(usuarioId);
        return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<?> validarErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(), "El campo " + error.getField() + " " +error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
