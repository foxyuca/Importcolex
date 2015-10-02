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
 * A Proveedores.
 */
@Entity
@Table(name = "PROVEEDORES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proveedores implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 150)        
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @NotNull        
    @Column(name = "identificacion", nullable = false)
    private String identificacion;

    @NotNull
    @Size(max = 450)        
    @Column(name = "direccion", length = 450, nullable = false)
    private String direccion;

    @NotNull
    @Size(max = 45)
    @Pattern(regexp = "(^[0-9]+$)")        
    @Column(name = "telefono", length = 45, nullable = false)
    private String telefono;

    @NotNull
    @Size(max = 200)
    @Pattern(regexp = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$")        
    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @OneToMany(mappedBy = "proveedores")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrdenCompra> ordencompras = new HashSet<>();
    
    @ManyToOne
    private TipoIdentificacion tipoIdentificacion;


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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<OrdenCompra> getOrdencompras() {
        return ordencompras;
    }

    public void setOrdencompras(Set<OrdenCompra> ordenCompras) {
        this.ordencompras = ordenCompras;
    }
    
    

    public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Proveedores proveedores = (Proveedores) o;

        if ( ! Objects.equals(id, proveedores.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Proveedores{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", identificacion='" + identificacion + "'" +
                ", direccion='" + direccion + "'" +
                ", telefono='" + telefono + "'" +
                ", email='" + email + "'" +
                '}';
    }
}
