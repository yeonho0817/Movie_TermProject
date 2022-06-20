package com.term.movie.repository;

import com.term.movie.dto.SeatDTO;
import com.term.movie.dto.TicketingDTO;

import java.util.List;

public interface TicketingCustomRepository {

    // 잔여 좌석 - > screenId
    SeatDTO.ReadScreenScheduleSeatDTO readRemainSeat(Long screenScheduleId);

    // 예매 내역
    List<TicketingDTO.MemberTicketingInfoDTO> readMemberTicketing(Long memberId);
}
