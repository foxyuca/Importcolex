package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.Clientes;
import co.com.importcolex.tejeduria.repository.ClientesRepository;
import co.com.importcolex.tejeduria.web.rest.dto.ClientesDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.ClientesMapper;

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
 * Test class for the ClientesResource REST controller.
 *
 * @see ClientesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClientesResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    private static final String DEFAULT_IDENTIFICACION = "SAMPLE_TEXT";
    private static final String UPDATED_IDENTIFICACION = "UPDATED_TEXT";
    private static final String DEFAULT_TELEFONO = "SAMPLE_TEXT";
    private static final String UPDATED_TELEFONO = "UPDATED_TEXT";
    private static final String DEFAULT_CORREO = "SAMPLE_TEXT";
    private static final String UPDATED_CORREO = "UPDATED_TEXT";
    private static final String DEFAULT_DIRECCION = "SAMPLE_TEXT";
    private static final String UPDATED_DIRECCION = "UPDATED_TEXT";

    @Inject
    private ClientesRepository clientesRepository;

    @Inject
    private ClientesMapper clientesMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClientesMockMvc;

    private Clientes clientes;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientesResource clientesResource = new ClientesResource();
        ReflectionTestUtils.setField(clientesResource, "clientesRepository", clientesRepository);
        ReflectionTestUtils.setField(clientesResource, "clientesMapper", clientesMapper);
        this.restClientesMockMvc = MockMvcBuilders.standaloneSetup(clientesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        clientes = new Clientes();
        clientes.setNombre(DEFAULT_NOMBRE);
        clientes.setIdentificacion(DEFAULT_IDENTIFICACION);
        clientes.setTelefono(DEFAULT_TELEFONO);
        clientes.setCorreo(DEFAULT_CORREO);
        clientes.setDireccion(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    public void createClientes() throws Exception {
        int databaseSizeBeforeCreate = clientesRepository.findAll().size();

        // Create the Clientes
        ClientesDTO clientesDTO = clientesMapper.clientesToClientesDTO(clientes);

        restClientesMockMvc.perform(post("/api/clientess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
                .andExpect(status().isCreated());

        // Validate the Clientes in the database
        List<Clientes> clientess = clientesRepository.findAll();
        assertThat(clientess).hasSize(databaseSizeBeforeCreate + 1);
        Clientes testClientes = clientess.get(clientess.size() - 1);
        assertThat(testClientes.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testClientes.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testClientes.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testClientes.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testClientes.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setNombre(null);

        // Create the Clientes, which fails.
        ClientesDTO clientesDTO = clientesMapper.clientesToClientesDTO(clientes);

        restClientesMockMvc.perform(post("/api/clientess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
                .andExpect(status().isBadRequest());

        List<Clientes> clientess = clientesRepository.findAll();
        assertThat(clientess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setIdentificacion(null);

        // Create the Clientes, which fails.
        ClientesDTO clientesDTO = clientesMapper.clientesToClientesDTO(clientes);

        restClientesMockMvc.perform(post("/api/clientess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
                .andExpect(status().isBadRequest());

        List<Clientes> clientess = clientesRepository.findAll();
        assertThat(clientess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setTelefono(null);

        // Create the Clientes, which fails.
        ClientesDTO clientesDTO = clientesMapper.clientesToClientesDTO(clientes);

        restClientesMockMvc.perform(post("/api/clientess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
                .andExpect(status().isBadRequest());

        List<Clientes> clientess = clientesRepository.findAll();
        assertThat(clientess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setCorreo(null);

        // Create the Clientes, which fails.
        ClientesDTO clientesDTO = clientesMapper.clientesToClientesDTO(clientes);

        restClientesMockMvc.perform(post("/api/clientess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
                .andExpect(status().isBadRequest());

        List<Clientes> clientess = clientesRepository.findAll();
        assertThat(clientess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientess() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        // Get all the clientess
        restClientesMockMvc.perform(get("/api/clientess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clientes.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
                .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())));
    }

    @Test
    @Transactional
    public void getClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        // Get the clientes
        restClientesMockMvc.perform(get("/api/clientess/{id}", clientes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(clientes.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientes() throws Exception {
        // Get the clientes
        restClientesMockMvc.perform(get("/api/clientess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

		int databaseSizeBeforeUpdate = clientesRepository.findAll().size();

        // Update the clientes
        clientes.setNombre(UPDATED_NOMBRE);
        clientes.setIdentificacion(UPDATED_IDENTIFICACION);
        clientes.setTelefono(UPDATED_TELEFONO);
        clientes.setCorreo(UPDATED_CORREO);
        clientes.setDireccion(UPDATED_DIRECCION);
        
        ClientesDTO clientesDTO = clientesMapper.clientesToClientesDTO(clientes);

        restClientesMockMvc.perform(put("/api/clientess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
                .andExpect(status().isOk());

        // Validate the Clientes in the database
        List<Clientes> clientess = clientesRepository.findAll();
        assertThat(clientess).hasSize(databaseSizeBeforeUpdate);
        Clientes testClientes = clientess.get(clientess.size() - 1);
        assertThat(testClientes.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testClientes.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testClientes.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testClientes.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testClientes.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void deleteClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

		int databaseSizeBeforeDelete = clientesRepository.findAll().size();

        // Get the clientes
        restClientesMockMvc.perform(delete("/api/clientess/{id}", clientes.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Clientes> clientess = clientesRepository.findAll();
        assertThat(clientess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
