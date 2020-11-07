package com.gestion.locative.service.mapper;


import com.gestion.locative.domain.*;
import com.gestion.locative.service.dto.BiensDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Biens} and its DTO {@link BiensDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdresseMapper.class})
public interface BiensMapper extends EntityMapper<BiensDTO, Biens> {

    @Mapping(source = "adresse.id", target = "adresseId")
    BiensDTO toDto(Biens biens);

    @Mapping(source = "adresseId", target = "adresse")
    Biens toEntity(BiensDTO biensDTO);

    default Biens fromId(Long id) {
        if (id == null) {
            return null;
        }
        Biens biens = new Biens();
        biens.setId(id);
        return biens;
    }
}
