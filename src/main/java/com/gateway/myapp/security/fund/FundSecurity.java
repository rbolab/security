package com.gateway.myapp.security.fund;


import com.gateway.myapp.service.dto.FundDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.beans.FeatureDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;


@Component
public class FundSecurity {

    public boolean displayFilter(Authentication authentication, List<FundDTO> funds){
        HashMap<Long, FundDTO> authorizedFunds = getAuthorizedFunds(authentication);

        for (FundDTO fund: funds) {
            FundDTO authFundDTO = authorizedFunds.get(fund.getId());
            if(authFundDTO != null){
                BeanUtils.copyProperties(authFundDTO, fund, getNullPropertyNames(authFundDTO));
            }else {
                funds.remove(fund);
            }
        }
        return true;
    }

    private HashMap<Long, FundDTO> getAuthorizedFunds(Authentication authentication){
        HashMap<Long, FundDTO> authorized = new HashMap<>();
        FundDTO fund1 = new FundDTO();
        fund1.setName("Custom Fund Name");
        authorized.put(Long.valueOf(1), fund1);
        authorized.put(Long.valueOf(2), new FundDTO());

        FundDTO fund3 = new FundDTO();
        fund3.setFees("Custom Fees");
        authorized.put(Long.valueOf(3), fund3);
        return authorized;
    }

    private String[] getNullPropertyNames(Object bean) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(bean);
        return Stream.of(wrappedSource.getPropertyDescriptors())
            .map(FeatureDescriptor::getName)
            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
            .toArray(String[]::new);
    }

}
