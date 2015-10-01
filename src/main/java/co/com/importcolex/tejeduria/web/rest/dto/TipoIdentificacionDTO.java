package co.com.importcolex.tejeduria.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TipoIdentificacion entity.
 */
public class TipoIdentificacionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String nombre;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoIdentificacionDTO tipoIdentificacionDTO = (TipoIdentificacionDTO) o;

        if ( ! Objects.equals(id, tipoIdentificacionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoIdentificacionDTO{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                '}';
    }
}
