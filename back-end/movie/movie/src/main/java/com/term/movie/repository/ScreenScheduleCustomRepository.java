package com.term.movie.repository;

import com.term.movie.domain.ScreeningSchedule;
import com.term.movie.dto.ScreenScheduleDTO;

import java.util.List;

public interface ScreenScheduleCustomRepository {
    // 전체 시간표 조회
    List<ScreenScheduleDTO.ReadScreenMovieDTO> readScreenMovie();


    List<ScreenScheduleDTO.ReadScreenScheduleInfoDTO> readScreenSchedule(Long movieId);

    ScreeningSchedule findById(Long screenScheduleId);

}
