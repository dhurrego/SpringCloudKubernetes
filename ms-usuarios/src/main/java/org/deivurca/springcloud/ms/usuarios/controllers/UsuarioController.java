package org.deivurca.springcloud.ms.usuarios.controllers;

import org.deivurca.springcloud.ms.usuarios.models.entity.Usuario;
import org.deivurca.springcloud.ms.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/crash")
    public void crash() {
        ((ConfigurableApplicationContext) applicationContext).close();
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Usuario>>> listar() {
        return ResponseEntity.ok(
                Collections.singletonMap("usuarios", service.listarTodo())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> detalle(@PathVariable Long id) {

        Optional<Usuario> usuarioOptional = service.listarPorId(id);

        if(usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuarioOptional.get());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Usuario usuario, BindingResult result) {

        if(result.hasErrors()) {
            return validarErrores(result);
        }

        if(service.listarUsuarioPorEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections
                            .singletonMap("error", "Ya existe un usuario con ese correo electrónico"));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result) {

        if(result.hasErrors()) {
            return validarErrores(result);
        }

        Optional<Usuario> usuarioOptional = service.listarPorId(id);

        if(usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioActualizar = usuarioOptional.get();

        if(!usuario.getEmail().equalsIgnoreCase(usuarioActualizar.getEmail()) &&
                service.listarUsuarioPorEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections
                            .singletonMap("mensaje", "Ya existe un usuario con ese correo electrónico"));
        }

        usuarioActualizar.setNombre(usuario.getNombre());
        usuarioActualizar.setEmail(usuario.getEmail());
        usuarioActualizar.setPassword(usuario.getPassword());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(usuarioActualizar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.listarPorId(id);

        if(usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios-por-ids")
    public ResponseEntity<List<Usuario>> listarUsuariosPorIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.listarUsuariosPorIds(ids));
    }

    private static ResponseEntity<?> validarErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(), "El campo " + error.getField() + " " +error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
