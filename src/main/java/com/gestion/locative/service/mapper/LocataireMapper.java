package com.gestion.locative.service.mapper;


import com.gestion.locative.domain.*;
import com.gestion.locative.service.dto.LocataireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Locataire} and its DTO {@link LocataireDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdresseMapper.class})
public interface LocataireMapper extends EntityMapper<LocataireDTO, Locataire> {

    @Mapping(source = "adresse.id", target = "adresseId")
    LocataireDTO toDto(Locataire locataire);

    @Mapping(source = "adresseId", target = "adresse")
    Locataire toEntity(LocataireDTO locataireDTO);

    default Locataire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Locataire locataire = new Locataire();
        locataire.setId(id);
        return locataire;
    }
}
