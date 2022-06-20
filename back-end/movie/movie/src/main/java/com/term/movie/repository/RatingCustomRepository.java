package com.term.movie.repository;

import com.term.movie.domain.Rating;
import com.term.movie.dto.RatingDTO;

import java.util.List;

public interface RatingCustomRepository {
    List<RatingDTO.ReadRatingDTO> readRatingDTO(Long movieId);

    RatingDTO.ReadRatingDTO readOneRatingDTO(Long ratingId);
}
