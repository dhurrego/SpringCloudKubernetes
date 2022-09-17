package org.deivurca.springcloud.ms.cursos.repositories;

import org.deivurca.springcloud.ms.cursos.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Modifying
    @Query("DELETE FROM CursoUsuario cu WHERE cu.usuarioId=?1")
    void eliminarCursoUsuarioPorUsuarioId(Long usuarioId);
}
