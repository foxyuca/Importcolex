package co.com.importcolex.tejeduria.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;


/**
 * A DTO for the FibrasTelaCruda entity.
 */
public class FibrasTelaCrudaDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8985843524937796596L;

	private Long id;

    @NotNull
    private Integer referencia;

    @NotNull
    private Integer peso;
    
    @NotNull
    private Integer cantidadUsada;
    
    private Long inventarioFibrasId;

    private String inventarioFibrasLote;

    private Long telaCrudaId;

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

    public Long getInventarioFibrasId() {
        return inventarioFibrasId;
    }

    public void setInventarioFibrasId(Long inventarioFibrasId) {
        this.inventarioFibrasId = inventarioFibrasId;
    }

    public String getInventarioFibrasLote() {
        return inventarioFibrasLote;
    }

    public void setInventarioFibrasLote(String inventarioFibrasLote) {
        this.inventarioFibrasLote = inventarioFibrasLote;
    }

    public Long getTelaCrudaId() {
        return telaCrudaId;
    }

    public void setTelaCrudaId(Long telaCrudaId) {
        this.telaCrudaId = telaCrudaId;
    }


    public Integer getCantidadUsada() {
		return cantidadUsada;
	}

	public void setCantidadUsada(Integer cantidadUsada) {
		this.cantidadUsada = cantidadUsada;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FibrasTelaCrudaDTO fibrasTelaCrudaDTO = (FibrasTelaCrudaDTO) o;

        if ( ! Objects.equals(id, fibrasTelaCrudaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "FibrasTelaCrudaDTO [id=" + id + ", referencia=" + referencia
				+ ", peso=" + peso + ", cantidadUsada=" + cantidadUsada
				+ ", inventarioFibrasId=" + inventarioFibrasId
				+ ", inventarioFibrasLote=" + inventarioFibrasLote
				+ ", telaCrudaId=" + telaCrudaId + "]";
	}


}
