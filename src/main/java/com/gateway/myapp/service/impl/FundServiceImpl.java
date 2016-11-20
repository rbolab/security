package com.gateway.myapp.service.impl;

import com.gateway.myapp.service.FundService;
import com.gateway.myapp.domain.Fund;
import com.gateway.myapp.repository.FundRepository;
import com.gateway.myapp.service.dto.FundDTO;
import com.gateway.myapp.service.mapper.FundMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Fund.
 */
@Service
@Transactional
public class FundServiceImpl implements FundService{

    private final Logger log = LoggerFactory.getLogger(FundServiceImpl.class);

    @Inject
    private FundRepository fundRepository;

    @Inject
    private FundMapper fundMapper;

    /**
     * Save a fund.
     *
     * @param fundDTO the entity to save
     * @return the persisted entity
     */
    public FundDTO save(FundDTO fundDTO) {
        log.debug("Request to save Fund : {}", fundDTO);
        Fund fund = fundMapper.fundDTOToFund(fundDTO);
        fund = fundRepository.save(fund);
        FundDTO result = fundMapper.fundToFundDTO(fund);
        return result;
    }

    /**
     *  Get all the funds.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FundDTO> findAll() {
        log.debug("Request to get all Funds");
        List<FundDTO> result = fundRepository.findAll().stream()
            .map(fundMapper::fundToFundDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one fund by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FundDTO findOne(Long id) {
        log.debug("Request to get Fund : {}", id);
        Fund fund = fundRepository.findOne(id);
        FundDTO fundDTO = fundMapper.fundToFundDTO(fund);
        return fundDTO;
    }

    /**
     *  Delete the  fund by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fund : {}", id);
        fundRepository.delete(id);
    }
}
