package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.TelaCruda;
import co.com.importcolex.tejeduria.repository.TelaCrudaRepository;
import co.com.importcolex.tejeduria.web.rest.dto.TelaCrudaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.TelaCrudaMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TelaCrudaResource REST controller.
 *
 * @see TelaCrudaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TelaCrudaResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final Double DEFAULT_ANCHO = 1D;
    private static final Double UPDATED_ANCHO = 2D;

    private static final Double DEFAULT_COSTO = 1D;
    private static final Double UPDATED_COSTO = 2D;

    private static final DateTime DEFAULT_FECHA_PRODUCCION = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_FECHA_PRODUCCION = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_FECHA_PRODUCCION_STR = dateTimeFormatter.print(DEFAULT_FECHA_PRODUCCION);

    private static final DateTime DEFAULT_HORA_INICIAL = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_HORA_INICIAL = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_HORA_INICIAL_STR = dateTimeFormatter.print(DEFAULT_HORA_INICIAL);

    private static final DateTime DEFAULT_HORA_FINAL = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_HORA_FINAL = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_HORA_FINAL_STR = dateTimeFormatter.print(DEFAULT_HORA_FINAL);
    private static final String DEFAULT_TURNO = "SAMPLE_TEXT";
    private static final String UPDATED_TURNO = "UPDATED_TEXT";

    private static final Double DEFAULT_GRAMAJE = 1D;
    private static final Double UPDATED_GRAMAJE = 2D;

    private static final DateTime DEFAULT_INICIO_PARO_MECANICO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_INICIO_PARO_MECANICO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_INICIO_PARO_MECANICO_STR = dateTimeFormatter.print(DEFAULT_INICIO_PARO_MECANICO);

    private static final DateTime DEFAULT_FIN_PARO_MECANICO = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_FIN_PARO_MECANICO = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_FIN_PARO_MECANICO_STR = dateTimeFormatter.print(DEFAULT_FIN_PARO_MECANICO);
    private static final String DEFAULT_MOTIVO_PARO_MECANICO = "SAMPLE_TEXT";
    private static final String UPDATED_MOTIVO_PARO_MECANICO = "UPDATED_TEXT";

    @Inject
    private TelaCrudaRepository telaCrudaRepository;

    @Inject
    private TelaCrudaMapper telaCrudaMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTelaCrudaMockMvc;

    private TelaCruda telaCruda;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TelaCrudaResource telaCrudaResource = new TelaCrudaResource();
        ReflectionTestUtils.setField(telaCrudaResource, "telaCrudaRepository", telaCrudaRepository);
        ReflectionTestUtils.setField(telaCrudaResource, "telaCrudaMapper", telaCrudaMapper);
        this.restTelaCrudaMockMvc = MockMvcBuilders.standaloneSetup(telaCrudaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        telaCruda = new TelaCruda();
        telaCruda.setPeso(DEFAULT_PESO);
        telaCruda.setAncho(DEFAULT_ANCHO);
        telaCruda.setCosto(DEFAULT_COSTO);
        telaCruda.setFechaProduccion(DEFAULT_FECHA_PRODUCCION);
        telaCruda.setHoraInicial(DEFAULT_HORA_INICIAL);
        telaCruda.setHoraFinal(DEFAULT_HORA_FINAL);
        telaCruda.setTurno(DEFAULT_TURNO);
        telaCruda.setGramaje(DEFAULT_GRAMAJE);
        telaCruda.setInicioParoMecanico(DEFAULT_INICIO_PARO_MECANICO);
        telaCruda.setFinParoMecanico(DEFAULT_FIN_PARO_MECANICO);
        telaCruda.setMotivoParoMecanico(DEFAULT_MOTIVO_PARO_MECANICO);
    }

    @Test
    @Transactional
    public void createTelaCruda() throws Exception {
        int databaseSizeBeforeCreate = telaCrudaRepository.findAll().size();

        // Create the TelaCruda
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isCreated());

        // Validate the TelaCruda in the database
        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeCreate + 1);
        TelaCruda testTelaCruda = telaCrudas.get(telaCrudas.size() - 1);
        assertThat(testTelaCruda.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testTelaCruda.getAncho()).isEqualTo(DEFAULT_ANCHO);
        assertThat(testTelaCruda.getCosto()).isEqualTo(DEFAULT_COSTO);
        assertThat(testTelaCruda.getFechaProduccion().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_FECHA_PRODUCCION);
        assertThat(testTelaCruda.getHoraInicial().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HORA_INICIAL);
        assertThat(testTelaCruda.getHoraFinal().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_HORA_FINAL);
        assertThat(testTelaCruda.getTurno()).isEqualTo(DEFAULT_TURNO);
        assertThat(testTelaCruda.getGramaje()).isEqualTo(DEFAULT_GRAMAJE);
        assertThat(testTelaCruda.getInicioParoMecanico().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_INICIO_PARO_MECANICO);
        assertThat(testTelaCruda.getFinParoMecanico().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_FIN_PARO_MECANICO);
        assertThat(testTelaCruda.getMotivoParoMecanico()).isEqualTo(DEFAULT_MOTIVO_PARO_MECANICO);
    }

    @Test
    @Transactional
    public void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = telaCrudaRepository.findAll().size();
        // set the field null
        telaCruda.setPeso(null);

        // Create the TelaCruda, which fails.
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnchoIsRequired() throws Exception {
        int databaseSizeBeforeTest = telaCrudaRepository.findAll().size();
        // set the field null
        telaCruda.setAncho(null);

        // Create the TelaCruda, which fails.
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = telaCrudaRepository.findAll().size();
        // set the field null
        telaCruda.setCosto(null);

        // Create the TelaCruda, which fails.
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaProduccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = telaCrudaRepository.findAll().size();
        // set the field null
        telaCruda.setFechaProduccion(null);

        // Create the TelaCruda, which fails.
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraInicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = telaCrudaRepository.findAll().size();
        // set the field null
        telaCruda.setHoraInicial(null);

        // Create the TelaCruda, which fails.
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = telaCrudaRepository.findAll().size();
        // set the field null
        telaCruda.setHoraFinal(null);

        // Create the TelaCruda, which fails.
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTurnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = telaCrudaRepository.findAll().size();
        // set the field null
        telaCruda.setTurno(null);

        // Create the TelaCruda, which fails.
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGramajeIsRequired() throws Exception {
        int databaseSizeBeforeTest = telaCrudaRepository.findAll().size();
        // set the field null
        telaCruda.setGramaje(null);

        // Create the TelaCruda, which fails.
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(post("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTelaCrudas() throws Exception {
        // Initialize the database
        telaCrudaRepository.saveAndFlush(telaCruda);

        // Get all the telaCrudas
        restTelaCrudaMockMvc.perform(get("/api/telaCrudas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(telaCruda.getId().intValue())))
                .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
                .andExpect(jsonPath("$.[*].ancho").value(hasItem(DEFAULT_ANCHO.doubleValue())))
                .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())))
                .andExpect(jsonPath("$.[*].fechaProduccion").value(hasItem(DEFAULT_FECHA_PRODUCCION_STR)))
                .andExpect(jsonPath("$.[*].horaInicial").value(hasItem(DEFAULT_HORA_INICIAL_STR)))
                .andExpect(jsonPath("$.[*].horaFinal").value(hasItem(DEFAULT_HORA_FINAL_STR)))
                .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO.toString())))
                .andExpect(jsonPath("$.[*].gramaje").value(hasItem(DEFAULT_GRAMAJE.doubleValue())))
                .andExpect(jsonPath("$.[*].inicioParoMecanico").value(hasItem(DEFAULT_INICIO_PARO_MECANICO_STR)))
                .andExpect(jsonPath("$.[*].finParoMecanico").value(hasItem(DEFAULT_FIN_PARO_MECANICO_STR)))
                .andExpect(jsonPath("$.[*].motivoParoMecanico").value(hasItem(DEFAULT_MOTIVO_PARO_MECANICO.toString())));
    }

    @Test
    @Transactional
    public void getTelaCruda() throws Exception {
        // Initialize the database
        telaCrudaRepository.saveAndFlush(telaCruda);

        // Get the telaCruda
        restTelaCrudaMockMvc.perform(get("/api/telaCrudas/{id}", telaCruda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(telaCruda.getId().intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.ancho").value(DEFAULT_ANCHO.doubleValue()))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.doubleValue()))
            .andExpect(jsonPath("$.fechaProduccion").value(DEFAULT_FECHA_PRODUCCION_STR))
            .andExpect(jsonPath("$.horaInicial").value(DEFAULT_HORA_INICIAL_STR))
            .andExpect(jsonPath("$.horaFinal").value(DEFAULT_HORA_FINAL_STR))
            .andExpect(jsonPath("$.turno").value(DEFAULT_TURNO.toString()))
            .andExpect(jsonPath("$.gramaje").value(DEFAULT_GRAMAJE.doubleValue()))
            .andExpect(jsonPath("$.inicioParoMecanico").value(DEFAULT_INICIO_PARO_MECANICO_STR))
            .andExpect(jsonPath("$.finParoMecanico").value(DEFAULT_FIN_PARO_MECANICO_STR))
            .andExpect(jsonPath("$.motivoParoMecanico").value(DEFAULT_MOTIVO_PARO_MECANICO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTelaCruda() throws Exception {
        // Get the telaCruda
        restTelaCrudaMockMvc.perform(get("/api/telaCrudas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelaCruda() throws Exception {
        // Initialize the database
        telaCrudaRepository.saveAndFlush(telaCruda);

		int databaseSizeBeforeUpdate = telaCrudaRepository.findAll().size();

        // Update the telaCruda
        telaCruda.setPeso(UPDATED_PESO);
        telaCruda.setAncho(UPDATED_ANCHO);
        telaCruda.setCosto(UPDATED_COSTO);
        telaCruda.setFechaProduccion(UPDATED_FECHA_PRODUCCION);
        telaCruda.setHoraInicial(UPDATED_HORA_INICIAL);
        telaCruda.setHoraFinal(UPDATED_HORA_FINAL);
        telaCruda.setTurno(UPDATED_TURNO);
        telaCruda.setGramaje(UPDATED_GRAMAJE);
        telaCruda.setInicioParoMecanico(UPDATED_INICIO_PARO_MECANICO);
        telaCruda.setFinParoMecanico(UPDATED_FIN_PARO_MECANICO);
        telaCruda.setMotivoParoMecanico(UPDATED_MOTIVO_PARO_MECANICO);
        
        TelaCrudaDTO telaCrudaDTO = telaCrudaMapper.telaCrudaToTelaCrudaDTO(telaCruda);

        restTelaCrudaMockMvc.perform(put("/api/telaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(telaCrudaDTO)))
                .andExpect(status().isOk());

        // Validate the TelaCruda in the database
        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeUpdate);
        TelaCruda testTelaCruda = telaCrudas.get(telaCrudas.size() - 1);
        assertThat(testTelaCruda.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testTelaCruda.getAncho()).isEqualTo(UPDATED_ANCHO);
        assertThat(testTelaCruda.getCosto()).isEqualTo(UPDATED_COSTO);
        assertThat(testTelaCruda.getFechaProduccion().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_FECHA_PRODUCCION);
        assertThat(testTelaCruda.getHoraInicial().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HORA_INICIAL);
        assertThat(testTelaCruda.getHoraFinal().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_HORA_FINAL);
        assertThat(testTelaCruda.getTurno()).isEqualTo(UPDATED_TURNO);
        assertThat(testTelaCruda.getGramaje()).isEqualTo(UPDATED_GRAMAJE);
        assertThat(testTelaCruda.getInicioParoMecanico().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_INICIO_PARO_MECANICO);
        assertThat(testTelaCruda.getFinParoMecanico().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_FIN_PARO_MECANICO);
        assertThat(testTelaCruda.getMotivoParoMecanico()).isEqualTo(UPDATED_MOTIVO_PARO_MECANICO);
    }

    @Test
    @Transactional
    public void deleteTelaCruda() throws Exception {
        // Initialize the database
        telaCrudaRepository.saveAndFlush(telaCruda);

		int databaseSizeBeforeDelete = telaCrudaRepository.findAll().size();

        // Get the telaCruda
        restTelaCrudaMockMvc.perform(delete("/api/telaCrudas/{id}", telaCruda.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TelaCruda> telaCrudas = telaCrudaRepository.findAll();
        assertThat(telaCrudas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
