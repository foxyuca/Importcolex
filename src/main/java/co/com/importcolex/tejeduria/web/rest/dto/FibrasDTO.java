package co.com.importcolex.tejeduria.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Fibras entity.
 */
public class FibrasDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 100)
    private String nombre;

    @NotNull
    @Size(max = 100)
    private String titulo;

    @NotNull
    @Size(max = 10)
    private String filamentos;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFilamentos() {
        return filamentos;
    }

    public void setFilamentos(String filamentos) {
        this.filamentos = filamentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FibrasDTO fibrasDTO = (FibrasDTO) o;

        if ( ! Objects.equals(id, fibrasDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FibrasDTO{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", titulo='" + titulo + "'" +
                ", filamentos='" + filamentos + "'" +
                '}';
    }
}
