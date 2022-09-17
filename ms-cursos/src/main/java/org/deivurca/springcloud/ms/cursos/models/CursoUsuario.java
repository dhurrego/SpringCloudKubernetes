package org.deivurca.springcloud.ms.cursos.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cursos_usuarios")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof CursoUsuario)) {
            return false;
        }

        CursoUsuario cursoUsuario = (CursoUsuario) obj;

        return this.usuarioId != null && this.usuarioId.equals(cursoUsuario.usuarioId);
    }
}
