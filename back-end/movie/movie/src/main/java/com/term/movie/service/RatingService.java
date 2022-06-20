package com.term.movie.service;

import com.term.movie.domain.Rating;
import com.term.movie.domain.Ticketing;
import com.term.movie.dto.RatingDTO;
import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ResponseMessage;
import com.term.movie.repository.RatingCustomRepository;
import com.term.movie.repository.RatingRepository;
import com.term.movie.repository.TicketingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RatingCustomRepository ratingCustomRepository;
    private final TicketingRepository ticketingRepository;

    // 조회
    public ResponseData readRating(Long ratingId) {
        return new ResponseData(HttpStatus.OK, "정상적으로 조회되었습니다.", ratingCustomRepository.readOneRatingDTO(ratingId));
    }

    // 등록
    public ResponseMessage createRating(RatingDTO.CreateRatingDTO createRatingDTO) {

        Optional<Ticketing> findTicketing = ticketingRepository.findById(createRatingDTO.getTicketingId());
        if (findTicketing.isEmpty()) return new ResponseMessage(HttpStatus.NOT_FOUND, "티켓이 존재하지 않습니다.");

        Rating rating = Rating.builder()
                    .ticketing(findTicketing.get())
                    .ratingPoint(createRatingDTO.getRatingPoint())
                    .contents(createRatingDTO.getContents())
                    .likesNumber(0)
                .build();

        ratingRepository.save(rating);

        return new ResponseMessage(HttpStatus.OK, "정상적으로 등록되었습니다.");
    }

    // 수정
    public ResponseMessage updateRating(RatingDTO.UpdateRatingDTO updateRatingDTO) {
        Optional<Rating> findRating = ratingRepository.findById(updateRatingDTO.getRatingId());

        if (findRating.isEmpty()) return new ResponseMessage(HttpStatus.NOT_FOUND, "검색하신 평점이 존재하지 않습니다.");

        findRating.get().updateRatingPoint(updateRatingDTO.getRatingPoint());
        findRating.get().updateContents(updateRatingDTO.getContents());

        ratingRepository.save(findRating.get());

        return new ResponseMessage(HttpStatus.OK, "정상적으로 수정되었습니다.");
    }

    // 삭제
    public ResponseMessage deleteRating(Long ratingId)
    {
        Optional<Rating> findRating = ratingRepository.findById(ratingId);

        if (findRating.isEmpty()) return new ResponseMessage(HttpStatus.NOT_FOUND, "검색한 평점이 존재하지 않습니다.");

        ratingRepository.deleteById(ratingId);

        return new ResponseMessage(HttpStatus.OK, "정상적으로 삭제되었습니다.");
    }

    // 좋아요
    public ResponseMessage updateLikesNumber(Long ratingId)
    {
        Optional<Rating> findRating = ratingRepository.findById(ratingId);

        if (findRating.isEmpty()) return new ResponseMessage(HttpStatus.NOT_FOUND, "검색한 평점이 존재하지 않습니다.");

        findRating.get().updateLikesNumber();

        ratingRepository.save(findRating.get());

        return new ResponseMessage(HttpStatus.OK, "정상적으로 등록되었습니다.");
    }
}
