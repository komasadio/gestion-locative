package com.gestion.locative.web.rest;

import com.gestion.locative.GestLocativeApp;
import com.gestion.locative.domain.Locataire;
import com.gestion.locative.repository.LocataireRepository;
import com.gestion.locative.service.LocataireService;
import com.gestion.locative.service.dto.LocataireDTO;
import com.gestion.locative.service.mapper.LocataireMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LocataireResource} REST controller.
 */
@SpringBootTest(classes = GestLocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocataireResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LocataireRepository locataireRepository;

    @Autowired
    private LocataireMapper locataireMapper;

    @Autowired
    private LocataireService locataireService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocataireMockMvc;

    private Locataire locataire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locataire createEntity(EntityManager em) {
        Locataire locataire = new Locataire()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE);
        return locataire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locataire createUpdatedEntity(EntityManager em) {
        Locataire locataire = new Locataire()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE);
        return locataire;
    }

    @BeforeEach
    public void initTest() {
        locataire = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocataire() throws Exception {
        int databaseSizeBeforeCreate = locataireRepository.findAll().size();
        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);
        restLocataireMockMvc.perform(post("/api/locataires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isCreated());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeCreate + 1);
        Locataire testLocataire = locataireList.get(locataireList.size() - 1);
        assertThat(testLocataire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testLocataire.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testLocataire.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLocataire.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testLocataire.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void createLocataireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locataireRepository.findAll().size();

        // Create the Locataire with an existing ID
        locataire.setId(1L);
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocataireMockMvc.perform(post("/api/locataires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocataires() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList
        restLocataireMockMvc.perform(get("/api/locataires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locataire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())));
    }
    
    @Test
    @Transactional
    public void getLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get the locataire
        restLocataireMockMvc.perform(get("/api/locataires/{id}", locataire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locataire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingLocataire() throws Exception {
        // Get the locataire
        restLocataireMockMvc.perform(get("/api/locataires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();

        // Update the locataire
        Locataire updatedLocataire = locataireRepository.findById(locataire.getId()).get();
        // Disconnect from session so that the updates on updatedLocataire are not directly saved in db
        em.detach(updatedLocataire);
        updatedLocataire
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE);
        LocataireDTO locataireDTO = locataireMapper.toDto(updatedLocataire);

        restLocataireMockMvc.perform(put("/api/locataires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isOk());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
        Locataire testLocataire = locataireList.get(locataireList.size() - 1);
        assertThat(testLocataire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLocataire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testLocataire.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLocataire.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testLocataire.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingLocataire() throws Exception {
        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();

        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocataireMockMvc.perform(put("/api/locataires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        int databaseSizeBeforeDelete = locataireRepository.findAll().size();

        // Delete the locataire
        restLocataireMockMvc.perform(delete("/api/locataires/{id}", locataire.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
