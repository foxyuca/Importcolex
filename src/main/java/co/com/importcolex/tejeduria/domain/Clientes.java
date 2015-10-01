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
 * A Clientes.
 */
@Entity
@Table(name = "CLIENTES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Clientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 150)        
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 20)        
    @Column(name = "identificacion", length = 20, nullable = false)
    private String identificacion;

    @NotNull
    @Size(max = 45)
    @Pattern(regexp = "(^[0-9]+$)")        
    @Column(name = "telefono", length = 45, nullable = false)
    private String telefono;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$")        
    @Column(name = "correo", length = 100, nullable = false)
    private String correo;

    @Size(max = 450)        
    @Column(name = "direccion", length = 450)
    private String direccion;

    @OneToMany(mappedBy = "clientes")
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

        Clientes clientes = (Clientes) o;

        if ( ! Objects.equals(id, clientes.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Clientes{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", identificacion='" + identificacion + "'" +
                ", telefono='" + telefono + "'" +
                ", correo='" + correo + "'" +
                ", direccion='" + direccion + "'" +
                '}';
    }
}
