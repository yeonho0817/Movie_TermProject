package com.term.movie.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.term.movie.domain.*;
import com.term.movie.dto.RatingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RatingCustomRepositoryImpl implements RatingCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RatingDTO.ReadRatingDTO> readRatingDTO(Long movieId) {
        QRating qRating = QRating.rating;
        QTicketing qTicketing = QTicketing.ticketing;

        QMovie qMovie = QMovie.movie;
        QMember qMember = QMember.member;

        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;


        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qRating.registerDatetime
                , ConstantImpl.create("%Y-%m-%d"));

        return jpaQueryFactory.selectDistinct(Projections.fields(RatingDTO.ReadRatingDTO.class,
                                qRating.id,
                                qMember.id.as("memberPK"),
                                qMember.memberId,
                                qRating.ratingPoint,
                                qRating.contents,
                                qRating.likesNumber,
                                formattedDate.as("registerDatetime")
                            ))
                .from(qRating)

                .leftJoin(qRating.ticketing(), qTicketing)
                .on(qRating.ticketing().id.eq(qTicketing.id))

                .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                .leftJoin(qScreeningSchedule.movie(), qMovie)
                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                .leftJoin(qTicketing.member(), qMember)
                .on(qTicketing.member().id.eq(qMember.id))

                .where(qMovie.id.eq(movieId))

                .fetch();
    }

    @Override
    public RatingDTO.ReadRatingDTO readOneRatingDTO(Long ratingId) {
        QRating qRating = QRating.rating;
        QTicketing qTicketing = QTicketing.ticketing;

        QMovie qMovie = QMovie.movie;
        QMember qMember = QMember.member;

        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qRating.registerDatetime
                , ConstantImpl.create("%Y-%m-%d"));

        return jpaQueryFactory.selectDistinct(Projections.fields(RatingDTO.ReadRatingDTO.class,
                        qRating.id,
                        qMember.memberId,
                        qRating.ratingPoint,
                        qRating.contents,
                        qRating.likesNumber,
                        formattedDate.as("registerDatetime")
                ))
                .from(qRating)

                .leftJoin(qRating.ticketing(), qTicketing)
                .on(qRating.ticketing().id.eq(qTicketing.id))

                .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                .leftJoin(qScreeningSchedule.movie(), qMovie)
                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                .leftJoin(qTicketing.member(), qMember)
                .on(qTicketing.member().id.eq(qMember.id))

                .where(qRating.id.eq(ratingId))

                .fetchOne();
    }
}
