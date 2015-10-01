package co.com.importcolex.tejeduria.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import co.com.importcolex.tejeduria.domain.util.CustomDateTimeDeserializer;
import co.com.importcolex.tejeduria.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TelaCruda.
 */
@Entity
@Table(name = "TELA_CRUDA")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TelaCruda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull        
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull        
    @Column(name = "ancho", nullable = false)
    private Double ancho;

    @NotNull        
    @Column(name = "costo", nullable = false)
    private Double costo;

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fecha_produccion", nullable = false)
    private DateTime fechaProduccion;

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "hora_inicial", nullable = false)
    private DateTime horaInicial;

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "hora_final", nullable = false)
    private DateTime horaFinal;

    @NotNull
    @Size(max = 45)        
    @Column(name = "turno", length = 45, nullable = false)
    private String turno;

    @NotNull        
    @Column(name = "gramaje", nullable = false)
    private Double gramaje;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "inicio_paro_mecanico", nullable = false)
    private DateTime inicioParoMecanico;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fin_paro_mecanico", nullable = false)
    private DateTime finParoMecanico;
    
    @Column(name = "motivo_paro_mecanico")
    private String motivoParoMecanico;

    @OneToMany(mappedBy = "telaCruda")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FibrasTelaCruda> fibrasTelaCrudas = new HashSet<>();

    @ManyToOne
    private TipoTela tipoTela;

    @ManyToOne
    private Maquina maquina;

    @ManyToOne
    private Operario operario;

    @ManyToOne
    private Clientes clientes;

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

    public Set<FibrasTelaCruda> getFibrasTelaCrudas() {
        return fibrasTelaCrudas;
    }

    public void setFibrasTelaCrudas(Set<FibrasTelaCruda> fibrasTelaCrudas) {
        this.fibrasTelaCrudas = fibrasTelaCrudas;
    }

    public TipoTela getTipoTela() {
        return tipoTela;
    }

    public void setTipoTela(TipoTela tipoTela) {
        this.tipoTela = tipoTela;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Operario getOperario() {
        return operario;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TelaCruda telaCruda = (TelaCruda) o;

        if ( ! Objects.equals(id, telaCruda.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TelaCruda{" +
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
