package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.Proveedores;
import co.com.importcolex.tejeduria.repository.ProveedoresRepository;
import co.com.importcolex.tejeduria.web.rest.dto.ProveedoresDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.ProveedoresMapper;

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
 * Test class for the ProveedoresResource REST controller.
 *
 * @see ProveedoresResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProveedoresResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    private static final String DEFAULT_IDENTIFICACION = "SAMPLE_TEXT";
    private static final String UPDATED_IDENTIFICACION = "UPDATED_TEXT";
    private static final String DEFAULT_DIRECCION = "SAMPLE_TEXT";
    private static final String UPDATED_DIRECCION = "UPDATED_TEXT";
    private static final String DEFAULT_TELEFONO = "SAMPLE_TEXT";
    private static final String UPDATED_TELEFONO = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    @Inject
    private ProveedoresRepository proveedoresRepository;

    @Inject
    private ProveedoresMapper proveedoresMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProveedoresMockMvc;

    private Proveedores proveedores;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProveedoresResource proveedoresResource = new ProveedoresResource();
        ReflectionTestUtils.setField(proveedoresResource, "proveedoresRepository", proveedoresRepository);
        ReflectionTestUtils.setField(proveedoresResource, "proveedoresMapper", proveedoresMapper);
        this.restProveedoresMockMvc = MockMvcBuilders.standaloneSetup(proveedoresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        proveedores = new Proveedores();
        proveedores.setNombre(DEFAULT_NOMBRE);
        proveedores.setIdentificacion(DEFAULT_IDENTIFICACION);
        proveedores.setDireccion(DEFAULT_DIRECCION);
        proveedores.setTelefono(DEFAULT_TELEFONO);
        proveedores.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createProveedores() throws Exception {
        int databaseSizeBeforeCreate = proveedoresRepository.findAll().size();

        // Create the Proveedores
        ProveedoresDTO proveedoresDTO = proveedoresMapper.proveedoresToProveedoresDTO(proveedores);

        restProveedoresMockMvc.perform(post("/api/proveedoress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedoresDTO)))
                .andExpect(status().isCreated());

        // Validate the Proveedores in the database
        List<Proveedores> proveedoress = proveedoresRepository.findAll();
        assertThat(proveedoress).hasSize(databaseSizeBeforeCreate + 1);
        Proveedores testProveedores = proveedoress.get(proveedoress.size() - 1);
        assertThat(testProveedores.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProveedores.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testProveedores.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testProveedores.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testProveedores.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedoresRepository.findAll().size();
        // set the field null
        proveedores.setNombre(null);

        // Create the Proveedores, which fails.
        ProveedoresDTO proveedoresDTO = proveedoresMapper.proveedoresToProveedoresDTO(proveedores);

        restProveedoresMockMvc.perform(post("/api/proveedoress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedoresDTO)))
                .andExpect(status().isBadRequest());

        List<Proveedores> proveedoress = proveedoresRepository.findAll();
        assertThat(proveedoress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedoresRepository.findAll().size();
        // set the field null
        proveedores.setIdentificacion(null);

        // Create the Proveedores, which fails.
        ProveedoresDTO proveedoresDTO = proveedoresMapper.proveedoresToProveedoresDTO(proveedores);

        restProveedoresMockMvc.perform(post("/api/proveedoress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedoresDTO)))
                .andExpect(status().isBadRequest());

        List<Proveedores> proveedoress = proveedoresRepository.findAll();
        assertThat(proveedoress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedoresRepository.findAll().size();
        // set the field null
        proveedores.setDireccion(null);

        // Create the Proveedores, which fails.
        ProveedoresDTO proveedoresDTO = proveedoresMapper.proveedoresToProveedoresDTO(proveedores);

        restProveedoresMockMvc.perform(post("/api/proveedoress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedoresDTO)))
                .andExpect(status().isBadRequest());

        List<Proveedores> proveedoress = proveedoresRepository.findAll();
        assertThat(proveedoress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedoresRepository.findAll().size();
        // set the field null
        proveedores.setTelefono(null);

        // Create the Proveedores, which fails.
        ProveedoresDTO proveedoresDTO = proveedoresMapper.proveedoresToProveedoresDTO(proveedores);

        restProveedoresMockMvc.perform(post("/api/proveedoress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedoresDTO)))
                .andExpect(status().isBadRequest());

        List<Proveedores> proveedoress = proveedoresRepository.findAll();
        assertThat(proveedoress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedoresRepository.findAll().size();
        // set the field null
        proveedores.setEmail(null);

        // Create the Proveedores, which fails.
        ProveedoresDTO proveedoresDTO = proveedoresMapper.proveedoresToProveedoresDTO(proveedores);

        restProveedoresMockMvc.perform(post("/api/proveedoress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedoresDTO)))
                .andExpect(status().isBadRequest());

        List<Proveedores> proveedoress = proveedoresRepository.findAll();
        assertThat(proveedoress).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProveedoress() throws Exception {
        // Initialize the database
        proveedoresRepository.saveAndFlush(proveedores);

        // Get all the proveedoress
        restProveedoresMockMvc.perform(get("/api/proveedoress"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(proveedores.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION.toString())))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getProveedores() throws Exception {
        // Initialize the database
        proveedoresRepository.saveAndFlush(proveedores);

        // Get the proveedores
        restProveedoresMockMvc.perform(get("/api/proveedoress/{id}", proveedores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(proveedores.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProveedores() throws Exception {
        // Get the proveedores
        restProveedoresMockMvc.perform(get("/api/proveedoress/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProveedores() throws Exception {
        // Initialize the database
        proveedoresRepository.saveAndFlush(proveedores);

		int databaseSizeBeforeUpdate = proveedoresRepository.findAll().size();

        // Update the proveedores
        proveedores.setNombre(UPDATED_NOMBRE);
        proveedores.setIdentificacion(UPDATED_IDENTIFICACION);
        proveedores.setDireccion(UPDATED_DIRECCION);
        proveedores.setTelefono(UPDATED_TELEFONO);
        proveedores.setEmail(UPDATED_EMAIL);
        
        ProveedoresDTO proveedoresDTO = proveedoresMapper.proveedoresToProveedoresDTO(proveedores);

        restProveedoresMockMvc.perform(put("/api/proveedoress")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedoresDTO)))
                .andExpect(status().isOk());

        // Validate the Proveedores in the database
        List<Proveedores> proveedoress = proveedoresRepository.findAll();
        assertThat(proveedoress).hasSize(databaseSizeBeforeUpdate);
        Proveedores testProveedores = proveedoress.get(proveedoress.size() - 1);
        assertThat(testProveedores.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProveedores.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testProveedores.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testProveedores.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testProveedores.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteProveedores() throws Exception {
        // Initialize the database
        proveedoresRepository.saveAndFlush(proveedores);

		int databaseSizeBeforeDelete = proveedoresRepository.findAll().size();

        // Get the proveedores
        restProveedoresMockMvc.perform(delete("/api/proveedoress/{id}", proveedores.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Proveedores> proveedoress = proveedoresRepository.findAll();
        assertThat(proveedoress).hasSize(databaseSizeBeforeDelete - 1);
    }
}
