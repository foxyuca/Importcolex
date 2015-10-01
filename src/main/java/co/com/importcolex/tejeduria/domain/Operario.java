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
 * A Operario.
 */
@Entity
@Table(name = "OPERARIO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 200)        
    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 45)        
    @Column(name = "turno", length = 45, nullable = false)
    private String turno;

    @NotNull
    @Size(max = 50)        
    @Column(name = "referencia", length = 50, nullable = false)
    private String referencia;

    @OneToMany(mappedBy = "operario")
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

        Operario operario = (Operario) o;

        if ( ! Objects.equals(id, operario.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Operario{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", turno='" + turno + "'" +
                ", referencia='" + referencia + "'" +
                '}';
    }
}
