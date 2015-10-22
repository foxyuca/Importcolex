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
 * A InventarioFibras.
 */
@Entity
@Table(name = "INVENTARIO_FIBRAS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InventarioFibras implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3931569821659655200L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50)        
    @Column(name = "lote", length = 50, nullable = false)
    private String lote;

    @NotNull        
    @Column(name = "inventario_inicial", nullable = false)
    private Integer inventarioInicial;

    @NotNull        
    @Column(name = "inventario_final", nullable = false)
    private Integer inventarioFinal;

    @NotNull        
    @Column(name = "costo", nullable = false)
    private Double costo;

    @ManyToOne
    private Fibras fibras;
    
    @ManyToOne
    private OrdenCompra ordenCompra;

    @OneToMany(mappedBy = "inventarioFibras")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FibrasTelaCruda> fibrasTelaCrudas = new HashSet<>();

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

    public Fibras getFibras() {
        return fibras;
    }

    public void setFibras(Fibras fibras) {
        this.fibras = fibras;
    }

    public Set<FibrasTelaCruda> getFibrasTelaCrudas() {
        return fibrasTelaCrudas;
    }

    public void setFibrasTelaCrudas(Set<FibrasTelaCruda> fibrasTelaCrudas) {
        this.fibrasTelaCrudas = fibrasTelaCrudas;
    }
    
    

    public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventarioFibras inventarioFibras = (InventarioFibras) o;

        if ( ! Objects.equals(id, inventarioFibras.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InventarioFibras{" +
                "id=" + id +
                ", lote='" + lote + "'" +
                ", inventarioInicial='" + inventarioInicial + "'" +
                ", inventarioFinal='" + inventarioFinal + "'" +
                ", costo='" + costo + "'" +
                '}';
    }
}
