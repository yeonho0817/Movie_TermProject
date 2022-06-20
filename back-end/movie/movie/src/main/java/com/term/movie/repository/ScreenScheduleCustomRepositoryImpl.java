package com.term.movie.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.term.movie.domain.*;
import com.term.movie.dto.ScreenScheduleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScreenScheduleCustomRepositoryImpl implements ScreenScheduleCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ScreenScheduleDTO.ReadScreenMovieDTO> readScreenMovie() {
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;
        QMovie qMovie = QMovie.movie;
        QTheater qTheater = QTheater.theater;

        StringTemplate formatOpeningTime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qMovie.openingTime
                , ConstantImpl.create("%Y-%m-%d"));

        StringTemplate formatStartDatetime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qScreeningSchedule.startTime
                , ConstantImpl.create("%Y-%m-%d"));


        return jpaQueryFactory.selectDistinct( Projections.fields(ScreenScheduleDTO.ReadScreenMovieDTO.class,
                        qMovie.id.as("movieId"),
                        qMovie.title,
                        qMovie.genre,
                        qMovie.ageLimit,
                        qMovie.runtime,

                        formatOpeningTime.as("openingTime")

                ))
                .from(qScreeningSchedule)

                .leftJoin(qScreeningSchedule.movie(), qMovie)
                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                .leftJoin(qScreeningSchedule.theater(), qTheater)
                .on(qScreeningSchedule.theater().id.eq(qTheater.id))

                .where(formatStartDatetime.goe("2022-06-13"))

                .fetch();
    }

    @Override
    public List<ScreenScheduleDTO.ReadScreenScheduleInfoDTO> readScreenSchedule(Long movieId) {
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;
        QMovie qMovie = QMovie.movie;
        QTheater qTheater = QTheater.theater;
        QSeat qSeat = QSeat.seat;
        QTicketing qTicketing = QTicketing.ticketing;
        QTicketingSeat qTicketingSeat = QTicketingSeat.ticketingSeat;
        QSalePolicy qSalePolicy = QSalePolicy.salePolicy;


        StringTemplate formatOpeningTime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qMovie.openingTime
                , ConstantImpl.create("%Y-%m-%d"));

        StringTemplate formatStartTime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qScreeningSchedule.startTime
                , ConstantImpl.create("%Y-%m-%d %H:%i"));

        StringTemplate formatEndTime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qScreeningSchedule.endTime
                , ConstantImpl.create("%Y-%m-%d %H:%i"));

        StringTemplate formatStartDatetime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qScreeningSchedule.startTime
                , ConstantImpl.create("%Y-%m-%d"));



        return jpaQueryFactory.select( Projections.fields(ScreenScheduleDTO.ReadScreenScheduleInfoDTO.class,
                        qScreeningSchedule.id.as("screenScheduleId"),

                        formatOpeningTime.as("openingTime"),

                        qTheater.name.as("theaterName"),
                        qTheater.floor.as("theaterFloor"),

                        qSalePolicy.salePolicyName.as("salePolicyName"),
                        qSalePolicy.value.as("salePolicyValue"),

                        formatStartTime.as("startTime"),
                        formatEndTime.as("endTime"),


                        Expressions.as(

                                JPAExpressions.select( qSeat.id.count().subtract(

                                                JPAExpressions.select( qTicketingSeat.id.count() )
                                                        .from(qTicketingSeat)

                                                        .leftJoin(qTicketingSeat.ticketing(), qTicketing)
                                                        .on(qTicketingSeat.ticketing().id.eq(qTicketing.id))

                                                        .leftJoin(qTicketingSeat.seat(), qSeat)
                                                        .on(qTicketingSeat.seat().id.eq(qSeat.id))

                                                        .leftJoin(qTicketingSeat.ticketing(), qTicketing)
                                                        .on(qTicketingSeat.ticketing().id.eq(qTicketing.id))

                                                        .where(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                                        ))

                                        .from(qSeat)

                                        .leftJoin(qSeat.theater(), qTheater)
                                        .on(qSeat.theater().id.eq(qTheater.id))

                                        .where(qScreeningSchedule.theater().id.eq(qTheater.id))

                        , "remainSeat")

                ))
                .from(qScreeningSchedule)

                .leftJoin(qScreeningSchedule.movie(), qMovie)
                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                .leftJoin(qScreeningSchedule.theater(), qTheater)
                .on(qScreeningSchedule.theater().id.eq(qTheater.id))

                .leftJoin(qScreeningSchedule.salePolicy(), qSalePolicy)
                .on(qScreeningSchedule.salePolicy().id.eq(qSalePolicy.id))

                .where(formatStartDatetime.goe("2022-06-13")
                        .and(qMovie.id.eq(movieId)))

//                .where(qMovie.id.eq(movieId))

                .fetch();
    }

    @Override
    public ScreeningSchedule findById(Long screenScheduleId) {
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;

        return jpaQueryFactory.selectFrom(qScreeningSchedule)

                .where(qScreeningSchedule.id.eq(screenScheduleId))

                .fetchOne();
    }
}
