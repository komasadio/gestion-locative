package com.gestion.locative.web.rest;

import com.gestion.locative.GestLocativeApp;
import com.gestion.locative.domain.Contrat;
import com.gestion.locative.repository.ContratRepository;
import com.gestion.locative.service.ContratService;
import com.gestion.locative.service.dto.ContratDTO;
import com.gestion.locative.service.mapper.ContratMapper;

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

import com.gestion.locative.domain.enumeration.Periode;
/**
 * Integration tests for the {@link ContratResource} REST controller.
 */
@SpringBootTest(classes = GestLocativeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContratResourceIT {

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_MONTANT_LOYER_NU = 1D;
    private static final Double UPDATED_MONTANT_LOYER_NU = 2D;

    private static final Double DEFAULT_MONTANT_CHARGES = 1D;
    private static final Double UPDATED_MONTANT_CHARGES = 2D;

    private static final Periode DEFAULT_PERIODICITE_LOYER = Periode.Mensuel;
    private static final Periode UPDATED_PERIODICITE_LOYER = Periode.Annuel;

    private static final Double DEFAULT_MONTANT_DEPOT_GARANTIE = 1D;
    private static final Double UPDATED_MONTANT_DEPOT_GARANTIE = 2D;

    private static final String DEFAULT_INFOS_COMPLEMENTAIRES = "AAAAAAAAAA";
    private static final String UPDATED_INFOS_COMPLEMENTAIRES = "BBBBBBBBBB";

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private ContratMapper contratMapper;

    @Autowired
    private ContratService contratService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContratMockMvc;

    private Contrat contrat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrat createEntity(EntityManager em) {
        Contrat contrat = new Contrat()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .montantLoyerNu(DEFAULT_MONTANT_LOYER_NU)
            .montantCharges(DEFAULT_MONTANT_CHARGES)
            .periodiciteLoyer(DEFAULT_PERIODICITE_LOYER)
            .montantDepotGarantie(DEFAULT_MONTANT_DEPOT_GARANTIE)
            .infosComplementaires(DEFAULT_INFOS_COMPLEMENTAIRES);
        return contrat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrat createUpdatedEntity(EntityManager em) {
        Contrat contrat = new Contrat()
            .dateDebut(UPDATED_DATE_DEBUT)
            .montantLoyerNu(UPDATED_MONTANT_LOYER_NU)
            .montantCharges(UPDATED_MONTANT_CHARGES)
            .periodiciteLoyer(UPDATED_PERIODICITE_LOYER)
            .montantDepotGarantie(UPDATED_MONTANT_DEPOT_GARANTIE)
            .infosComplementaires(UPDATED_INFOS_COMPLEMENTAIRES);
        return contrat;
    }

    @BeforeEach
    public void initTest() {
        contrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrat() throws Exception {
        int databaseSizeBeforeCreate = contratRepository.findAll().size();
        // Create the Contrat
        ContratDTO contratDTO = contratMapper.toDto(contrat);
        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contratDTO)))
            .andExpect(status().isCreated());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeCreate + 1);
        Contrat testContrat = contratList.get(contratList.size() - 1);
        assertThat(testContrat.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testContrat.getMontantLoyerNu()).isEqualTo(DEFAULT_MONTANT_LOYER_NU);
        assertThat(testContrat.getMontantCharges()).isEqualTo(DEFAULT_MONTANT_CHARGES);
        assertThat(testContrat.getPeriodiciteLoyer()).isEqualTo(DEFAULT_PERIODICITE_LOYER);
        assertThat(testContrat.getMontantDepotGarantie()).isEqualTo(DEFAULT_MONTANT_DEPOT_GARANTIE);
        assertThat(testContrat.getInfosComplementaires()).isEqualTo(DEFAULT_INFOS_COMPLEMENTAIRES);
    }

    @Test
    @Transactional
    public void createContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratRepository.findAll().size();

        // Create the Contrat with an existing ID
        contrat.setId(1L);
        ContratDTO contratDTO = contratMapper.toDto(contrat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contratDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContrats() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList
        restContratMockMvc.perform(get("/api/contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].montantLoyerNu").value(hasItem(DEFAULT_MONTANT_LOYER_NU.doubleValue())))
            .andExpect(jsonPath("$.[*].montantCharges").value(hasItem(DEFAULT_MONTANT_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].periodiciteLoyer").value(hasItem(DEFAULT_PERIODICITE_LOYER.toString())))
            .andExpect(jsonPath("$.[*].montantDepotGarantie").value(hasItem(DEFAULT_MONTANT_DEPOT_GARANTIE.doubleValue())))
            .andExpect(jsonPath("$.[*].infosComplementaires").value(hasItem(DEFAULT_INFOS_COMPLEMENTAIRES)));
    }
    
    @Test
    @Transactional
    public void getContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get the contrat
        restContratMockMvc.perform(get("/api/contrats/{id}", contrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contrat.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.montantLoyerNu").value(DEFAULT_MONTANT_LOYER_NU.doubleValue()))
            .andExpect(jsonPath("$.montantCharges").value(DEFAULT_MONTANT_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.periodiciteLoyer").value(DEFAULT_PERIODICITE_LOYER.toString()))
            .andExpect(jsonPath("$.montantDepotGarantie").value(DEFAULT_MONTANT_DEPOT_GARANTIE.doubleValue()))
            .andExpect(jsonPath("$.infosComplementaires").value(DEFAULT_INFOS_COMPLEMENTAIRES));
    }
    @Test
    @Transactional
    public void getNonExistingContrat() throws Exception {
        // Get the contrat
        restContratMockMvc.perform(get("/api/contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        int databaseSizeBeforeUpdate = contratRepository.findAll().size();

        // Update the contrat
        Contrat updatedContrat = contratRepository.findById(contrat.getId()).get();
        // Disconnect from session so that the updates on updatedContrat are not directly saved in db
        em.detach(updatedContrat);
        updatedContrat
            .dateDebut(UPDATED_DATE_DEBUT)
            .montantLoyerNu(UPDATED_MONTANT_LOYER_NU)
            .montantCharges(UPDATED_MONTANT_CHARGES)
            .periodiciteLoyer(UPDATED_PERIODICITE_LOYER)
            .montantDepotGarantie(UPDATED_MONTANT_DEPOT_GARANTIE)
            .infosComplementaires(UPDATED_INFOS_COMPLEMENTAIRES);
        ContratDTO contratDTO = contratMapper.toDto(updatedContrat);

        restContratMockMvc.perform(put("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contratDTO)))
            .andExpect(status().isOk());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeUpdate);
        Contrat testContrat = contratList.get(contratList.size() - 1);
        assertThat(testContrat.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testContrat.getMontantLoyerNu()).isEqualTo(UPDATED_MONTANT_LOYER_NU);
        assertThat(testContrat.getMontantCharges()).isEqualTo(UPDATED_MONTANT_CHARGES);
        assertThat(testContrat.getPeriodiciteLoyer()).isEqualTo(UPDATED_PERIODICITE_LOYER);
        assertThat(testContrat.getMontantDepotGarantie()).isEqualTo(UPDATED_MONTANT_DEPOT_GARANTIE);
        assertThat(testContrat.getInfosComplementaires()).isEqualTo(UPDATED_INFOS_COMPLEMENTAIRES);
    }

    @Test
    @Transactional
    public void updateNonExistingContrat() throws Exception {
        int databaseSizeBeforeUpdate = contratRepository.findAll().size();

        // Create the Contrat
        ContratDTO contratDTO = contratMapper.toDto(contrat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratMockMvc.perform(put("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contratDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        int databaseSizeBeforeDelete = contratRepository.findAll().size();

        // Delete the contrat
        restContratMockMvc.perform(delete("/api/contrats/{id}", contrat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
