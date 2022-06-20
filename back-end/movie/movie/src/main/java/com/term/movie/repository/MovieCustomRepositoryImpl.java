package com.term.movie.repository;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.term.movie.domain.*;
import com.term.movie.dto.ActorDTO;
import org.apache.commons.lang3.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.term.movie.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import com.querydsl.core.ResultTransformer;
import com.querydsl.core.group.GroupBy;


import org.springframework.data.domain.Pageable;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class MovieCustomRepositoryImpl implements MovieCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMovie qMovie = QMovie.movie;
    private final QMovieActor movieActor = QMovieActor.movieActor;
    private final QActor qActor = QActor.actor;

    @Override
    public List<MovieDTO.ReadMovieListDTO> readMainMovie(Pageable pageable) {

        QTicketing qTicketing = QTicketing.ticketing;   // 예매율
        QRating qRating = QRating.rating;   // 평점
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;


        return jpaQueryFactory.select(Projections.fields(MovieDTO.ReadMovieListDTO.class,
                    qMovie.id,
                    qMovie.image,
                    qMovie.title,

                    ExpressionUtils.as(
                            MathExpressions.round(
                                JPAExpressions.select(qTicketing.id.count().floatValue().divide(
                                                JPAExpressions.select(qTicketing.id.count().floatValue())
                                                        .from(qTicketing)
                                        ))
                                        .from(qTicketing)
                                        .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                                        .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))
                                        .where(qScreeningSchedule.movie().id.eq(qMovie.id)),
                            3).multiply(100),
                    "ticketingRate"),

                    ExpressionUtils.as(
                        MathExpressions.round(
                            JPAExpressions.select(qRating.ratingPoint.avg().floatValue())
                                    .from(qRating)

                                    .leftJoin(qRating.ticketing(), qTicketing)
                                    .on(qRating.ticketing().id.eq(qTicketing.id))

                                    .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                                    .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

//                                    .leftJoin(qScreeningSchedule.movie(), qMovie)
//                                    .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                                    .where(qScreeningSchedule.movie().id.eq(qMovie.id))
                        ,3).coalesce(0.0F)
                    ,"rating")

                ))
                .from(qMovie)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();
    }


    @Override
    public List<MovieDTO.ReadAllMovieListDTO> readAllMovie(MovieDTO.MovieSearchCondition movieSearchCondition) {
        QTicketing qTicketing = QTicketing.ticketing;   // 예매율
        QRating qRating = QRating.rating;   // 평점
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;
        QActor qActor = QActor.actor;
        QMovieActor qMovieActor = QMovieActor.movieActor;

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qMovie.openingTime
                , ConstantImpl.create("%Y-%m-%d"));

        return jpaQueryFactory.selectDistinct(Projections.fields(MovieDTO.ReadAllMovieListDTO.class,
                        qMovie.id,
                        qMovie.title,
                        qMovie.image,
                        formattedDate.as("openingTime"),

                        ExpressionUtils.as(
                                MathExpressions.round(
                                        JPAExpressions.select(qTicketing.id.count().floatValue().divide(
                                                        JPAExpressions.select(qTicketing.id.count().floatValue())
                                                                .from(qTicketing)
                                                ))
                                                .from(qTicketing)
                                                .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                                                .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))
                                                .where(qScreeningSchedule.movie().id.eq(qMovie.id)),
                                        3).multiply(100),
                                "ticketingRate"),

                        ExpressionUtils.as(
                                MathExpressions.round(
                                        JPAExpressions.select(qRating.ratingPoint.avg().floatValue())
                                                .from(qRating)

                                                .leftJoin(qRating.ticketing(), qTicketing)
                                                .on(qRating.ticketing().id.eq(qTicketing.id))

                                                .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                                                .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

//                                                .leftJoin(qScreeningSchedule.movie(), qMovie)
//                                                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                                                .where(qScreeningSchedule.movie().id.eq(qMovie.id))
                                        ,3).coalesce(0.0F)
                                ,"rating")
                ))
                .from(qMovie)

                .leftJoin(qMovie.movieActorList, qMovieActor)
                .on(qMovieActor.movie().id.eq(qMovie.id))

                .leftJoin(qMovieActor.actor(), qActor)
                .on(qMovieActor.actor().id.eq(qActor.id))

                .where(
                        likeTitle(movieSearchCondition.getTitle()),
                        eqActor(movieSearchCondition.getActorName())
                )
                .fetch();
    }

    @Override
    public MovieDTO.ReadMovieDetailDTO readDetailMovie(Long movieId) {
        QTicketing qTicketing = QTicketing.ticketing;   // 예매율
        QRating qRating = QRating.rating;   // 평점
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;

        QActor qActor = QActor.actor;
        QMovieActor qMovieActor = QMovieActor.movieActor;

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qMovie.openingTime
                , ConstantImpl.create("%Y-%m-%d"));


        return jpaQueryFactory.selectDistinct(
                    Projections.fields(MovieDTO.ReadMovieDetailDTO.class,
                            qMovie.id,
                            qMovie.title,
                            qMovie.image,
                            qMovie.contents,
                            qMovie.ageLimit,
                            qMovie.runtime,
                            qMovie.cost,
                            qMovie.genre,
                            formattedDate.as("openingTime"),

                            ExpressionUtils.as(
                                    MathExpressions.round(
                                            JPAExpressions.select(qTicketing.id.count().floatValue().divide(
                                                            JPAExpressions.select(qTicketing.id.count().floatValue())
                                                                    .from(qTicketing)
                                                    ))
                                                    .from(qTicketing)
                                                    .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                                                    .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))
                                                    .where(qScreeningSchedule.movie().id.eq(qMovie.id)),
                                            3).multiply(100),
                                    "ticketingRate"),

                            ExpressionUtils.as(
                                    MathExpressions.round(
                                            JPAExpressions.select(qRating.ratingPoint.avg().floatValue())
                                                    .from(qRating)

                                                    .leftJoin(qRating.ticketing(), qTicketing)
                                                    .on(qRating.ticketing().id.eq(qTicketing.id))

                                                    .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                                                    .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

//                                                    .leftJoin(qScreeningSchedule.movie(), qMovie)
//                                                    .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                                                    .where(qScreeningSchedule.movie().id.eq(qMovie.id))
                                            ,3).coalesce(0.0F)
                                    ,"rating")
                ))
                .from(qMovie)

                .leftJoin(qMovie.movieActorList, qMovieActor)
                .on(qMovieActor.movie().id.eq(qMovie.id))

                .leftJoin(qMovieActor.actor(), qActor)
                .on(qMovieActor.actor().id.eq(qActor.id))

                .where(qMovie.id.eq(movieId))

                .fetchOne();
    }

    @Override
    public List<MovieDTO.DistributionChart> readGenderDistributionChart(Long movieId) {
        QMember qMember = QMember.member;
        QTicketing qTicketing = QTicketing.ticketing;
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;

        return jpaQueryFactory.select(Projections.fields(MovieDTO.DistributionChart.class,
                    qMember.gender.stringValue().as("x"),

                    MathExpressions.round(
                        qTicketing.id.count().floatValue().divide(
                            JPAExpressions.select( qTicketing.id.count().floatValue() )
                                    .from(qTicketing)

                                    .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                                    .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                                    .leftJoin(qScreeningSchedule.movie(), qMovie)
                                    .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                                    .where(qMovie.id.eq(movieId))
                        )
                    ,3).multiply(100).as("y")
                ))
                .from(qTicketing)

                .leftJoin(qTicketing.member(), qMember)
                .on(qTicketing.member().id.eq(qMember.id))

                .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                .leftJoin(qScreeningSchedule.movie(), qMovie)
                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                .groupBy(qMember.gender)

                .where(qMovie.id.eq(movieId))

                .fetch();

    }

    @Override
    public List<MovieDTO.DistributionChart> readAgeDistributionChart(Long movieId) {
        QMember qMember = QMember.member;
        QTicketing qTicketing = QTicketing.ticketing;
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;

        return jpaQueryFactory.select( Projections.fields(MovieDTO.DistributionChart.class,
                    qMember.age.subtract( qMember.age.mod(10) ).stringValue().as("x"),

                    MathExpressions.round(
                            qTicketing.id.count().floatValue().divide(
                                    JPAExpressions.select( qTicketing.id.count().floatValue() )
                                            .from(qTicketing)

                                            .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                                            .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                                            .leftJoin(qScreeningSchedule.movie(), qMovie)
                                            .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                                            .where(qMovie.id.eq(movieId))
                            )
                    ,3).multiply(100).as("y")
                ))
                .from(qTicketing)

                .leftJoin(qTicketing.screeningSchedule(), qScreeningSchedule)
                .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                .leftJoin(qTicketing.member(), qMember)
                .on(qTicketing.member().id.eq(qMember.id))

                .leftJoin(qScreeningSchedule.movie(), qMovie)
                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                .where(qMovie.id.eq(movieId))

                .groupBy( qMember.age.subtract(qMember.age.mod(10)) )

                .fetch();
    }


    private BooleanExpression likeTitle(String title)
    {
        if (StringUtils.isEmpty(title)) {
            return null;
        }
        return qMovie.title.contains(title);
    }

    private BooleanExpression eqActor(String actorName) {
        if (StringUtils.isEmpty(actorName))
        {
            return null;
        }
        return qActor.name.eq(actorName);
    }

}
