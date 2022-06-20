package com.term.movie.repository;

import com.term.movie.domain.SalePolicy;
import com.term.movie.dto.SalePolicyDTO;

import java.util.List;
import java.util.Optional;

public interface SalePolicyCustomRepository {
    List<SalePolicyDTO.ReadSalePolicyDTO> readSalePolicy();
    
    // 정액
    SalePolicy findById(Long salePolicyId);

    SalePolicy findBySalePolicyName(String salePolicyName);

}
