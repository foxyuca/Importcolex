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
 * A TipoTela.
 */
@Entity
@Table(name = "TIPO_TELA")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoTela implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 100)        
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @ManyToOne
    private DireccionamientoTela direccionamientoTela;

    @OneToMany(mappedBy = "tipoTela")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TelaCruda> telaCrudas = new HashSet<>();

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

    public DireccionamientoTela getDireccionamientoTela() {
        return direccionamientoTela;
    }

    public void setDireccionamientoTela(DireccionamientoTela direccionamientoTela) {
        this.direccionamientoTela = direccionamientoTela;
    }

    public Set<TelaCruda> getTelaCrudas() {
        return telaCrudas;
    }

    public void setTelaCrudas(Set<TelaCruda> telaCrudas) {
        this.telaCrudas = telaCrudas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoTela tipoTela = (TipoTela) o;

        if ( ! Objects.equals(id, tipoTela.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoTela{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                '}';
    }
}
