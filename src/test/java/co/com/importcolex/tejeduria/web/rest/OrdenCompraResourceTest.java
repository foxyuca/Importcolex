package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.OrdenCompra;
import co.com.importcolex.tejeduria.repository.OrdenCompraRepository;
import co.com.importcolex.tejeduria.web.rest.dto.OrdenCompraDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.OrdenCompraMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OrdenCompraResource REST controller.
 *
 * @see OrdenCompraResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrdenCompraResourceTest {


    private static final BigDecimal DEFAULT_TICKET = new BigDecimal(1);
    private static final BigDecimal UPDATED_TICKET = new BigDecimal(2);

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Double DEFAULT_COSTO = 1D;
    private static final Double UPDATED_COSTO = 2D;

    private static final Boolean DEFAULT_APROVADA = false;
    private static final Boolean UPDATED_APROVADA = true;

    private static final Boolean DEFAULT_ORDENADA = false;
    private static final Boolean UPDATED_ORDENADA = true;

    private static final Boolean DEFAULT_RECIBIDA = false;
    private static final Boolean UPDATED_RECIBIDA = true;

    @Inject
    private OrdenCompraRepository ordenCompraRepository;

    @Inject
    private OrdenCompraMapper ordenCompraMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrdenCompraMockMvc;

    private OrdenCompra ordenCompra;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrdenCompraResource ordenCompraResource = new OrdenCompraResource();
        ReflectionTestUtils.setField(ordenCompraResource, "ordenCompraRepository", ordenCompraRepository);
        ReflectionTestUtils.setField(ordenCompraResource, "ordenCompraMapper", ordenCompraMapper);
        this.restOrdenCompraMockMvc = MockMvcBuilders.standaloneSetup(ordenCompraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ordenCompra = new OrdenCompra();
        ordenCompra.setTicket(DEFAULT_TICKET);
        ordenCompra.setCantidad(DEFAULT_CANTIDAD);
        ordenCompra.setCosto(DEFAULT_COSTO);
        ordenCompra.setAprovada(DEFAULT_APROVADA);
        ordenCompra.setOrdenada(DEFAULT_ORDENADA);
        ordenCompra.setRecibida(DEFAULT_RECIBIDA);
    }

    @Test
    @Transactional
    public void createOrdenCompra() throws Exception {
        int databaseSizeBeforeCreate = ordenCompraRepository.findAll().size();

        // Create the OrdenCompra
        OrdenCompraDTO ordenCompraDTO = ordenCompraMapper.ordenCompraToOrdenCompraDTO(ordenCompra);

        restOrdenCompraMockMvc.perform(post("/api/ordenCompras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordenCompraDTO)))
                .andExpect(status().isCreated());

        // Validate the OrdenCompra in the database
        List<OrdenCompra> ordenCompras = ordenCompraRepository.findAll();
        assertThat(ordenCompras).hasSize(databaseSizeBeforeCreate + 1);
        OrdenCompra testOrdenCompra = ordenCompras.get(ordenCompras.size() - 1);
        assertThat(testOrdenCompra.getTicket()).isEqualTo(DEFAULT_TICKET);
        assertThat(testOrdenCompra.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testOrdenCompra.getCosto()).isEqualTo(DEFAULT_COSTO);
        assertThat(testOrdenCompra.getAprovada()).isEqualTo(DEFAULT_APROVADA);
        assertThat(testOrdenCompra.getOrdenada()).isEqualTo(DEFAULT_ORDENADA);
        assertThat(testOrdenCompra.getRecibida()).isEqualTo(DEFAULT_RECIBIDA);
    }

    @Test
    @Transactional
    public void checkCostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordenCompraRepository.findAll().size();
        // set the field null
        ordenCompra.setCosto(null);

        // Create the OrdenCompra, which fails.
        OrdenCompraDTO ordenCompraDTO = ordenCompraMapper.ordenCompraToOrdenCompraDTO(ordenCompra);

        restOrdenCompraMockMvc.perform(post("/api/ordenCompras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordenCompraDTO)))
                .andExpect(status().isBadRequest());

        List<OrdenCompra> ordenCompras = ordenCompraRepository.findAll();
        assertThat(ordenCompras).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdenCompras() throws Exception {
        // Initialize the database
        ordenCompraRepository.saveAndFlush(ordenCompra);

        // Get all the ordenCompras
        restOrdenCompraMockMvc.perform(get("/api/ordenCompras"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ordenCompra.getId().intValue())))
                .andExpect(jsonPath("$.[*].ticket").value(hasItem(DEFAULT_TICKET.intValue())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
                .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())))
                .andExpect(jsonPath("$.[*].aprovada").value(hasItem(DEFAULT_APROVADA.booleanValue())))
                .andExpect(jsonPath("$.[*].ordenada").value(hasItem(DEFAULT_ORDENADA.booleanValue())))
                .andExpect(jsonPath("$.[*].recibida").value(hasItem(DEFAULT_RECIBIDA.booleanValue())));
    }

    @Test
    @Transactional
    public void getOrdenCompra() throws Exception {
        // Initialize the database
        ordenCompraRepository.saveAndFlush(ordenCompra);

        // Get the ordenCompra
        restOrdenCompraMockMvc.perform(get("/api/ordenCompras/{id}", ordenCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ordenCompra.getId().intValue()))
            .andExpect(jsonPath("$.ticket").value(DEFAULT_TICKET.intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.doubleValue()))
            .andExpect(jsonPath("$.aprovada").value(DEFAULT_APROVADA.booleanValue()))
            .andExpect(jsonPath("$.ordenada").value(DEFAULT_ORDENADA.booleanValue()))
            .andExpect(jsonPath("$.recibida").value(DEFAULT_RECIBIDA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdenCompra() throws Exception {
        // Get the ordenCompra
        restOrdenCompraMockMvc.perform(get("/api/ordenCompras/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdenCompra() throws Exception {
        // Initialize the database
        ordenCompraRepository.saveAndFlush(ordenCompra);

		int databaseSizeBeforeUpdate = ordenCompraRepository.findAll().size();

        // Update the ordenCompra
        ordenCompra.setTicket(UPDATED_TICKET);
        ordenCompra.setCantidad(UPDATED_CANTIDAD);
        ordenCompra.setCosto(UPDATED_COSTO);
        ordenCompra.setAprovada(UPDATED_APROVADA);
        ordenCompra.setOrdenada(UPDATED_ORDENADA);
        ordenCompra.setRecibida(UPDATED_RECIBIDA);
        
        OrdenCompraDTO ordenCompraDTO = ordenCompraMapper.ordenCompraToOrdenCompraDTO(ordenCompra);

        restOrdenCompraMockMvc.perform(put("/api/ordenCompras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordenCompraDTO)))
                .andExpect(status().isOk());

        // Validate the OrdenCompra in the database
        List<OrdenCompra> ordenCompras = ordenCompraRepository.findAll();
        assertThat(ordenCompras).hasSize(databaseSizeBeforeUpdate);
        OrdenCompra testOrdenCompra = ordenCompras.get(ordenCompras.size() - 1);
        assertThat(testOrdenCompra.getTicket()).isEqualTo(UPDATED_TICKET);
        assertThat(testOrdenCompra.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testOrdenCompra.getCosto()).isEqualTo(UPDATED_COSTO);
        assertThat(testOrdenCompra.getAprovada()).isEqualTo(UPDATED_APROVADA);
        assertThat(testOrdenCompra.getOrdenada()).isEqualTo(UPDATED_ORDENADA);
        assertThat(testOrdenCompra.getRecibida()).isEqualTo(UPDATED_RECIBIDA);
    }

    @Test
    @Transactional
    public void deleteOrdenCompra() throws Exception {
        // Initialize the database
        ordenCompraRepository.saveAndFlush(ordenCompra);

		int databaseSizeBeforeDelete = ordenCompraRepository.findAll().size();

        // Get the ordenCompra
        restOrdenCompraMockMvc.perform(delete("/api/ordenCompras/{id}", ordenCompra.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrdenCompra> ordenCompras = ordenCompraRepository.findAll();
        assertThat(ordenCompras).hasSize(databaseSizeBeforeDelete - 1);
    }
}
