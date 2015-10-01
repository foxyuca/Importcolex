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
 * A DireccionamientoTela.
 */
@Entity
@Table(name = "DIRECCIONAMIENTO_TELA")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DireccionamientoTela implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)        
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "direccionamientoTela")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TipoTela> tipoTelas = new HashSet<>();

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

    public Set<TipoTela> getTipoTelas() {
        return tipoTelas;
    }

    public void setTipoTelas(Set<TipoTela> tipoTelas) {
        this.tipoTelas = tipoTelas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DireccionamientoTela direccionamientoTela = (DireccionamientoTela) o;

        if ( ! Objects.equals(id, direccionamientoTela.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DireccionamientoTela{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                '}';
    }
}
