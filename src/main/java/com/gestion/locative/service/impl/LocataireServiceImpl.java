package com.gestion.locative.service.impl;

import com.gestion.locative.service.LocataireService;
import com.gestion.locative.domain.Locataire;
import com.gestion.locative.repository.LocataireRepository;
import com.gestion.locative.service.dto.LocataireDTO;
import com.gestion.locative.service.mapper.LocataireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Locataire}.
 */
@Service
@Transactional
public class LocataireServiceImpl implements LocataireService {

    private final Logger log = LoggerFactory.getLogger(LocataireServiceImpl.class);

    private final LocataireRepository locataireRepository;

    private final LocataireMapper locataireMapper;

    public LocataireServiceImpl(LocataireRepository locataireRepository, LocataireMapper locataireMapper) {
        this.locataireRepository = locataireRepository;
        this.locataireMapper = locataireMapper;
    }

    @Override
    public LocataireDTO save(LocataireDTO locataireDTO) {
        log.debug("Request to save Locataire : {}", locataireDTO);
        Locataire locataire = locataireMapper.toEntity(locataireDTO);
        locataire = locataireRepository.save(locataire);
        return locataireMapper.toDto(locataire);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocataireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Locataires");
        return locataireRepository.findAll(pageable)
            .map(locataireMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LocataireDTO> findOne(Long id) {
        log.debug("Request to get Locataire : {}", id);
        return locataireRepository.findById(id)
            .map(locataireMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Locataire : {}", id);
        locataireRepository.deleteById(id);
    }
}
