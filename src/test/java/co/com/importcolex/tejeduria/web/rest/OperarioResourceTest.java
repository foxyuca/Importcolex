package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.Operario;
import co.com.importcolex.tejeduria.repository.OperarioRepository;
import co.com.importcolex.tejeduria.web.rest.dto.OperarioDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.OperarioMapper;

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
 * Test class for the OperarioResource REST controller.
 *
 * @see OperarioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OperarioResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    private static final String DEFAULT_TURNO = "SAMPLE_TEXT";
    private static final String UPDATED_TURNO = "UPDATED_TEXT";
    private static final String DEFAULT_REFERENCIA = "SAMPLE_TEXT";
    private static final String UPDATED_REFERENCIA = "UPDATED_TEXT";

    @Inject
    private OperarioRepository operarioRepository;

    @Inject
    private OperarioMapper operarioMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOperarioMockMvc;

    private Operario operario;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OperarioResource operarioResource = new OperarioResource();
        ReflectionTestUtils.setField(operarioResource, "operarioRepository", operarioRepository);
        ReflectionTestUtils.setField(operarioResource, "operarioMapper", operarioMapper);
        this.restOperarioMockMvc = MockMvcBuilders.standaloneSetup(operarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        operario = new Operario();
        operario.setNombre(DEFAULT_NOMBRE);
        operario.setTurno(DEFAULT_TURNO);
        operario.setReferencia(DEFAULT_REFERENCIA);
    }

    @Test
    @Transactional
    public void createOperario() throws Exception {
        int databaseSizeBeforeCreate = operarioRepository.findAll().size();

        // Create the Operario
        OperarioDTO operarioDTO = operarioMapper.operarioToOperarioDTO(operario);

        restOperarioMockMvc.perform(post("/api/operarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operarioDTO)))
                .andExpect(status().isCreated());

        // Validate the Operario in the database
        List<Operario> operarios = operarioRepository.findAll();
        assertThat(operarios).hasSize(databaseSizeBeforeCreate + 1);
        Operario testOperario = operarios.get(operarios.size() - 1);
        assertThat(testOperario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testOperario.getTurno()).isEqualTo(DEFAULT_TURNO);
        assertThat(testOperario.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = operarioRepository.findAll().size();
        // set the field null
        operario.setNombre(null);

        // Create the Operario, which fails.
        OperarioDTO operarioDTO = operarioMapper.operarioToOperarioDTO(operario);

        restOperarioMockMvc.perform(post("/api/operarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operarioDTO)))
                .andExpect(status().isBadRequest());

        List<Operario> operarios = operarioRepository.findAll();
        assertThat(operarios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTurnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = operarioRepository.findAll().size();
        // set the field null
        operario.setTurno(null);

        // Create the Operario, which fails.
        OperarioDTO operarioDTO = operarioMapper.operarioToOperarioDTO(operario);

        restOperarioMockMvc.perform(post("/api/operarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operarioDTO)))
                .andExpect(status().isBadRequest());

        List<Operario> operarios = operarioRepository.findAll();
        assertThat(operarios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = operarioRepository.findAll().size();
        // set the field null
        operario.setReferencia(null);

        // Create the Operario, which fails.
        OperarioDTO operarioDTO = operarioMapper.operarioToOperarioDTO(operario);

        restOperarioMockMvc.perform(post("/api/operarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operarioDTO)))
                .andExpect(status().isBadRequest());

        List<Operario> operarios = operarioRepository.findAll();
        assertThat(operarios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperarios() throws Exception {
        // Initialize the database
        operarioRepository.saveAndFlush(operario);

        // Get all the operarios
        restOperarioMockMvc.perform(get("/api/operarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(operario.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].turno").value(hasItem(DEFAULT_TURNO.toString())))
                .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA.toString())));
    }

    @Test
    @Transactional
    public void getOperario() throws Exception {
        // Initialize the database
        operarioRepository.saveAndFlush(operario);

        // Get the operario
        restOperarioMockMvc.perform(get("/api/operarios/{id}", operario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(operario.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.turno").value(DEFAULT_TURNO.toString()))
            .andExpect(jsonPath("$.referencia").value(DEFAULT_REFERENCIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOperario() throws Exception {
        // Get the operario
        restOperarioMockMvc.perform(get("/api/operarios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperario() throws Exception {
        // Initialize the database
        operarioRepository.saveAndFlush(operario);

		int databaseSizeBeforeUpdate = operarioRepository.findAll().size();

        // Update the operario
        operario.setNombre(UPDATED_NOMBRE);
        operario.setTurno(UPDATED_TURNO);
        operario.setReferencia(UPDATED_REFERENCIA);
        
        OperarioDTO operarioDTO = operarioMapper.operarioToOperarioDTO(operario);

        restOperarioMockMvc.perform(put("/api/operarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(operarioDTO)))
                .andExpect(status().isOk());

        // Validate the Operario in the database
        List<Operario> operarios = operarioRepository.findAll();
        assertThat(operarios).hasSize(databaseSizeBeforeUpdate);
        Operario testOperario = operarios.get(operarios.size() - 1);
        assertThat(testOperario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testOperario.getTurno()).isEqualTo(UPDATED_TURNO);
        assertThat(testOperario.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    public void deleteOperario() throws Exception {
        // Initialize the database
        operarioRepository.saveAndFlush(operario);

		int databaseSizeBeforeDelete = operarioRepository.findAll().size();

        // Get the operario
        restOperarioMockMvc.perform(delete("/api/operarios/{id}", operario.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Operario> operarios = operarioRepository.findAll();
        assertThat(operarios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
