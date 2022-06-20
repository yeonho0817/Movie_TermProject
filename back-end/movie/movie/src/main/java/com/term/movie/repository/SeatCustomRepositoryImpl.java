package com.term.movie.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.term.movie.domain.*;
import com.term.movie.dto.SeatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatCustomRepositoryImpl implements SeatCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SeatDTO.ReadSeatDTO> readRemainSeat(Long screenScheduleId) {
        QSeat qSeat = QSeat.seat;
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;
        QTheater qTheater = QTheater.theater;
        QTicketing qTicketing = QTicketing.ticketing;
        QTicketingSeat qTicketingSeat = QTicketingSeat.ticketingSeat;


        return jpaQueryFactory.selectDistinct(Projections.fields(SeatDTO.ReadSeatDTO.class,
                    qSeat.id,
                    qSeat.rowNumber,
                    qSeat.colNumber
                ))
                .from(qTicketingSeat)

                .rightJoin(qTicketingSeat.seat(), qSeat)
                .on(qTicketingSeat.seat().id.eq(qSeat.id))

//                .rightJoin(qSeat.theater(), qTheater)
//                .on(qSeat.theater().id.eq(qTheater.id))

                .join(qTicketingSeat.ticketing(), qTicketing)
                .on(qTicketingSeat.ticketing().id.eq(qTicketing.id))

                .join(qTicketing.screeningSchedule(), qScreeningSchedule)
                .on(qTicketing.screeningSchedule().id.eq(qScreeningSchedule.id))

                .join(qScreeningSchedule.theater(), qTheater)
                .on(qScreeningSchedule.theater().id.eq(qTheater.id))

                .where(qScreeningSchedule.id.eq(screenScheduleId))

                .fetch();
    }

    @Override
    public List<SeatDTO.ReadSeatDTO> readScreenSeat(Long screenScheduleId) {
        QSeat qSeat = QSeat.seat;
        QScreeningSchedule qScreeningSchedule = QScreeningSchedule.screeningSchedule;
        QTheater qTheater = QTheater.theater;


        return jpaQueryFactory.select( Projections.fields(SeatDTO.ReadSeatDTO.class,
                    qSeat.id,
                    qSeat.rowNumber,
                    qSeat.colNumber
                ))

                .from(qScreeningSchedule)

                .join(qScreeningSchedule.theater(), qTheater)
                .on(qScreeningSchedule.theater().id.eq(qTheater.id))

                .join(qTheater.seatList, qSeat)

                .where(qScreeningSchedule.id.eq(screenScheduleId))

                .fetch();
    }
}
