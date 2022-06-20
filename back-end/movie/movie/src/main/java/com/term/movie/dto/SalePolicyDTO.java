package com.term.movie.dto;

import com.term.movie.domain.SalePolicyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


public class SalePolicyDTO {

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadSalePolicyDTO {
        private Long salePolicyId;
        private SalePolicyName salePolicyName;
        private Integer value;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReadSalePolicyManagement {
        List<ReadSalePolicyDTO> readSalePolicyDTOList;
        List<ScreenScheduleDTO.ReadScreenScheduleInfoDTO> readScreenScheduleInfoDTOList;
    }


    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FlatRatePolicyDTO {
        private Long salePolicyId;

        @Range(max = 100, min = 0, message = "0~100 값을 입력해주세요")
        private Integer value;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FlatPolicyDTO {
        private Long salePolicyId;

        @Range(max = 5000, min = 0, message = "0~5000 값을 입력해주세요")
        private Integer value;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApplySalePolicyDTO {
        private String salePolicyName;
        private Long screenScheduleId;
    }


}
