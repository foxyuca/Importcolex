package co.com.importcolex.tejeduria.web.rest.dto;

import org.joda.time.DateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TelaCruda entity.
 */
public class TelaCrudaDTO implements Serializable {

    private Long id;

    @NotNull
    private Double peso;

    @NotNull
    private Double ancho;

    @NotNull
    private Double costo;

    @NotNull
    private DateTime fechaProduccion;

    @NotNull
    private DateTime horaInicial;

    @NotNull
    private DateTime horaFinal;

    @NotNull
    @Size(max = 45)
    private String turno;

    @NotNull
    private Double gramaje;

    private DateTime inicioParoMecanico;

    private DateTime finParoMecanico;

    private String motivoParoMecanico;

    private Long tipoTelaId;

    private String tipoTelaNombre;

    private Long maquinaId;

    private Long operarioId;

    private String operarioNombre;

    private Long clientesId;

    private String clientesNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public DateTime getFechaProduccion() {
        return fechaProduccion;
    }

    public void setFechaProduccion(DateTime fechaProduccion) {
        this.fechaProduccion = fechaProduccion;
    }

    public DateTime getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(DateTime horaInicial) {
        this.horaInicial = horaInicial;
    }

    public DateTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(DateTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Double getGramaje() {
        return gramaje;
    }

    public void setGramaje(Double gramaje) {
        this.gramaje = gramaje;
    }

    public DateTime getInicioParoMecanico() {
        return inicioParoMecanico;
    }

    public void setInicioParoMecanico(DateTime inicioParoMecanico) {
        this.inicioParoMecanico = inicioParoMecanico;
    }

    public DateTime getFinParoMecanico() {
        return finParoMecanico;
    }

    public void setFinParoMecanico(DateTime finParoMecanico) {
        this.finParoMecanico = finParoMecanico;
    }

    public String getMotivoParoMecanico() {
        return motivoParoMecanico;
    }

    public void setMotivoParoMecanico(String motivoParoMecanico) {
        this.motivoParoMecanico = motivoParoMecanico;
    }

    public Long getTipoTelaId() {
        return tipoTelaId;
    }

    public void setTipoTelaId(Long tipoTelaId) {
        this.tipoTelaId = tipoTelaId;
    }

    public String getTipoTelaNombre() {
        return tipoTelaNombre;
    }

    public void setTipoTelaNombre(String tipoTelaNombre) {
        this.tipoTelaNombre = tipoTelaNombre;
    }

    public Long getMaquinaId() {
        return maquinaId;
    }

    public void setMaquinaId(Long maquinaId) {
        this.maquinaId = maquinaId;
    }

    public Long getOperarioId() {
        return operarioId;
    }

    public void setOperarioId(Long operarioId) {
        this.operarioId = operarioId;
    }

    public String getOperarioNombre() {
        return operarioNombre;
    }

    public void setOperarioNombre(String operarioNombre) {
        this.operarioNombre = operarioNombre;
    }

    public Long getClientesId() {
        return clientesId;
    }

    public void setClientesId(Long clientesId) {
        this.clientesId = clientesId;
    }

    public String getClientesNombre() {
        return clientesNombre;
    }

    public void setClientesNombre(String clientesNombre) {
        this.clientesNombre = clientesNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TelaCrudaDTO telaCrudaDTO = (TelaCrudaDTO) o;

        if ( ! Objects.equals(id, telaCrudaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TelaCrudaDTO{" +
                "id=" + id +
                ", peso='" + peso + "'" +
                ", ancho='" + ancho + "'" +
                ", costo='" + costo + "'" +
                ", fechaProduccion='" + fechaProduccion + "'" +
                ", horaInicial='" + horaInicial + "'" +
                ", horaFinal='" + horaFinal + "'" +
                ", turno='" + turno + "'" +
                ", gramaje='" + gramaje + "'" +
                ", inicioParoMecanico='" + inicioParoMecanico + "'" +
                ", finParoMecanico='" + finParoMecanico + "'" +
                ", motivoParoMecanico='" + motivoParoMecanico + "'" +
                '}';
    }
}
