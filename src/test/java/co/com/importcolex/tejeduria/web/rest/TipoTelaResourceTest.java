package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.TipoTela;
import co.com.importcolex.tejeduria.repository.TipoTelaRepository;
import co.com.importcolex.tejeduria.web.rest.dto.TipoTelaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.TipoTelaMapper;

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
 * Test class for the TipoTelaResource REST controller.
 *
 * @see TipoTelaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TipoTelaResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    @Inject
    private TipoTelaRepository tipoTelaRepository;

    @Inject
    private TipoTelaMapper tipoTelaMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTipoTelaMockMvc;

    private TipoTela tipoTela;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoTelaResource tipoTelaResource = new TipoTelaResource();
        ReflectionTestUtils.setField(tipoTelaResource, "tipoTelaRepository", tipoTelaRepository);
        ReflectionTestUtils.setField(tipoTelaResource, "tipoTelaMapper", tipoTelaMapper);
        this.restTipoTelaMockMvc = MockMvcBuilders.standaloneSetup(tipoTelaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tipoTela = new TipoTela();
        tipoTela.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoTela() throws Exception {
        int databaseSizeBeforeCreate = tipoTelaRepository.findAll().size();

        // Create the TipoTela
        TipoTelaDTO tipoTelaDTO = tipoTelaMapper.tipoTelaToTipoTelaDTO(tipoTela);

        restTipoTelaMockMvc.perform(post("/api/tipoTelas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoTelaDTO)))
                .andExpect(status().isCreated());

        // Validate the TipoTela in the database
        List<TipoTela> tipoTelas = tipoTelaRepository.findAll();
        assertThat(tipoTelas).hasSize(databaseSizeBeforeCreate + 1);
        TipoTela testTipoTela = tipoTelas.get(tipoTelas.size() - 1);
        assertThat(testTipoTela.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoTelaRepository.findAll().size();
        // set the field null
        tipoTela.setNombre(null);

        // Create the TipoTela, which fails.
        TipoTelaDTO tipoTelaDTO = tipoTelaMapper.tipoTelaToTipoTelaDTO(tipoTela);

        restTipoTelaMockMvc.perform(post("/api/tipoTelas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoTelaDTO)))
                .andExpect(status().isBadRequest());

        List<TipoTela> tipoTelas = tipoTelaRepository.findAll();
        assertThat(tipoTelas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoTelas() throws Exception {
        // Initialize the database
        tipoTelaRepository.saveAndFlush(tipoTela);

        // Get all the tipoTelas
        restTipoTelaMockMvc.perform(get("/api/tipoTelas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tipoTela.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getTipoTela() throws Exception {
        // Initialize the database
        tipoTelaRepository.saveAndFlush(tipoTela);

        // Get the tipoTela
        restTipoTelaMockMvc.perform(get("/api/tipoTelas/{id}", tipoTela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tipoTela.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoTela() throws Exception {
        // Get the tipoTela
        restTipoTelaMockMvc.perform(get("/api/tipoTelas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoTela() throws Exception {
        // Initialize the database
        tipoTelaRepository.saveAndFlush(tipoTela);

		int databaseSizeBeforeUpdate = tipoTelaRepository.findAll().size();

        // Update the tipoTela
        tipoTela.setNombre(UPDATED_NOMBRE);
        
        TipoTelaDTO tipoTelaDTO = tipoTelaMapper.tipoTelaToTipoTelaDTO(tipoTela);

        restTipoTelaMockMvc.perform(put("/api/tipoTelas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoTelaDTO)))
                .andExpect(status().isOk());

        // Validate the TipoTela in the database
        List<TipoTela> tipoTelas = tipoTelaRepository.findAll();
        assertThat(tipoTelas).hasSize(databaseSizeBeforeUpdate);
        TipoTela testTipoTela = tipoTelas.get(tipoTelas.size() - 1);
        assertThat(testTipoTela.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteTipoTela() throws Exception {
        // Initialize the database
        tipoTelaRepository.saveAndFlush(tipoTela);

		int databaseSizeBeforeDelete = tipoTelaRepository.findAll().size();

        // Get the tipoTela
        restTipoTelaMockMvc.perform(delete("/api/tipoTelas/{id}", tipoTela.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoTela> tipoTelas = tipoTelaRepository.findAll();
        assertThat(tipoTelas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
