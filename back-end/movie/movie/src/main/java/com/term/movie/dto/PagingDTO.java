package com.term.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PagingDTO {
    private int currentPageNo;
    private int recordPerPage;
}
