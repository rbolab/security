package com.gateway.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Fund entity.
 */
public class FundDTO implements Serializable {

    private Long id;

    private String name;

    private String fees;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FundDTO fundDTO = (FundDTO) o;

        if ( ! Objects.equals(id, fundDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FundDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", fees='" + fees + "'" +
            '}';
    }
}