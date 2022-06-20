package com.term.movie.service;

import com.term.movie.dto.*;
import com.term.movie.repository.MovieActorCustomRepository;
import com.term.movie.repository.MovieCustomRepository;
import com.term.movie.repository.RatingCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieCustomRepository movieCustomRepository;
    private final MovieActorCustomRepository movieActorCustomRepository;
    private final RatingCustomRepository ratingCustomRepository;

    // 메인페이지 조회
    public ResponseData readMainMovie(PagingDTO pagingDTO) {
        Pageable pageable = PageRequest.of(pagingDTO.getCurrentPageNo(), pagingDTO.getRecordPerPage());

        return new ResponseData(HttpStatus.OK, "정상적으로 조회되었습니다.", movieCustomRepository.readMainMovie(pageable));
    }

    // 전체 조회    - 예매율 순
    public ResponseData readAllMovieSortedTicketingRating(@ModelAttribute MovieDTO.MovieSearchCondition movieSearchCondition) {
        return new ResponseData(HttpStatus.OK, "정상적으로 조회되었습니다.",
                movieCustomRepository.readAllMovie(movieSearchCondition).stream()
                    .sorted(Comparator.comparing(MovieDTO.ReadAllMovieListDTO::getTicketingRate).reversed())
                    .collect(Collectors.toList()));
    }

    // 전체 조회    - 평점 순
    public ResponseData readAllMovieSortedRating(@ModelAttribute MovieDTO.MovieSearchCondition movieSearchCondition) {
        return new ResponseData(HttpStatus.OK, "정상적으로 조회되었습니다.",

                movieCustomRepository.readAllMovie(movieSearchCondition).stream()
                    .sorted(Comparator.comparing(MovieDTO.ReadAllMovieListDTO::getRating).reversed())
                    .collect(Collectors.toList()));
    }

    // 상세 조회
    public ResponseData readDetailMovie(Long movieId) {
         MovieDTO.ReadMovieDetailDTO readMovieDetailDTOList = movieCustomRepository.readDetailMovie(movieId);

         // 배우
        readMovieDetailDTOList.setActorList(movieActorCustomRepository.readMovieActor(movieId));
        
        // 평점
        readMovieDetailDTOList.setReadRatingDTOList(
                ratingCustomRepository.readRatingDTO(movieId).stream()
                    .sorted(Comparator.comparing(RatingDTO.ReadRatingDTO::getRegisterDatetime))
                    .collect(Collectors.toList())
        );

        // 차트 - 성별
        List<MovieDTO.DistributionChart> genderDistributionChartList = movieCustomRepository.readGenderDistributionChart(movieId);

        readMovieDetailDTOList.setGenderDistributionChartProcess( dataProcess(genderDistributionChartList) );

        // 차트 - 연령별

        List<MovieDTO.DistributionChart> ageDistributionChartList = movieCustomRepository.readAgeDistributionChart(movieId).stream()
                        .sorted(Comparator.comparing(MovieDTO.DistributionChart::getX))
                        .collect(Collectors.toList());

        readMovieDetailDTOList.setAgeDistributionChartProcess( dataProcess(ageDistributionChartList) );


        return new ResponseData(HttpStatus.OK, "정상적으로 조회되었습니다.", readMovieDetailDTOList);
    }


    private MovieDTO.DistributionChartProcess dataProcess(List<MovieDTO.DistributionChart> distributionChartList) {

        List<String> labels = new ArrayList<>();
        List<Float> datas = new ArrayList<>();

        distributionChartList.stream()
                .forEach(genderDistributionChart -> {
                    labels.add(genderDistributionChart.getX());
                    datas.add(genderDistributionChart.getY());
                });


        return MovieDTO.DistributionChartProcess.builder()
                .datas(datas)
                .labels(labels)
                .build();
    }

}
