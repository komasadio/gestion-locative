package com.gestion.locative.service.impl;

import com.gestion.locative.service.BiensService;
import com.gestion.locative.domain.Biens;
import com.gestion.locative.repository.BiensRepository;
import com.gestion.locative.service.dto.BiensDTO;
import com.gestion.locative.service.mapper.BiensMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Biens}.
 */
@Service
@Transactional
public class BiensServiceImpl implements BiensService {

    private final Logger log = LoggerFactory.getLogger(BiensServiceImpl.class);

    private final BiensRepository biensRepository;

    private final BiensMapper biensMapper;

    public BiensServiceImpl(BiensRepository biensRepository, BiensMapper biensMapper) {
        this.biensRepository = biensRepository;
        this.biensMapper = biensMapper;
    }

    @Override
    public BiensDTO save(BiensDTO biensDTO) {
        log.debug("Request to save Biens : {}", biensDTO);
        Biens biens = biensMapper.toEntity(biensDTO);
        biens = biensRepository.save(biens);
        return biensMapper.toDto(biens);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BiensDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Biens");
        return biensRepository.findAll(pageable)
            .map(biensMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BiensDTO> findOne(Long id) {
        log.debug("Request to get Biens : {}", id);
        return biensRepository.findById(id)
            .map(biensMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Biens : {}", id);
        biensRepository.deleteById(id);
    }
}
