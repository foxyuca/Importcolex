package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.InventarioFibras;
import co.com.importcolex.tejeduria.repository.InventarioFibrasRepository;
import co.com.importcolex.tejeduria.web.rest.dto.InventarioFibrasDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.InventarioFibrasMapper;

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
 * Test class for the InventarioFibrasResource REST controller.
 *
 * @see InventarioFibrasResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InventarioFibrasResourceTest {

    private static final String DEFAULT_LOTE = "SAMPLE_TEXT";
    private static final String UPDATED_LOTE = "UPDATED_TEXT";

    private static final Integer DEFAULT_INVENTARIO_INICIAL = 1;
    private static final Integer UPDATED_INVENTARIO_INICIAL = 2;

    private static final Integer DEFAULT_INVENTARIO_FINAL = 1;
    private static final Integer UPDATED_INVENTARIO_FINAL = 2;

    private static final Double DEFAULT_COSTO = 1D;
    private static final Double UPDATED_COSTO = 2D;

    @Inject
    private InventarioFibrasRepository inventarioFibrasRepository;

    @Inject
    private InventarioFibrasMapper inventarioFibrasMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInventarioFibrasMockMvc;

    private InventarioFibras inventarioFibras;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InventarioFibrasResource inventarioFibrasResource = new InventarioFibrasResource();
        ReflectionTestUtils.setField(inventarioFibrasResource, "inventarioFibrasRepository", inventarioFibrasRepository);
        ReflectionTestUtils.setField(inventarioFibrasResource, "inventarioFibrasMapper", inventarioFibrasMapper);
        this.restInventarioFibrasMockMvc = MockMvcBuilders.standaloneSetup(inventarioFibrasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        inventarioFibras = new InventarioFibras();
        inventarioFibras.setLote(DEFAULT_LOTE);
        inventarioFibras.setInventarioInicial(DEFAULT_INVENTARIO_INICIAL);
        inventarioFibras.setInventarioFinal(DEFAULT_INVENTARIO_FINAL);
        inventarioFibras.setCosto(DEFAULT_COSTO);
    }

    @Test
    @Transactional
    public void createInventarioFibras() throws Exception {
        int databaseSizeBeforeCreate = inventarioFibrasRepository.findAll().size();

        // Create the InventarioFibras
        InventarioFibrasDTO inventarioFibrasDTO = inventarioFibrasMapper.inventarioFibrasToInventarioFibrasDTO(inventarioFibras);

        restInventarioFibrasMockMvc.perform(post("/api/inventarioFibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventarioFibrasDTO)))
                .andExpect(status().isCreated());

        // Validate the InventarioFibras in the database
        List<InventarioFibras> inventarioFibrass = inventarioFibrasRepository.findAll();
        assertThat(inventarioFibrass).hasSize(databaseSizeBeforeCreate + 1);
        InventarioFibras testInventarioFibras = inventarioFibrass.get(inventarioFibrass.size() - 1);
        assertThat(testInventarioFibras.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testInventarioFibras.getInventarioInicial()).isEqualTo(DEFAULT_INVENTARIO_INICIAL);
        assertThat(testInventarioFibras.getInventarioFinal()).isEqualTo(DEFAULT_INVENTARIO_FINAL);
        assertThat(testInventarioFibras.getCosto()).isEqualTo(DEFAULT_COSTO);
    }

    @Test
    @Transactional
    public void checkLoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioFibrasRepository.findAll().size();
        // set the field null
        inventarioFibras.setLote(null);

        // Create the InventarioFibras, which fails.
        InventarioFibrasDTO inventarioFibrasDTO = inventarioFibrasMapper.inventarioFibrasToInventarioFibrasDTO(inventarioFibras);

        restInventarioFibrasMockMvc.perform(post("/api/inventarioFibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventarioFibrasDTO)))
                .andExpect(status().isBadRequest());

        List<InventarioFibras> inventarioFibrass = inventarioFibrasRepository.findAll();
        assertThat(inventarioFibrass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInventarioInicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioFibrasRepository.findAll().size();
        // set the field null
        inventarioFibras.setInventarioInicial(null);

        // Create the InventarioFibras, which fails.
        InventarioFibrasDTO inventarioFibrasDTO = inventarioFibrasMapper.inventarioFibrasToInventarioFibrasDTO(inventarioFibras);

        restInventarioFibrasMockMvc.perform(post("/api/inventarioFibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventarioFibrasDTO)))
                .andExpect(status().isBadRequest());

        List<InventarioFibras> inventarioFibrass = inventarioFibrasRepository.findAll();
        assertThat(inventarioFibrass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInventarioFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioFibrasRepository.findAll().size();
        // set the field null
        inventarioFibras.setInventarioFinal(null);

        // Create the InventarioFibras, which fails.
        InventarioFibrasDTO inventarioFibrasDTO = inventarioFibrasMapper.inventarioFibrasToInventarioFibrasDTO(inventarioFibras);

        restInventarioFibrasMockMvc.perform(post("/api/inventarioFibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventarioFibrasDTO)))
                .andExpect(status().isBadRequest());

        List<InventarioFibras> inventarioFibrass = inventarioFibrasRepository.findAll();
        assertThat(inventarioFibrass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioFibrasRepository.findAll().size();
        // set the field null
        inventarioFibras.setCosto(null);

        // Create the InventarioFibras, which fails.
        InventarioFibrasDTO inventarioFibrasDTO = inventarioFibrasMapper.inventarioFibrasToInventarioFibrasDTO(inventarioFibras);

        restInventarioFibrasMockMvc.perform(post("/api/inventarioFibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventarioFibrasDTO)))
                .andExpect(status().isBadRequest());

        List<InventarioFibras> inventarioFibrass = inventarioFibrasRepository.findAll();
        assertThat(inventarioFibrass).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventarioFibrass() throws Exception {
        // Initialize the database
        inventarioFibrasRepository.saveAndFlush(inventarioFibras);

        // Get all the inventarioFibrass
        restInventarioFibrasMockMvc.perform(get("/api/inventarioFibrass"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(inventarioFibras.getId().intValue())))
                .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE.toString())))
                .andExpect(jsonPath("$.[*].inventarioInicial").value(hasItem(DEFAULT_INVENTARIO_INICIAL)))
                .andExpect(jsonPath("$.[*].inventarioFinal").value(hasItem(DEFAULT_INVENTARIO_FINAL)))
                .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())));
    }

    @Test
    @Transactional
    public void getInventarioFibras() throws Exception {
        // Initialize the database
        inventarioFibrasRepository.saveAndFlush(inventarioFibras);

        // Get the inventarioFibras
        restInventarioFibrasMockMvc.perform(get("/api/inventarioFibrass/{id}", inventarioFibras.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(inventarioFibras.getId().intValue()))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE.toString()))
            .andExpect(jsonPath("$.inventarioInicial").value(DEFAULT_INVENTARIO_INICIAL))
            .andExpect(jsonPath("$.inventarioFinal").value(DEFAULT_INVENTARIO_FINAL))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInventarioFibras() throws Exception {
        // Get the inventarioFibras
        restInventarioFibrasMockMvc.perform(get("/api/inventarioFibrass/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventarioFibras() throws Exception {
        // Initialize the database
        inventarioFibrasRepository.saveAndFlush(inventarioFibras);

		int databaseSizeBeforeUpdate = inventarioFibrasRepository.findAll().size();

        // Update the inventarioFibras
        inventarioFibras.setLote(UPDATED_LOTE);
        inventarioFibras.setInventarioInicial(UPDATED_INVENTARIO_INICIAL);
        inventarioFibras.setInventarioFinal(UPDATED_INVENTARIO_FINAL);
        inventarioFibras.setCosto(UPDATED_COSTO);
        
        InventarioFibrasDTO inventarioFibrasDTO = inventarioFibrasMapper.inventarioFibrasToInventarioFibrasDTO(inventarioFibras);

        restInventarioFibrasMockMvc.perform(put("/api/inventarioFibrass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inventarioFibrasDTO)))
                .andExpect(status().isOk());

        // Validate the InventarioFibras in the database
        List<InventarioFibras> inventarioFibrass = inventarioFibrasRepository.findAll();
        assertThat(inventarioFibrass).hasSize(databaseSizeBeforeUpdate);
        InventarioFibras testInventarioFibras = inventarioFibrass.get(inventarioFibrass.size() - 1);
        assertThat(testInventarioFibras.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testInventarioFibras.getInventarioInicial()).isEqualTo(UPDATED_INVENTARIO_INICIAL);
        assertThat(testInventarioFibras.getInventarioFinal()).isEqualTo(UPDATED_INVENTARIO_FINAL);
        assertThat(testInventarioFibras.getCosto()).isEqualTo(UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void deleteInventarioFibras() throws Exception {
        // Initialize the database
        inventarioFibrasRepository.saveAndFlush(inventarioFibras);

		int databaseSizeBeforeDelete = inventarioFibrasRepository.findAll().size();

        // Get the inventarioFibras
        restInventarioFibrasMockMvc.perform(delete("/api/inventarioFibrass/{id}", inventarioFibras.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InventarioFibras> inventarioFibrass = inventarioFibrasRepository.findAll();
        assertThat(inventarioFibrass).hasSize(databaseSizeBeforeDelete - 1);
    }
}
