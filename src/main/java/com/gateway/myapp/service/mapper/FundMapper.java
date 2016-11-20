package com.gateway.myapp.service.mapper;

import com.gateway.myapp.domain.*;
import com.gateway.myapp.service.dto.FundDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Fund and its DTO FundDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FundMapper {

    FundDTO fundToFundDTO(Fund fund);

    List<FundDTO> fundsToFundDTOs(List<Fund> funds);

    Fund fundDTOToFund(FundDTO fundDTO);

    List<Fund> fundDTOsToFunds(List<FundDTO> fundDTOs);
}
