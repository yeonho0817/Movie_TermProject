package com.term.movie.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.term.movie.domain.QSalePolicy;
import com.term.movie.domain.SalePolicy;
import com.term.movie.dto.SalePolicyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SalePolicyCustomRepositoryImpl implements SalePolicyCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SalePolicyDTO.ReadSalePolicyDTO> readSalePolicy() {
        QSalePolicy qSalePolicy = QSalePolicy.salePolicy;


        return jpaQueryFactory.select(Projections.fields(SalePolicyDTO.ReadSalePolicyDTO.class,
                    qSalePolicy.id.as("salePolicyId"),
                    qSalePolicy.salePolicyName.as("salePolicyName"),
                    qSalePolicy.value.as("value")
                ))

                .from(qSalePolicy)

                .fetch();
    }

    @Override
    public SalePolicy findById(Long salePolicyId) {
        QSalePolicy qSalePolicy = QSalePolicy.salePolicy;

        return jpaQueryFactory.selectFrom(qSalePolicy)

                .where(qSalePolicy.id.eq(salePolicyId))

                .fetchOne();
    }

    @Override
    public SalePolicy findBySalePolicyName(String salePolicyName) {
        QSalePolicy qSalePolicy = QSalePolicy.salePolicy;

        return jpaQueryFactory.selectFrom(qSalePolicy)

                .where(qSalePolicy.salePolicyName.stringValue().eq(salePolicyName))

                .fetchOne();
    }


}
