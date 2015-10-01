package co.com.importcolex.tejeduria.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the OrdenCompra entity.
 */
public class OrdenCompraDTO implements Serializable {

    private Long id;

    private BigDecimal ticket;

    private Integer cantidad;

    @NotNull
    private Double costo;

    private Boolean aprovada;

    private Boolean ordenada;

    private Boolean recibida;

    private Long proveedoresId;

    private String proveedoresNombre;

    private Long fibrasId;

    private String fibrasNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTicket() {
        return ticket;
    }

    public void setTicket(BigDecimal ticket) {
        this.ticket = ticket;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Boolean getAprovada() {
        return aprovada;
    }

    public void setAprovada(Boolean aprovada) {
        this.aprovada = aprovada;
    }

    public Boolean getOrdenada() {
        return ordenada;
    }

    public void setOrdenada(Boolean ordenada) {
        this.ordenada = ordenada;
    }

    public Boolean getRecibida() {
        return recibida;
    }

    public void setRecibida(Boolean recibida) {
        this.recibida = recibida;
    }

    public Long getProveedoresId() {
        return proveedoresId;
    }

    public void setProveedoresId(Long proveedoresId) {
        this.proveedoresId = proveedoresId;
    }

    public String getProveedoresNombre() {
        return proveedoresNombre;
    }

    public void setProveedoresNombre(String proveedoresNombre) {
        this.proveedoresNombre = proveedoresNombre;
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

        OrdenCompraDTO ordenCompraDTO = (OrdenCompraDTO) o;

        if ( ! Objects.equals(id, ordenCompraDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrdenCompraDTO{" +
                "id=" + id +
                ", ticket='" + ticket + "'" +
                ", cantidad='" + cantidad + "'" +
                ", costo='" + costo + "'" +
                ", aprovada='" + aprovada + "'" +
                ", ordenada='" + ordenada + "'" +
                ", recibida='" + recibida + "'" +
                '}';
    }
}
