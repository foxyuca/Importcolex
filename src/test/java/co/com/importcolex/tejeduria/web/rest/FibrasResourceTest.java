package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.Fibras;
import co.com.importcolex.tejeduria.repository.FibrasRepository;
import co.com.importcolex.tejeduria.web.rest.dto.FibrasDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.FibrasMapper;

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
 * Test class for the FibrasResource REST controller.
 *
 * @see FibrasResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FibrasResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    private static final String DEFAULT_TITULO = "SAMPLE_TEXT";
    private static final String UPDATED_TITULO = "UPDATED_TEXT";
    private static final String DEFAULT_FILAMENTOS = "SAMPLE_TEXT";
    private static final String UPDATED_FILAMENTOS = "UPDATED_TEXT";

    @Inject
    private FibrasRepository fibrasRepository;

    @Inject
    private FibrasMapper fibrasMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFibrasMockMvc;

    private Fibras fibras;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FibrasResource fibrasResource = new FibrasResource();
        ReflectionTestUtils.setField(fibrasResource, "fibrasRepository", fibrasRepository);
        ReflectionTestUtils.setField(fibrasResource, "fibrasMapper", fibrasMapper);
        this.restFibrasMockMvc = MockMvcBuilders.standaloneSetup(fibrasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fibras = new Fibras();
        fibras.setNombre(DEFAULT_NOMBRE);
        fibras.setTitulo(DEFAULT_TITULO);
        fibras.setFilamentos(DEFAULT_FILAMENTOS);
    }

    @Test
    @Transactional
    public void createFibras() throws Exception {
        int databaseSizeBeforeCreate = fibrasRepository.findAll().size();

        // Create the Fibras
        FibrasDTO fibrasDTO = fibrasMapper.fibrasToFibrasDTO(fibras);

        restFibrasMockMvc.perform(post("/api/fibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasDTO)))
                .andExpect(status().isCreated());

        // Validate the Fibras in the database
        List<Fibras> fibrass = fibrasRepository.findAll();
        assertThat(fibrass).hasSize(databaseSizeBeforeCreate + 1);
        Fibras testFibras = fibrass.get(fibrass.size() - 1);
        assertThat(testFibras.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testFibras.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testFibras.getFilamentos()).isEqualTo(DEFAULT_FILAMENTOS);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = fibrasRepository.findAll().size();
        // set the field null
        fibras.setNombre(null);

        // Create the Fibras, which fails.
        FibrasDTO fibrasDTO = fibrasMapper.fibrasToFibrasDTO(fibras);

        restFibrasMockMvc.perform(post("/api/fibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasDTO)))
                .andExpect(status().isBadRequest());

        List<Fibras> fibrass = fibrasRepository.findAll();
        assertThat(fibrass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = fibrasRepository.findAll().size();
        // set the field null
        fibras.setTitulo(null);

        // Create the Fibras, which fails.
        FibrasDTO fibrasDTO = fibrasMapper.fibrasToFibrasDTO(fibras);

        restFibrasMockMvc.perform(post("/api/fibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasDTO)))
                .andExpect(status().isBadRequest());

        List<Fibras> fibrass = fibrasRepository.findAll();
        assertThat(fibrass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilamentosIsRequired() throws Exception {
        int databaseSizeBeforeTest = fibrasRepository.findAll().size();
        // set the field null
        fibras.setFilamentos(null);

        // Create the Fibras, which fails.
        FibrasDTO fibrasDTO = fibrasMapper.fibrasToFibrasDTO(fibras);

        restFibrasMockMvc.perform(post("/api/fibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasDTO)))
                .andExpect(status().isBadRequest());

        List<Fibras> fibrass = fibrasRepository.findAll();
        assertThat(fibrass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFibrass() throws Exception {
        // Initialize the database
        fibrasRepository.saveAndFlush(fibras);

        // Get all the fibrass
        restFibrasMockMvc.perform(get("/api/fibrass"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fibras.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
                .andExpect(jsonPath("$.[*].filamentos").value(hasItem(DEFAULT_FILAMENTOS.toString())));
    }

    @Test
    @Transactional
    public void getFibras() throws Exception {
        // Initialize the database
        fibrasRepository.saveAndFlush(fibras);

        // Get the fibras
        restFibrasMockMvc.perform(get("/api/fibrass/{id}", fibras.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fibras.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.filamentos").value(DEFAULT_FILAMENTOS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFibras() throws Exception {
        // Get the fibras
        restFibrasMockMvc.perform(get("/api/fibrass/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFibras() throws Exception {
        // Initialize the database
        fibrasRepository.saveAndFlush(fibras);

		int databaseSizeBeforeUpdate = fibrasRepository.findAll().size();

        // Update the fibras
        fibras.setNombre(UPDATED_NOMBRE);
        fibras.setTitulo(UPDATED_TITULO);
        fibras.setFilamentos(UPDATED_FILAMENTOS);
        
        FibrasDTO fibrasDTO = fibrasMapper.fibrasToFibrasDTO(fibras);

        restFibrasMockMvc.perform(put("/api/fibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasDTO)))
                .andExpect(status().isOk());

        // Validate the Fibras in the database
        List<Fibras> fibrass = fibrasRepository.findAll();
        assertThat(fibrass).hasSize(databaseSizeBeforeUpdate);
        Fibras testFibras = fibrass.get(fibrass.size() - 1);
        assertThat(testFibras.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testFibras.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testFibras.getFilamentos()).isEqualTo(UPDATED_FILAMENTOS);
    }

    @Test
    @Transactional
    public void deleteFibras() throws Exception {
        // Initialize the database
        fibrasRepository.saveAndFlush(fibras);

		int databaseSizeBeforeDelete = fibrasRepository.findAll().size();

        // Get the fibras
        restFibrasMockMvc.perform(delete("/api/fibrass/{id}", fibras.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fibras> fibrass = fibrasRepository.findAll();
        assertThat(fibrass).hasSize(databaseSizeBeforeDelete - 1);
    }
}
