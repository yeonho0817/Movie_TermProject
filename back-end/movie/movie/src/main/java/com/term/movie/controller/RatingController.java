package com.term.movie.controller;

import com.term.movie.dto.RatingDTO;
import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ResponseMessage;
import com.term.movie.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class RatingController {
    private final RatingService ratingService;

    // 조회
    @GetMapping("/rating/{ratingId}")
    public ResponseEntity readRating(@PathVariable("ratingId") Long ratingId)
    {
        ResponseData responseData = ratingService.readRating(ratingId);

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);
    }

    // 등록
    @PostMapping("/rating/create")
    public ResponseEntity createRating(@RequestBody RatingDTO.CreateRatingDTO createRatingDTO)
    {
        ResponseMessage responseMessage = ratingService.createRating(createRatingDTO);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }

    // 수정
    @PutMapping("/rating/update")
    public ResponseEntity updateRating(@RequestBody RatingDTO.UpdateRatingDTO updateRatingDTO) {
        ResponseMessage responseMessage = ratingService.updateRating(updateRatingDTO);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }
    
    // 삭제
    @DeleteMapping("/rating/delete/{ratingId}")
    public ResponseEntity deleteRating(@PathVariable("ratingId") Long ratingId)
    {
        ResponseMessage responseMessage = ratingService.deleteRating(ratingId);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }


    // 좋아요
    @PutMapping("/rating/like/{ratingId}")
    public ResponseEntity updateLikes(@PathVariable("ratingId") Long ratingId)
    {
        ResponseMessage responseMessage = ratingService.updateLikesNumber(ratingId);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }

}
