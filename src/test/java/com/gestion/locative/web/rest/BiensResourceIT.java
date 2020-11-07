package com.gestion.locative.web.rest;

import com.gestion.locative.GestLocativeApp;
import com.gestion.locative.domain.Biens;
import com.gestion.locative.repository.BiensRepository;
import com.gestion.locative.service.BiensService;
import com.gestion.locative.service.dto.BiensDTO;
import com.gestion.locative.service.mapper.BiensMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gestion.locative.domain.enumeration.TypeBien;
/**
 * Integration tests for the {@link BiensResource} REST controller.
 */
@SpringBootTest(classes = GestLocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BiensResourceIT {

    private static final Boolean DEFAULT_EST_MEUBLE = false;
    private static final Boolean UPDATED_EST_MEUBLE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TypeBien DEFAULT_TYPE = TypeBien.Appartement;
    private static final TypeBien UPDATED_TYPE = TypeBien.Immeuble;

    private static final Double DEFAULT_SURFACE = 1D;
    private static final Double UPDATED_SURFACE = 2D;

    @Autowired
    private BiensRepository biensRepository;

    @Autowired
    private BiensMapper biensMapper;

    @Autowired
    private BiensService biensService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBiensMockMvc;

    private Biens biens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biens createEntity(EntityManager em) {
        Biens biens = new Biens()
            .estMeuble(DEFAULT_EST_MEUBLE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .surface(DEFAULT_SURFACE);
        return biens;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biens createUpdatedEntity(EntityManager em) {
        Biens biens = new Biens()
            .estMeuble(UPDATED_EST_MEUBLE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .surface(UPDATED_SURFACE);
        return biens;
    }

    @BeforeEach
    public void initTest() {
        biens = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiens() throws Exception {
        int databaseSizeBeforeCreate = biensRepository.findAll().size();
        // Create the Biens
        BiensDTO biensDTO = biensMapper.toDto(biens);
        restBiensMockMvc.perform(post("/api/biens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biensDTO)))
            .andExpect(status().isCreated());

        // Validate the Biens in the database
        List<Biens> biensList = biensRepository.findAll();
        assertThat(biensList).hasSize(databaseSizeBeforeCreate + 1);
        Biens testBiens = biensList.get(biensList.size() - 1);
        assertThat(testBiens.isEstMeuble()).isEqualTo(DEFAULT_EST_MEUBLE);
        assertThat(testBiens.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBiens.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBiens.getSurface()).isEqualTo(DEFAULT_SURFACE);
    }

    @Test
    @Transactional
    public void createBiensWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biensRepository.findAll().size();

        // Create the Biens with an existing ID
        biens.setId(1L);
        BiensDTO biensDTO = biensMapper.toDto(biens);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiensMockMvc.perform(post("/api/biens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biensDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Biens in the database
        List<Biens> biensList = biensRepository.findAll();
        assertThat(biensList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBiens() throws Exception {
        // Initialize the database
        biensRepository.saveAndFlush(biens);

        // Get all the biensList
        restBiensMockMvc.perform(get("/api/biens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biens.getId().intValue())))
            .andExpect(jsonPath("$.[*].estMeuble").value(hasItem(DEFAULT_EST_MEUBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getBiens() throws Exception {
        // Initialize the database
        biensRepository.saveAndFlush(biens);

        // Get the biens
        restBiensMockMvc.perform(get("/api/biens/{id}", biens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(biens.getId().intValue()))
            .andExpect(jsonPath("$.estMeuble").value(DEFAULT_EST_MEUBLE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBiens() throws Exception {
        // Get the biens
        restBiensMockMvc.perform(get("/api/biens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiens() throws Exception {
        // Initialize the database
        biensRepository.saveAndFlush(biens);

        int databaseSizeBeforeUpdate = biensRepository.findAll().size();

        // Update the biens
        Biens updatedBiens = biensRepository.findById(biens.getId()).get();
        // Disconnect from session so that the updates on updatedBiens are not directly saved in db
        em.detach(updatedBiens);
        updatedBiens
            .estMeuble(UPDATED_EST_MEUBLE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .surface(UPDATED_SURFACE);
        BiensDTO biensDTO = biensMapper.toDto(updatedBiens);

        restBiensMockMvc.perform(put("/api/biens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biensDTO)))
            .andExpect(status().isOk());

        // Validate the Biens in the database
        List<Biens> biensList = biensRepository.findAll();
        assertThat(biensList).hasSize(databaseSizeBeforeUpdate);
        Biens testBiens = biensList.get(biensList.size() - 1);
        assertThat(testBiens.isEstMeuble()).isEqualTo(UPDATED_EST_MEUBLE);
        assertThat(testBiens.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBiens.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBiens.getSurface()).isEqualTo(UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void updateNonExistingBiens() throws Exception {
        int databaseSizeBeforeUpdate = biensRepository.findAll().size();

        // Create the Biens
        BiensDTO biensDTO = biensMapper.toDto(biens);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBiensMockMvc.perform(put("/api/biens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biensDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Biens in the database
        List<Biens> biensList = biensRepository.findAll();
        assertThat(biensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBiens() throws Exception {
        // Initialize the database
        biensRepository.saveAndFlush(biens);

        int databaseSizeBeforeDelete = biensRepository.findAll().size();

        // Delete the biens
        restBiensMockMvc.perform(delete("/api/biens/{id}", biens.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Biens> biensList = biensRepository.findAll();
        assertThat(biensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
