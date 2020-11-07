package com.gestion.locative.service.mapper;


import com.gestion.locative.domain.*;
import com.gestion.locative.service.dto.AdresseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adresse} and its DTO {@link AdresseDTO}.
 */
@Mapper(componentModel = "spring", uses = {PaysMapper.class})
public interface AdresseMapper extends EntityMapper<AdresseDTO, Adresse> {

    @Mapping(source = "pays.id", target = "paysId")
    AdresseDTO toDto(Adresse adresse);

    @Mapping(source = "paysId", target = "pays")
    Adresse toEntity(AdresseDTO adresseDTO);

    default Adresse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adresse adresse = new Adresse();
        adresse.setId(id);
        return adresse;
    }
}
