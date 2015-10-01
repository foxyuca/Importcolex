package co.com.importcolex.tejeduria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FibrasTelaCruda.
 */
@Entity
@Table(name = "FIBRAS_TELA_CRUDA")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FibrasTelaCruda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull        
    @Column(name = "referencia", nullable = false)
    private Integer referencia;

    @NotNull        
    @Column(name = "peso", nullable = false)
    private Integer peso;

    @ManyToOne
    private InventarioFibras inventarioFibras;

    @ManyToOne
    private TelaCruda telaCruda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public InventarioFibras getInventarioFibras() {
        return inventarioFibras;
    }

    public void setInventarioFibras(InventarioFibras inventarioFibras) {
        this.inventarioFibras = inventarioFibras;
    }

    public TelaCruda getTelaCruda() {
        return telaCruda;
    }

    public void setTelaCruda(TelaCruda telaCruda) {
        this.telaCruda = telaCruda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FibrasTelaCruda fibrasTelaCruda = (FibrasTelaCruda) o;

        if ( ! Objects.equals(id, fibrasTelaCruda.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FibrasTelaCruda{" +
                "id=" + id +
                ", referencia='" + referencia + "'" +
                ", peso='" + peso + "'" +
                '}';
    }
}
