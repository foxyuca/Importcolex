package co.com.importcolex.tejeduria.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the InventarioFibras entity.
 */
public class InventarioFibrasDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String lote;

    @NotNull
    private Integer inventarioInicial;

    @NotNull
    private Integer inventarioFinal;

    @NotNull
    private Double costo;

    private Long fibrasId;

    private String fibrasNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Integer getInventarioInicial() {
        return inventarioInicial;
    }

    public void setInventarioInicial(Integer inventarioInicial) {
        this.inventarioInicial = inventarioInicial;
    }

    public Integer getInventarioFinal() {
        return inventarioFinal;
    }

    public void setInventarioFinal(Integer inventarioFinal) {
        this.inventarioFinal = inventarioFinal;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Long getFibrasId() {
        return fibrasId;
    }

    public void setFibrasId(Long fibrasId) {
        this.fibrasId = fibrasId;
    }

    public String getFibrasNombre() {
        return fibrasNombre;
    }

    public void setFibrasNombre(String fibrasNombre) {
        this.fibrasNombre = fibrasNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventarioFibrasDTO inventarioFibrasDTO = (InventarioFibrasDTO) o;

        if ( ! Objects.equals(id, inventarioFibrasDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InventarioFibrasDTO{" +
                "id=" + id +
                ", lote='" + lote + "'" +
                ", inventarioInicial='" + inventarioInicial + "'" +
                ", inventarioFinal='" + inventarioFinal + "'" +
                ", costo='" + costo + "'" +
                '}';
    }
}
