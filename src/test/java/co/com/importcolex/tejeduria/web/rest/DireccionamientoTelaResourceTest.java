package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.DireccionamientoTela;
import co.com.importcolex.tejeduria.repository.DireccionamientoTelaRepository;
import co.com.importcolex.tejeduria.web.rest.dto.DireccionamientoTelaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.DireccionamientoTelaMapper;

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
 * Test class for the DireccionamientoTelaResource REST controller.
 *
 * @see DireccionamientoTelaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DireccionamientoTelaResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    @Inject
    private DireccionamientoTelaRepository direccionamientoTelaRepository;

    @Inject
    private DireccionamientoTelaMapper direccionamientoTelaMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDireccionamientoTelaMockMvc;

    private DireccionamientoTela direccionamientoTela;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DireccionamientoTelaResource direccionamientoTelaResource = new DireccionamientoTelaResource();
        ReflectionTestUtils.setField(direccionamientoTelaResource, "direccionamientoTelaRepository", direccionamientoTelaRepository);
        ReflectionTestUtils.setField(direccionamientoTelaResource, "direccionamientoTelaMapper", direccionamientoTelaMapper);
        this.restDireccionamientoTelaMockMvc = MockMvcBuilders.standaloneSetup(direccionamientoTelaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        direccionamientoTela = new DireccionamientoTela();
        direccionamientoTela.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createDireccionamientoTela() throws Exception {
        int databaseSizeBeforeCreate = direccionamientoTelaRepository.findAll().size();

        // Create the DireccionamientoTela
        DireccionamientoTelaDTO direccionamientoTelaDTO = direccionamientoTelaMapper.direccionamientoTelaToDireccionamientoTelaDTO(direccionamientoTela);

        restDireccionamientoTelaMockMvc.perform(post("/api/direccionamientoTelas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(direccionamientoTelaDTO)))
                .andExpect(status().isCreated());

        // Validate the DireccionamientoTela in the database
        List<DireccionamientoTela> direccionamientoTelas = direccionamientoTelaRepository.findAll();
        assertThat(direccionamientoTelas).hasSize(databaseSizeBeforeCreate + 1);
        DireccionamientoTela testDireccionamientoTela = direccionamientoTelas.get(direccionamientoTelas.size() - 1);
        assertThat(testDireccionamientoTela.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = direccionamientoTelaRepository.findAll().size();
        // set the field null
        direccionamientoTela.setNombre(null);

        // Create the DireccionamientoTela, which fails.
        DireccionamientoTelaDTO direccionamientoTelaDTO = direccionamientoTelaMapper.direccionamientoTelaToDireccionamientoTelaDTO(direccionamientoTela);

        restDireccionamientoTelaMockMvc.perform(post("/api/direccionamientoTelas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(direccionamientoTelaDTO)))
                .andExpect(status().isBadRequest());

        List<DireccionamientoTela> direccionamientoTelas = direccionamientoTelaRepository.findAll();
        assertThat(direccionamientoTelas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDireccionamientoTelas() throws Exception {
        // Initialize the database
        direccionamientoTelaRepository.saveAndFlush(direccionamientoTela);

        // Get all the direccionamientoTelas
        restDireccionamientoTelaMockMvc.perform(get("/api/direccionamientoTelas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(direccionamientoTela.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getDireccionamientoTela() throws Exception {
        // Initialize the database
        direccionamientoTelaRepository.saveAndFlush(direccionamientoTela);

        // Get the direccionamientoTela
        restDireccionamientoTelaMockMvc.perform(get("/api/direccionamientoTelas/{id}", direccionamientoTela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(direccionamientoTela.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDireccionamientoTela() throws Exception {
        // Get the direccionamientoTela
        restDireccionamientoTelaMockMvc.perform(get("/api/direccionamientoTelas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDireccionamientoTela() throws Exception {
        // Initialize the database
        direccionamientoTelaRepository.saveAndFlush(direccionamientoTela);

		int databaseSizeBeforeUpdate = direccionamientoTelaRepository.findAll().size();

        // Update the direccionamientoTela
        direccionamientoTela.setNombre(UPDATED_NOMBRE);
        
        DireccionamientoTelaDTO direccionamientoTelaDTO = direccionamientoTelaMapper.direccionamientoTelaToDireccionamientoTelaDTO(direccionamientoTela);

        restDireccionamientoTelaMockMvc.perform(put("/api/direccionamientoTelas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(direccionamientoTelaDTO)))
                .andExpect(status().isOk());

        // Validate the DireccionamientoTela in the database
        List<DireccionamientoTela> direccionamientoTelas = direccionamientoTelaRepository.findAll();
        assertThat(direccionamientoTelas).hasSize(databaseSizeBeforeUpdate);
        DireccionamientoTela testDireccionamientoTela = direccionamientoTelas.get(direccionamientoTelas.size() - 1);
        assertThat(testDireccionamientoTela.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteDireccionamientoTela() throws Exception {
        // Initialize the database
        direccionamientoTelaRepository.saveAndFlush(direccionamientoTela);

		int databaseSizeBeforeDelete = direccionamientoTelaRepository.findAll().size();

        // Get the direccionamientoTela
        restDireccionamientoTelaMockMvc.perform(delete("/api/direccionamientoTelas/{id}", direccionamientoTela.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DireccionamientoTela> direccionamientoTelas = direccionamientoTelaRepository.findAll();
        assertThat(direccionamientoTelas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
