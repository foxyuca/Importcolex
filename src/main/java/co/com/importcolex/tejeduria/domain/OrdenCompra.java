package co.com.importcolex.tejeduria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrdenCompra.
 */
@Entity
@Table(name = "ORDEN_COMPRA")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrdenCompra implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1544466302624824323L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "ticket", precision=10, scale=2)
    private BigDecimal ticket;
    
    @Column(name = "cantidad")
    private Integer cantidad;

    @NotNull        
    @Column(name = "costo", nullable = false)
    private Double costo;
    
    @Column(name = "aprovada")
    private Boolean aprovada;
    
    @Column(name = "ordenada")
    private Boolean ordenada;
    
    @Column(name = "recibida")
    private Boolean recibida;

    @ManyToOne
    private Proveedores proveedores;

    @ManyToOne
    private Fibras fibras;
    
    @OneToMany(mappedBy = "ordenCompra")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InventarioFibras> inventarioFibrass = new HashSet<>();

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

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public Fibras getFibras() {
        return fibras;
    }

    public void setFibras(Fibras fibras) {
        this.fibras = fibras;
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

        OrdenCompra ordenCompra = (OrdenCompra) o;

        if ( ! Objects.equals(id, ordenCompra.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrdenCompra{" +
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
