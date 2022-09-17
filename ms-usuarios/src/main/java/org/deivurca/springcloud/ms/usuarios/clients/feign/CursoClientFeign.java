package org.deivurca.springcloud.ms.usuarios.clients.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cursos", url = "ms-cursos:8002")
public interface CursoClientFeign {

    @DeleteMapping("/desasignar-usuario/{usuarioId}")
    void eliminarCursoUsuarioPorUsuarioId(@PathVariable Long usuarioId);
}
