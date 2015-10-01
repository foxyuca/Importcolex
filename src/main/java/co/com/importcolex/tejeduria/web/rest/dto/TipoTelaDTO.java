package co.com.importcolex.tejeduria.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TipoTela entity.
 */
public class TipoTelaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nombre;

    private Long direccionamientoTelaId;

    private String direccionamientoTelaNombre;

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

    public Long getDireccionamientoTelaId() {
        return direccionamientoTelaId;
    }

    public void setDireccionamientoTelaId(Long direccionamientoTelaId) {
        this.direccionamientoTelaId = direccionamientoTelaId;
    }

    public String getDireccionamientoTelaNombre() {
        return direccionamientoTelaNombre;
    }

    public void setDireccionamientoTelaNombre(String direccionamientoTelaNombre) {
        this.direccionamientoTelaNombre = direccionamientoTelaNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoTelaDTO tipoTelaDTO = (TipoTelaDTO) o;

        if ( ! Objects.equals(id, tipoTelaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoTelaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                '}';
    }
}
