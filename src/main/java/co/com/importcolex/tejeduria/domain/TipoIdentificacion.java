package co.com.importcolex.tejeduria.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TipoIdentificacion.
 */
@Entity
@Table(name = "TIPO_IDENTIFICACION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoIdentificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 10)        
    @Column(name = "nombre", length = 10, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tipoIdentificacion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proveedores> proveedoress = new HashSet<>();

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

    public Set<Proveedores> getProveedoress() {
        return proveedoress;
    }

    public void setProveedoress(Set<Proveedores> proveedoress) {
        this.proveedoress = proveedoress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoIdentificacion tipoIdentificacion = (TipoIdentificacion) o;

        if ( ! Objects.equals(id, tipoIdentificacion.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoIdentificacion{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                '}';
    }
}
