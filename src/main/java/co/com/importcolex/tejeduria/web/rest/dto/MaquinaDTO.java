package co.com.importcolex.tejeduria.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Maquina entity.
 */
public class MaquinaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 45)
    private String marca;

    @NotNull
    private Integer galga;

    private Integer diametro;

    private Integer rpm;

    private Integer agujas;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaquinaDTO maquinaDTO = (MaquinaDTO) o;

        if ( ! Objects.equals(id, maquinaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MaquinaDTO{" +
                "id=" + id +
                ", marca='" + marca + "'" +
                ", galga='" + galga + "'" +
                ", diametro='" + diametro + "'" +
                ", rpm='" + rpm + "'" +
                ", agujas='" + agujas + "'" +
                '}';
    }
}
