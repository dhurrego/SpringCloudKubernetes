package org.deivurca.springcloud.ms.cursos.clients.feign;

import org.deivurca.springcloud.ms.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-usuarios")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping
    Usuario guardar(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-ids")
    List<Usuario> listarUsuariosPorIds(@RequestParam List<Long> ids);
}
