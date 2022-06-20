package com.term.movie.repository;

import com.term.movie.domain.Movie;
import com.term.movie.dto.MovieDTO;
import com.term.movie.dto.PagingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MovieCustomRepository {

    List<MovieDTO.ReadMovieListDTO> readMainMovie(Pageable pageable);

    List<MovieDTO.ReadAllMovieListDTO> readAllMovie(MovieDTO.MovieSearchCondition movieSearchCondition);

    MovieDTO.ReadMovieDetailDTO readDetailMovie(Long movieId);


    List<MovieDTO.DistributionChart> readGenderDistributionChart(Long movieId);

    List<MovieDTO.DistributionChart> readAgeDistributionChart(Long movieId);

}
