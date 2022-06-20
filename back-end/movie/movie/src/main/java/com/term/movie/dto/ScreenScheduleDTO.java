package com.term.movie.dto;

import com.term.movie.domain.Genre;
import com.term.movie.domain.SalePolicyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class ScreenScheduleDTO {
    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadScreenScheduleInfoDTO {
        private Long screenScheduleId;

        private String theaterName;
        private Integer theaterFloor;

        private String startTime;
        private String endTime;

        private Long remainSeat;

        private Boolean isClosed;

        private SalePolicyName salePolicyName;
        private Integer salePolicyValue;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadScreenMovieDTO {
        private Long movieId;
        private String title;
        private Genre genre;

        private Integer ageLimit;
        private Integer runtime;
        private String openingTime;

        List<ReadScreenScheduleInfoDTO> readScreenScheduleInfoDTOList;
    }



}
