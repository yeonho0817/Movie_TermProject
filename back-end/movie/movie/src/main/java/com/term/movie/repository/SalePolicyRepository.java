package com.term.movie.repository;

import com.term.movie.domain.SalePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalePolicyRepository extends JpaRepository<SalePolicy, Long> {

}
