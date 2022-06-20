package com.term.movie.repository;

import com.term.movie.dto.SeatDTO;

import java.util.List;

public interface SeatCustomRepository {
    List<SeatDTO.ReadSeatDTO> readRemainSeat(Long screenScheduleId);

    List<SeatDTO.ReadSeatDTO> readScreenSeat(Long screenScheduleId);
}
