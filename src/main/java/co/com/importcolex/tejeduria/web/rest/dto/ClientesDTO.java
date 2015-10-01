package co.com.importcolex.tejeduria.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Clientes entity.
 */
public class ClientesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nombre;

    @NotNull
    @Size(max = 20)
    private String identificacion;

    @NotNull
    @Size(max = 45)
    @Pattern(regexp = "(^[0-9]+$)")
    private String telefono;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "^[-!#$%&amp;&#39;*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&amp;&#39;*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$")
    private String correo;

    @Size(max = 450)
    private String direccion;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientesDTO clientesDTO = (ClientesDTO) o;

        if ( ! Objects.equals(id, clientesDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientesDTO{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", identificacion='" + identificacion + "'" +
                ", telefono='" + telefono + "'" +
                ", correo='" + correo + "'" +
                ", direccion='" + direccion + "'" +
                '}';
    }
}
