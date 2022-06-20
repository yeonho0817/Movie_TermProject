package com.term.movie.controller;

import com.term.movie.dto.MovieDTO;
import com.term.movie.dto.PagingDTO;
import com.term.movie.dto.ResponseData;
import com.term.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MovieController {
    private final MovieService movieService;

    // 메인페이지 조회
    @GetMapping("/main")
    public ResponseEntity readMainMovie(@ModelAttribute PagingDTO pagingDTO) {

        ResponseData responseData = movieService.readMainMovie(pagingDTO);

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);
    }

    // 전체 조회    - 예매율 순
    @GetMapping("/movie/ticketingRating")
    public ResponseEntity readAllMovieSortedTicketingRating(@ModelAttribute MovieDTO.MovieSearchCondition movieSearchCondition) {

        ResponseData responseData =  movieService.readAllMovieSortedTicketingRating(movieSearchCondition);

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);
    }

    // 전체 조회    - 평점 순
    @GetMapping("/movie/rating")
    public ResponseEntity readAllMovieSortedRating(@ModelAttribute MovieDTO.MovieSearchCondition movieSearchCondition) {

        ResponseData responseData =   movieService.readAllMovieSortedRating(movieSearchCondition);

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);
    }

    // 상세 조회
    @GetMapping("/movie/detail/{movieId}")
    public ResponseEntity readDetailMovie(@PathVariable("movieId") Long movieId) {

        ResponseData responseData =   movieService.readDetailMovie(movieId);

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);
    }
}
