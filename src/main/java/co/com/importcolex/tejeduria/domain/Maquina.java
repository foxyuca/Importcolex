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
 * A Maquina.
 */
@Entity
@Table(name = "MAQUINA")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Maquina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 45)        
    @Column(name = "marca", length = 45, nullable = false)
    private String marca;

    @NotNull        
    @Column(name = "galga", nullable = false)
    private Integer galga;
    
    @Column(name = "diametro")
    private Integer diametro;
    
    @Column(name = "rpm")
    private Integer rpm;
    
    @Column(name = "agujas")
    private Integer agujas;

    @OneToMany(mappedBy = "maquina")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TelaCruda> telaCrudas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getGalga() {
        return galga;
    }

    public void setGalga(Integer galga) {
        this.galga = galga;
    }

    public Integer getDiametro() {
        return diametro;
    }

    public void setDiametro(Integer diametro) {
        this.diametro = diametro;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public Integer getAgujas() {
        return agujas;
    }

    public void setAgujas(Integer agujas) {
        this.agujas = agujas;
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

        Maquina maquina = (Maquina) o;

        if ( ! Objects.equals(id, maquina.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Maquina{" +
                "id=" + id +
                ", marca='" + marca + "'" +
                ", galga='" + galga + "'" +
                ", diametro='" + diametro + "'" +
                ", rpm='" + rpm + "'" +
                ", agujas='" + agujas + "'" +
                '}';
    }
}
