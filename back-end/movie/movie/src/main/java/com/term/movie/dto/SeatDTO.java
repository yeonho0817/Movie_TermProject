package com.term.movie.dto;

import com.term.movie.domain.SalePolicyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class SeatDTO {
    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadSeatDTO {
        private Long id;
        private Integer rowNumber;
        private Integer colNumber;
        private Boolean isUsing;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadSeatColDTO {
        private char colName;
        private List<ReadSeatDTO> readSeatDTOList;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadScreenScheduleSeatDTO {
        private Long screenScheduleId;

        private String theaterName;
        private Integer theaterFloor;

        private String startTime;
        private String endTime;

        private Integer price;

        private Long remainSeat;

        private SalePolicyName salePolicyName;
        private Integer salePolicyValue;

        List<ReadSeatColDTO> seatColDTOS;
    }

}
