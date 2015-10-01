package co.com.importcolex.tejeduria.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Operario entity.
 */
public class OperarioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String nombre;

    @NotNull
    @Size(max = 45)
    private String turno;

    @NotNull
    @Size(max = 50)
    private String referencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperarioDTO operarioDTO = (OperarioDTO) o;

        if ( ! Objects.equals(id, operarioDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OperarioDTO{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", turno='" + turno + "'" +
                ", referencia='" + referencia + "'" +
                '}';
    }
}
