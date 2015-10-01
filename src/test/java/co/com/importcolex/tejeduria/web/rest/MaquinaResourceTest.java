package co.com.importcolex.tejeduria.web.rest;

import co.com.importcolex.tejeduria.Application;
import co.com.importcolex.tejeduria.domain.Maquina;
import co.com.importcolex.tejeduria.repository.MaquinaRepository;
import co.com.importcolex.tejeduria.web.rest.dto.MaquinaDTO;
import co.com.importcolex.tejeduria.web.rest.mapper.MaquinaMapper;

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
 * Test class for the MaquinaResource REST controller.
 *
 * @see MaquinaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MaquinaResourceTest {

    private static final String DEFAULT_MARCA = "SAMPLE_TEXT";
    private static final String UPDATED_MARCA = "UPDATED_TEXT";

    private static final Integer DEFAULT_GALGA = 1;
    private static final Integer UPDATED_GALGA = 2;

    private static final Integer DEFAULT_DIAMETRO = 1;
    private static final Integer UPDATED_DIAMETRO = 2;

    private static final Integer DEFAULT_RPM = 1;
    private static final Integer UPDATED_RPM = 2;

    private static final Integer DEFAULT_AGUJAS = 1;
    private static final Integer UPDATED_AGUJAS = 2;

    @Inject
    private MaquinaRepository maquinaRepository;

    @Inject
    private MaquinaMapper maquinaMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMaquinaMockMvc;

    private Maquina maquina;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MaquinaResource maquinaResource = new MaquinaResource();
        ReflectionTestUtils.setField(maquinaResource, "maquinaRepository", maquinaRepository);
        ReflectionTestUtils.setField(maquinaResource, "maquinaMapper", maquinaMapper);
        this.restMaquinaMockMvc = MockMvcBuilders.standaloneSetup(maquinaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        maquina = new Maquina();
        maquina.setMarca(DEFAULT_MARCA);
        maquina.setGalga(DEFAULT_GALGA);
        maquina.setDiametro(DEFAULT_DIAMETRO);
        maquina.setRpm(DEFAULT_RPM);
        maquina.setAgujas(DEFAULT_AGUJAS);
    }

    @Test
    @Transactional
    public void createMaquina() throws Exception {
        int databaseSizeBeforeCreate = maquinaRepository.findAll().size();

        // Create the Maquina
        MaquinaDTO maquinaDTO = maquinaMapper.maquinaToMaquinaDTO(maquina);

        restMaquinaMockMvc.perform(post("/api/maquinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maquinaDTO)))
                .andExpect(status().isCreated());

        // Validate the Maquina in the database
        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(databaseSizeBeforeCreate + 1);
        Maquina testMaquina = maquinas.get(maquinas.size() - 1);
        assertThat(testMaquina.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testMaquina.getGalga()).isEqualTo(DEFAULT_GALGA);
        assertThat(testMaquina.getDiametro()).isEqualTo(DEFAULT_DIAMETRO);
        assertThat(testMaquina.getRpm()).isEqualTo(DEFAULT_RPM);
        assertThat(testMaquina.getAgujas()).isEqualTo(DEFAULT_AGUJAS);
    }

    @Test
    @Transactional
    public void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = maquinaRepository.findAll().size();
        // set the field null
        maquina.setMarca(null);

        // Create the Maquina, which fails.
        MaquinaDTO maquinaDTO = maquinaMapper.maquinaToMaquinaDTO(maquina);

        restMaquinaMockMvc.perform(post("/api/maquinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maquinaDTO)))
                .andExpect(status().isBadRequest());

        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGalgaIsRequired() throws Exception {
        int databaseSizeBeforeTest = maquinaRepository.findAll().size();
        // set the field null
        maquina.setGalga(null);

        // Create the Maquina, which fails.
        MaquinaDTO maquinaDTO = maquinaMapper.maquinaToMaquinaDTO(maquina);

        restMaquinaMockMvc.perform(post("/api/maquinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maquinaDTO)))
                .andExpect(status().isBadRequest());

        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaquinas() throws Exception {
        // Initialize the database
        maquinaRepository.saveAndFlush(maquina);

        // Get all the maquinas
        restMaquinaMockMvc.perform(get("/api/maquinas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(maquina.getId().intValue())))
                .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
                .andExpect(jsonPath("$.[*].galga").value(hasItem(DEFAULT_GALGA)))
                .andExpect(jsonPath("$.[*].diametro").value(hasItem(DEFAULT_DIAMETRO)))
                .andExpect(jsonPath("$.[*].rpm").value(hasItem(DEFAULT_RPM)))
                .andExpect(jsonPath("$.[*].agujas").value(hasItem(DEFAULT_AGUJAS)));
    }

    @Test
    @Transactional
    public void getMaquina() throws Exception {
        // Initialize the database
        maquinaRepository.saveAndFlush(maquina);

        // Get the maquina
        restMaquinaMockMvc.perform(get("/api/maquinas/{id}", maquina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(maquina.getId().intValue()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.galga").value(DEFAULT_GALGA))
            .andExpect(jsonPath("$.diametro").value(DEFAULT_DIAMETRO))
            .andExpect(jsonPath("$.rpm").value(DEFAULT_RPM))
            .andExpect(jsonPath("$.agujas").value(DEFAULT_AGUJAS));
    }

    @Test
    @Transactional
    public void getNonExistingMaquina() throws Exception {
        // Get the maquina
        restMaquinaMockMvc.perform(get("/api/maquinas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaquina() throws Exception {
        // Initialize the database
        maquinaRepository.saveAndFlush(maquina);

		int databaseSizeBeforeUpdate = maquinaRepository.findAll().size();

        // Update the maquina
        maquina.setMarca(UPDATED_MARCA);
        maquina.setGalga(UPDATED_GALGA);
        maquina.setDiametro(UPDATED_DIAMETRO);
        maquina.setRpm(UPDATED_RPM);
        maquina.setAgujas(UPDATED_AGUJAS);
        
        MaquinaDTO maquinaDTO = maquinaMapper.maquinaToMaquinaDTO(maquina);

        restMaquinaMockMvc.perform(put("/api/maquinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maquinaDTO)))
                .andExpect(status().isOk());

        // Validate the Maquina in the database
        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(databaseSizeBeforeUpdate);
        Maquina testMaquina = maquinas.get(maquinas.size() - 1);
        assertThat(testMaquina.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testMaquina.getGalga()).isEqualTo(UPDATED_GALGA);
        assertThat(testMaquina.getDiametro()).isEqualTo(UPDATED_DIAMETRO);
        assertThat(testMaquina.getRpm()).isEqualTo(UPDATED_RPM);
        assertThat(testMaquina.getAgujas()).isEqualTo(UPDATED_AGUJAS);
    }

    @Test
    @Transactional
    public void deleteMaquina() throws Exception {
        // Initialize the database
        maquinaRepository.saveAndFlush(maquina);

		int databaseSizeBeforeDelete = maquinaRepository.findAll().size();

        // Get the maquina
        restMaquinaMockMvc.perform(delete("/api/maquinas/{id}", maquina.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
