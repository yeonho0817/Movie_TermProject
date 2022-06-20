package com.term.movie.controller;

import com.term.movie.dto.ResponseData;
import com.term.movie.service.ScreenScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScreenScheduleController {
    private final ScreenScheduleService screeningScheduleService;

    // 전체 시간표 조회
    @GetMapping("/screenSchedule")
    public ResponseEntity readScreenSchedule() {
        ResponseData responseData = screeningScheduleService.readScreenSchedule();

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);
    }

//    // 한 영화 시간표 조회
//    @GetMapping("/screenSchedule/{movieId}")
//    public ResponseEntity readMovieScreenSchedule(@Param("movieId") Long movieId) {
//        ResponseData responseData = screeningScheduleService.readMovieScreenSchedule(movieId);
//
//        return ResponseEntity
//                .status(responseData.getStatus())
//                .body(responseData);
//    }
}
