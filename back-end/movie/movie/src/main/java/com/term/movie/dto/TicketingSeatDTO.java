package com.term.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class TicketingSeatDTO {

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateTicketingSeatDTO {
        private Long seatId;
    }

}
