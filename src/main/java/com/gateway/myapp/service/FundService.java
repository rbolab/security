package com.gateway.myapp.service;

import com.gateway.myapp.service.dto.FundDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Fund.
 */
public interface FundService {

    /**
     * Save a fund.
     *
     * @param fundDTO the entity to save
     * @return the persisted entity
     */
    FundDTO save(FundDTO fundDTO);

    /**
     *  Get all the funds.
     *  
     *  @return the list of entities
     */
    List<FundDTO> findAll();

    /**
     *  Get the "id" fund.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FundDTO findOne(Long id);

    /**
     *  Delete the "id" fund.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
