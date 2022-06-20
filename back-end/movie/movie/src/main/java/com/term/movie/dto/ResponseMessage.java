package com.term.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseMessage {
    private HttpStatus status;
    private String message;
}
