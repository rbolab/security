package com.gateway.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gateway.myapp.service.FundService;
import com.gateway.myapp.service.dto.FundDTO;
import com.gateway.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Fund.
 */
@RestController
@RequestMapping("/api")
public class FundResource {

    private final Logger log = LoggerFactory.getLogger(FundResource.class);

    @Inject
    private FundService fundService;

    /**
     * POST  /funds : Create a new fund.
     *
     * @param fundDTO the fundDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fundDTO, or with status 400 (Bad Request) if the fund has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/funds")
    @Timed
    public ResponseEntity<FundDTO> createFund(@RequestBody FundDTO fundDTO) throws URISyntaxException {
        log.debug("REST request to save Fund : {}", fundDTO);
        if (fundDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fund", "idexists", "A new fund cannot already have an ID")).body(null);
        }
        FundDTO result = fundService.save(fundDTO);
        return ResponseEntity.created(new URI("/api/funds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fund", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /funds : Updates an existing fund.
     *
     * @param fundDTO the fundDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fundDTO,
     * or with status 400 (Bad Request) if the fundDTO is not valid,
     * or with status 500 (Internal Server Error) if the fundDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/funds")
    @Timed
    public ResponseEntity<FundDTO> updateFund(@RequestBody FundDTO fundDTO) throws URISyntaxException {
        log.debug("REST request to update Fund : {}", fundDTO);
        if (fundDTO.getId() == null) {
            return createFund(fundDTO);
        }
        FundDTO result = fundService.save(fundDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fund", fundDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /funds : get all the funds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of funds in body
     */
    @GetMapping("/funds")
    @Timed
    @PostAuthorize("@fundSecurity.displayFilter(authentication, returnObject)")
    public List<FundDTO> getAllFunds() {
        log.debug("REST request to get all Funds");
        return fundService.findAll();
    }

    /**
     * GET  /funds/:id : get the "id" fund.
     *
     * @param id the id of the fundDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fundDTO, or with status 404 (Not Found)
     */
    @GetMapping("/funds/{id}")
    @Timed
    public ResponseEntity<FundDTO> getFund(@PathVariable Long id) {
        log.debug("REST request to get Fund : {}", id);
        FundDTO fundDTO = fundService.findOne(id);
        return Optional.ofNullable(fundDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /funds/:id : delete the "id" fund.
     *
     * @param id the id of the fundDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/funds/{id}")
    @Timed
    public ResponseEntity<Void> deleteFund(@PathVariable Long id) {
        log.debug("REST request to delete Fund : {}", id);
        fundService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fund", id.toString())).build();
    }

}
