package com.term.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class TicketingDTO {

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateTicketingDTO {
        private Long memberId;
        private Long screenScheduleId;
        private Integer price;

        private List<Integer> ticketingSeatDTOList;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemberTicketingInfoDTO {
        private Long ticketId;
        private String movieTitle;
        private String movieImgAddress;
        private String theaterName;
        private String registerDatetime;
        private Integer seatCount;
        private Integer price;

        private Long ratingId;
    }
}
