package com.term.movie.controller;

import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ResponseMessage;
import com.term.movie.dto.TicketingDTO;
import com.term.movie.service.TicketingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticketing")
public class TicketingController {
    private final TicketingService ticketingService;
    
    // 예매 등록
    @PostMapping("/create")
    public ResponseEntity createTicketing(@RequestBody TicketingDTO.CreateTicketingDTO createTicketingDTO) {
        ResponseMessage responseMessage = ticketingService.createTicketing(createTicketingDTO);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }
    
    // 예매 자리 조회
    @GetMapping("/remainSeat/{screenScheduleId}")
    public ResponseEntity readRemainSeat(@PathVariable("screenScheduleId") Long screenScheduleId) {
        ResponseData responseData = ticketingService.readRemainSeat(screenScheduleId);

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);
    }
}
