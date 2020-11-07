package com.gestion.locative.service.mapper;


import com.gestion.locative.domain.*;
import com.gestion.locative.service.dto.ContratDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contrat} and its DTO {@link ContratDTO}.
 */
@Mapper(componentModel = "spring", uses = {BiensMapper.class, LocataireMapper.class})
public interface ContratMapper extends EntityMapper<ContratDTO, Contrat> {

    @Mapping(source = "bien.id", target = "bienId")
    @Mapping(source = "locataire.id", target = "locataireId")
    ContratDTO toDto(Contrat contrat);

    @Mapping(source = "bienId", target = "bien")
    @Mapping(source = "locataireId", target = "locataire")
    Contrat toEntity(ContratDTO contratDTO);

    default Contrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contrat contrat = new Contrat();
        contrat.setId(id);
        return contrat;
    }
}
