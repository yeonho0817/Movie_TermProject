package com.term.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;

public class RatingDTO {
    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadRatingDTO {
        private Long id;
        private Long memberPK;
        private String memberId;
        private Integer ratingPoint;
        private String contents;
        private Integer likesNumber;
        private String registerDatetime;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRatingDTO {
        private Long ratingId;
        private Integer ratingPoint;
        private String contents;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRatingDTO {
        private Long ticketingId;
        private Integer ratingPoint;
        private String contents;
    }

}
