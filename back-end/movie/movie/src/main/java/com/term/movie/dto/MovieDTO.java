package com.term.movie.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.term.movie.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

public class MovieDTO {

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadMovieListDTO {
        private Long id;
        private String title;
        private String image;
        private Float rating; // 평점
        private Float ticketingRate;    // 예매율
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadAllMovieListDTO {
        private Long id;
        private String title;
        private String image;
        private String openingTime;
        private Float rating; // 평점
        private Float ticketingRate;    // 예매율
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadMovieDetailDTO {
        private Long id;
        private String title;
        private Integer ageLimit;
        private Integer runtime;
        private Integer cost;
        private String image;
        private Genre genre;
        private String openingTime;

        private String contents;

        private Float rating; // 평점
        private Float ticketingRate;    // 예매율

        private List<ActorDTO> actorList;

        private List<RatingDTO.ReadRatingDTO> readRatingDTOList;

//        private List<GenderDistributionChart> genderDistributionChartList;
        private DistributionChartProcess genderDistributionChartProcess;

        private DistributionChartProcess ageDistributionChartProcess;

//        private List<AgeDistributionChart> ageDistributionChartList;
    }


    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MovieSearchCondition {
        private String title;
        private String actorName;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DistributionChart {
        private String x;
        private Float y;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DistributionChartProcess {
        private List<String> labels;
        private List<Float> datas;
    }

//    @Data
//    @RequiredArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class AgeDistributionChart {
//        private String x;
//        private Float y;
//    }

}
