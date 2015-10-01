package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.FibrasTelaCruda;
import co.com.importcolex.tejeduria.repository.FibrasTelaCrudaRepository;
import co.com.importcolex.tejeduria.web.rest.dto.FibrasTelaCrudaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.FibrasTelaCrudaMapper;

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
 * Test class for the FibrasTelaCrudaResource REST controller.
 *
 * @see FibrasTelaCrudaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FibrasTelaCrudaResourceTest {


    private static final Integer DEFAULT_REFERENCIA = 1;
    private static final Integer UPDATED_REFERENCIA = 2;

    private static final Integer DEFAULT_PESO = 1;
    private static final Integer UPDATED_PESO = 2;

    @Inject
    private FibrasTelaCrudaRepository fibrasTelaCrudaRepository;

    @Inject
    private FibrasTelaCrudaMapper fibrasTelaCrudaMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFibrasTelaCrudaMockMvc;

    private FibrasTelaCruda fibrasTelaCruda;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FibrasTelaCrudaResource fibrasTelaCrudaResource = new FibrasTelaCrudaResource();
        ReflectionTestUtils.setField(fibrasTelaCrudaResource, "fibrasTelaCrudaRepository", fibrasTelaCrudaRepository);
        ReflectionTestUtils.setField(fibrasTelaCrudaResource, "fibrasTelaCrudaMapper", fibrasTelaCrudaMapper);
        this.restFibrasTelaCrudaMockMvc = MockMvcBuilders.standaloneSetup(fibrasTelaCrudaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fibrasTelaCruda = new FibrasTelaCruda();
        fibrasTelaCruda.setReferencia(DEFAULT_REFERENCIA);
        fibrasTelaCruda.setPeso(DEFAULT_PESO);
    }

    @Test
    @Transactional
    public void createFibrasTelaCruda() throws Exception {
        int databaseSizeBeforeCreate = fibrasTelaCrudaRepository.findAll().size();

        // Create the FibrasTelaCruda
        FibrasTelaCrudaDTO fibrasTelaCrudaDTO = fibrasTelaCrudaMapper.fibrasTelaCrudaToFibrasTelaCrudaDTO(fibrasTelaCruda);

        restFibrasTelaCrudaMockMvc.perform(post("/api/fibrasTelaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasTelaCrudaDTO)))
                .andExpect(status().isCreated());

        // Validate the FibrasTelaCruda in the database
        List<FibrasTelaCruda> fibrasTelaCrudas = fibrasTelaCrudaRepository.findAll();
        assertThat(fibrasTelaCrudas).hasSize(databaseSizeBeforeCreate + 1);
        FibrasTelaCruda testFibrasTelaCruda = fibrasTelaCrudas.get(fibrasTelaCrudas.size() - 1);
        assertThat(testFibrasTelaCruda.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
        assertThat(testFibrasTelaCruda.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    public void checkReferenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fibrasTelaCrudaRepository.findAll().size();
        // set the field null
        fibrasTelaCruda.setReferencia(null);

        // Create the FibrasTelaCruda, which fails.
        FibrasTelaCrudaDTO fibrasTelaCrudaDTO = fibrasTelaCrudaMapper.fibrasTelaCrudaToFibrasTelaCrudaDTO(fibrasTelaCruda);

        restFibrasTelaCrudaMockMvc.perform(post("/api/fibrasTelaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasTelaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<FibrasTelaCruda> fibrasTelaCrudas = fibrasTelaCrudaRepository.findAll();
        assertThat(fibrasTelaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fibrasTelaCrudaRepository.findAll().size();
        // set the field null
        fibrasTelaCruda.setPeso(null);

        // Create the FibrasTelaCruda, which fails.
        FibrasTelaCrudaDTO fibrasTelaCrudaDTO = fibrasTelaCrudaMapper.fibrasTelaCrudaToFibrasTelaCrudaDTO(fibrasTelaCruda);

        restFibrasTelaCrudaMockMvc.perform(post("/api/fibrasTelaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasTelaCrudaDTO)))
                .andExpect(status().isBadRequest());

        List<FibrasTelaCruda> fibrasTelaCrudas = fibrasTelaCrudaRepository.findAll();
        assertThat(fibrasTelaCrudas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFibrasTelaCrudas() throws Exception {
        // Initialize the database
        fibrasTelaCrudaRepository.saveAndFlush(fibrasTelaCruda);

        // Get all the fibrasTelaCrudas
        restFibrasTelaCrudaMockMvc.perform(get("/api/fibrasTelaCrudas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fibrasTelaCruda.getId().intValue())))
                .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA)))
                .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)));
    }

    @Test
    @Transactional
    public void getFibrasTelaCruda() throws Exception {
        // Initialize the database
        fibrasTelaCrudaRepository.saveAndFlush(fibrasTelaCruda);

        // Get the fibrasTelaCruda
        restFibrasTelaCrudaMockMvc.perform(get("/api/fibrasTelaCrudas/{id}", fibrasTelaCruda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fibrasTelaCruda.getId().intValue()))
            .andExpect(jsonPath("$.referencia").value(DEFAULT_REFERENCIA))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO));
    }

    @Test
    @Transactional
    public void getNonExistingFibrasTelaCruda() throws Exception {
        // Get the fibrasTelaCruda
        restFibrasTelaCrudaMockMvc.perform(get("/api/fibrasTelaCrudas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFibrasTelaCruda() throws Exception {
        // Initialize the database
        fibrasTelaCrudaRepository.saveAndFlush(fibrasTelaCruda);

		int databaseSizeBeforeUpdate = fibrasTelaCrudaRepository.findAll().size();

        // Update the fibrasTelaCruda
        fibrasTelaCruda.setReferencia(UPDATED_REFERENCIA);
        fibrasTelaCruda.setPeso(UPDATED_PESO);
        
        FibrasTelaCrudaDTO fibrasTelaCrudaDTO = fibrasTelaCrudaMapper.fibrasTelaCrudaToFibrasTelaCrudaDTO(fibrasTelaCruda);

        restFibrasTelaCrudaMockMvc.perform(put("/api/fibrasTelaCrudas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fibrasTelaCrudaDTO)))
                .andExpect(status().isOk());

        // Validate the FibrasTelaCruda in the database
        List<FibrasTelaCruda> fibrasTelaCrudas = fibrasTelaCrudaRepository.findAll();
        assertThat(fibrasTelaCrudas).hasSize(databaseSizeBeforeUpdate);
        FibrasTelaCruda testFibrasTelaCruda = fibrasTelaCrudas.get(fibrasTelaCrudas.size() - 1);
        assertThat(testFibrasTelaCruda.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
        assertThat(testFibrasTelaCruda.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    public void deleteFibrasTelaCruda() throws Exception {
        // Initialize the database
        fibrasTelaCrudaRepository.saveAndFlush(fibrasTelaCruda);

		int databaseSizeBeforeDelete = fibrasTelaCrudaRepository.findAll().size();

        // Get the fibrasTelaCruda
        restFibrasTelaCrudaMockMvc.perform(delete("/api/fibrasTelaCrudas/{id}", fibrasTelaCruda.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FibrasTelaCruda> fibrasTelaCrudas = fibrasTelaCrudaRepository.findAll();
        assertThat(fibrasTelaCrudas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
