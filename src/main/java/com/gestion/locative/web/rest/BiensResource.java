package com.gestion.locative.web.rest;

import com.gestion.locative.service.BiensService;
import com.gestion.locative.web.rest.errors.BadRequestAlertException;
import com.gestion.locative.service.dto.BiensDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gestion.locative.domain.Biens}.
 */
@RestController
@RequestMapping("/api")
public class BiensResource {

    private final Logger log = LoggerFactory.getLogger(BiensResource.class);

    private static final String ENTITY_NAME = "biens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BiensService biensService;

    public BiensResource(BiensService biensService) {
        this.biensService = biensService;
    }

    /**
     * {@code POST  /biens} : Create a new biens.
     *
     * @param biensDTO the biensDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new biensDTO, or with status {@code 400 (Bad Request)} if the biens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/biens")
    public ResponseEntity<BiensDTO> createBiens(@RequestBody BiensDTO biensDTO) throws URISyntaxException {
        log.debug("REST request to save Biens : {}", biensDTO);
        if (biensDTO.getId() != null) {
            throw new BadRequestAlertException("A new biens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BiensDTO result = biensService.save(biensDTO);
        return ResponseEntity.created(new URI("/api/biens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /biens} : Updates an existing biens.
     *
     * @param biensDTO the biensDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biensDTO,
     * or with status {@code 400 (Bad Request)} if the biensDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the biensDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/biens")
    public ResponseEntity<BiensDTO> updateBiens(@RequestBody BiensDTO biensDTO) throws URISyntaxException {
        log.debug("REST request to update Biens : {}", biensDTO);
        if (biensDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BiensDTO result = biensService.save(biensDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biensDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /biens} : get all the biens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of biens in body.
     */
    @GetMapping("/biens")
    public ResponseEntity<List<BiensDTO>> getAllBiens(Pageable pageable) {
        log.debug("REST request to get a page of Biens");
        Page<BiensDTO> page = biensService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /biens/:id} : get the "id" biens.
     *
     * @param id the id of the biensDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the biensDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/biens/{id}")
    public ResponseEntity<BiensDTO> getBiens(@PathVariable Long id) {
        log.debug("REST request to get Biens : {}", id);
        Optional<BiensDTO> biensDTO = biensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(biensDTO);
    }

    /**
     * {@code DELETE  /biens/:id} : delete the "id" biens.
     *
     * @param id the id of the biensDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/biens/{id}")
    public ResponseEntity<Void> deleteBiens(@PathVariable Long id) {
        log.debug("REST request to delete Biens : {}", id);
        biensService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
