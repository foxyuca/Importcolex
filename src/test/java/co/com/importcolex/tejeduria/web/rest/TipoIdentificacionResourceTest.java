package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.TipoIdentificacion;
import co.com.importcolex.tejeduria.repository.TipoIdentificacionRepository;
import co.com.importcolex.tejeduria.web.rest.dto.TipoIdentificacionDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.TipoIdentificacionMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TipoIdentificacionResource REST controller.
 *
 * @see TipoIdentificacionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TipoIdentificacionResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    @Inject
    private TipoIdentificacionRepository tipoIdentificacionRepository;

    @Inject
    private TipoIdentificacionMapper tipoIdentificacionMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTipoIdentificacionMockMvc;

    private TipoIdentificacion tipoIdentificacion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoIdentificacionResource tipoIdentificacionResource = new TipoIdentificacionResource();
        ReflectionTestUtils.setField(tipoIdentificacionResource, "tipoIdentificacionRepository", tipoIdentificacionRepository);
        ReflectionTestUtils.setField(tipoIdentificacionResource, "tipoIdentificacionMapper", tipoIdentificacionMapper);
        this.restTipoIdentificacionMockMvc = MockMvcBuilders.standaloneSetup(tipoIdentificacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tipoIdentificacion = new TipoIdentificacion();
        tipoIdentificacion.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoIdentificacion() throws Exception {
        int databaseSizeBeforeCreate = tipoIdentificacionRepository.findAll().size();

        // Create the TipoIdentificacion
        TipoIdentificacionDTO tipoIdentificacionDTO = tipoIdentificacionMapper.tipoIdentificacionToTipoIdentificacionDTO(tipoIdentificacion);

        restTipoIdentificacionMockMvc.perform(post("/api/tipoIdentificacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoIdentificacionDTO)))
                .andExpect(status().isCreated());

        // Validate the TipoIdentificacion in the database
        List<TipoIdentificacion> tipoIdentificacions = tipoIdentificacionRepository.findAll();
        assertThat(tipoIdentificacions).hasSize(databaseSizeBeforeCreate + 1);
        TipoIdentificacion testTipoIdentificacion = tipoIdentificacions.get(tipoIdentificacions.size() - 1);
        assertThat(testTipoIdentificacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoIdentificacionRepository.findAll().size();
        // set the field null
        tipoIdentificacion.setNombre(null);

        // Create the TipoIdentificacion, which fails.
        TipoIdentificacionDTO tipoIdentificacionDTO = tipoIdentificacionMapper.tipoIdentificacionToTipoIdentificacionDTO(tipoIdentificacion);

        restTipoIdentificacionMockMvc.perform(post("/api/tipoIdentificacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoIdentificacionDTO)))
                .andExpect(status().isBadRequest());

        List<TipoIdentificacion> tipoIdentificacions = tipoIdentificacionRepository.findAll();
        assertThat(tipoIdentificacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoIdentificacions() throws Exception {
        // Initialize the database
        tipoIdentificacionRepository.saveAndFlush(tipoIdentificacion);

        // Get all the tipoIdentificacions
        restTipoIdentificacionMockMvc.perform(get("/api/tipoIdentificacions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tipoIdentificacion.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getTipoIdentificacion() throws Exception {
        // Initialize the database
        tipoIdentificacionRepository.saveAndFlush(tipoIdentificacion);

        // Get the tipoIdentificacion
        restTipoIdentificacionMockMvc.perform(get("/api/tipoIdentificacions/{id}", tipoIdentificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tipoIdentificacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoIdentificacion() throws Exception {
        // Get the tipoIdentificacion
        restTipoIdentificacionMockMvc.perform(get("/api/tipoIdentificacions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoIdentificacion() throws Exception {
        // Initialize the database
        tipoIdentificacionRepository.saveAndFlush(tipoIdentificacion);

		int databaseSizeBeforeUpdate = tipoIdentificacionRepository.findAll().size();

        // Update the tipoIdentificacion
        tipoIdentificacion.setNombre(UPDATED_NOMBRE);
        
        TipoIdentificacionDTO tipoIdentificacionDTO = tipoIdentificacionMapper.tipoIdentificacionToTipoIdentificacionDTO(tipoIdentificacion);

        restTipoIdentificacionMockMvc.perform(put("/api/tipoIdentificacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoIdentificacionDTO)))
                .andExpect(status().isOk());

        // Validate the TipoIdentificacion in the database
        List<TipoIdentificacion> tipoIdentificacions = tipoIdentificacionRepository.findAll();
        assertThat(tipoIdentificacions).hasSize(databaseSizeBeforeUpdate);
        TipoIdentificacion testTipoIdentificacion = tipoIdentificacions.get(tipoIdentificacions.size() - 1);
        assertThat(testTipoIdentificacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteTipoIdentificacion() throws Exception {
        // Initialize the database
        tipoIdentificacionRepository.saveAndFlush(tipoIdentificacion);

		int databaseSizeBeforeDelete = tipoIdentificacionRepository.findAll().size();

        // Get the tipoIdentificacion
        restTipoIdentificacionMockMvc.perform(delete("/api/tipoIdentificacions/{id}", tipoIdentificacion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoIdentificacion> tipoIdentificacions = tipoIdentificacionRepository.findAll();
        assertThat(tipoIdentificacions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
