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
 * A Fibras.
 */
@Entity
@Table(name = "FIBRAS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fibras implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 100)        
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 100)        
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @NotNull
    @Size(max = 10)        
    @Column(name = "filamentos", length = 10, nullable = false)
    private String filamentos;

    @OneToMany(mappedBy = "fibras")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrdenCompra> ordenCompras = new HashSet<>();

    @OneToMany(mappedBy = "fibras")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InventarioFibras> inventarioFibrass = new HashSet<>();

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

    public Set<OrdenCompra> getOrdenCompras() {
        return ordenCompras;
    }

    public void setOrdenCompras(Set<OrdenCompra> ordenCompras) {
        this.ordenCompras = ordenCompras;
    }

    public Set<InventarioFibras> getInventarioFibrass() {
        return inventarioFibrass;
    }

    public void setInventarioFibrass(Set<InventarioFibras> inventarioFibrass) {
        this.inventarioFibrass = inventarioFibrass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Fibras fibras = (Fibras) o;

        if ( ! Objects.equals(id, fibras.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fibras{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", titulo='" + titulo + "'" +
                ", filamentos='" + filamentos + "'" +
                '}';
    }
}
