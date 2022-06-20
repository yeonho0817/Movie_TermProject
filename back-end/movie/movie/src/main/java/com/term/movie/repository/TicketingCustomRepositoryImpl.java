package com.term.movie.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.term.movie.domain.*;
import com.term.movie.dto.SeatDTO;
import com.term.movie.dto.TicketingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketingCustomRepositoryImpl implements TicketingCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SeatDTO.ReadScreenScheduleSeatDTO readRemainSeat(Long screenScheduleId) {
        QTheater qTheater = QTheater.theater;
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;
        QMovie qMovie = QMovie.movie;
        QTicketing qTicketing = QTicketing.ticketing;
        QTicketingSeat qTicketingSeat = QTicketingSeat.ticketingSeat;
        QSeat qSeat = QSeat.seat;
        QSalePolicy qSalePolicy = QSalePolicy.salePolicy;

        StringTemplate formatStartTime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qScreeningSchedule.startTime
                , ConstantImpl.create("%H:%i"));

        StringTemplate formatEndTime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qScreeningSchedule.endTime
                , ConstantImpl.create("%H:%i"));

        return jpaQueryFactory.selectDistinct(Projections.fields(SeatDTO.ReadScreenScheduleSeatDTO.class,
                        qScreeningSchedule.id.as("screenScheduleId"),

                        qMovie.id.as("movieId"),
                        qMovie.cost.as("price"),

                        qTheater.name.as("theaterName"),
                        qTheater.floor.as("theaterFloor"),
                        formatStartTime.as("startTime"),
                        formatEndTime.as("endTime"),

                        qSalePolicy.salePolicyName.as("salePolicyName"),
                        qSalePolicy.value.as("salePolicyValue"),

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

                .leftJoin(qScreeningSchedule.theater(), qTheater)
                .on(qScreeningSchedule.theater().id.eq(qTheater.id))

                .leftJoin(qScreeningSchedule.movie(), qMovie)
                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                .leftJoin(qScreeningSchedule.salePolicy(), qSalePolicy)
                .on(qScreeningSchedule.salePolicy().id.eq(qSalePolicy.id))

                .where(qScreeningSchedule.id.eq(screenScheduleId))

                .fetchOne();
    }

    @Override
    public List<TicketingDTO.MemberTicketingInfoDTO> readMemberTicketing(Long memberId) {
        QTheater qTheater = QTheater.theater;
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;
        QMovie qMovie = QMovie.movie;
        QTicketing qTicketing = QTicketing.ticketing;
        QTicketingSeat qTicketingSeat = QTicketingSeat.ticketingSeat;
        QRating qRating = QRating.rating;

        StringTemplate formatRegisterDatetime = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qTicketing.registerDatetime
                , ConstantImpl.create("%Y-%m-%d"));

        return jpaQueryFactory.select(Projections.fields(TicketingDTO.MemberTicketingInfoDTO.class,
                    qTicketing.id.as("ticketId"),

                    qMovie.title.as("movieTitle"),
                    qMovie.image.as("movieImgAddress"),

                    qTheater.name.as("theaterName"),

                    formatRegisterDatetime.as("registerDatetime"),

                    qTicketing.price.as("price"),

                    Expressions.as(
                            JPAExpressions.select(qTicketingSeat.id.count().intValue())
                                    .from(qTicketingSeat)

                                    .where(qTicketingSeat.ticketing().id.eq(qTicketing.id))
                            , "seatCount"
                    ),

                    Expressions.as(
                            JPAExpressions.select(qRating.id)
                                    .from(qRating)

                                    .where(qRating.ticketing().id.eq(qTicketing.id))

                            , "ratingId"
                    )

                ))

                .from(qTicketing)

                .join(qTicketing.screeningSchedule(), qScreeningSchedule)
                .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                .join(qScreeningSchedule.movie(), qMovie)
                .on(qScreeningSchedule.movie().id.eq(qMovie.id))

                .join(qScreeningSchedule.theater(), qTheater)
                .on(qScreeningSchedule.theater().id.eq(qTheater.id))

                .where(qTicketing.member().id.eq(memberId))

                .fetch();
    }
}
