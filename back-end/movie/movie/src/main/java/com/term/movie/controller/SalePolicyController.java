package com.term.movie.controller;

import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ResponseMessage;
import com.term.movie.dto.SalePolicyDTO;
import com.term.movie.service.SalePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class SalePolicyController {
    private final SalePolicyService salePolicyService;

    // 정책 조회
    @GetMapping("/salePolicy")
    public ResponseEntity readSalePolicy() {
        ResponseData responseData = salePolicyService.readSalePolicy();

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);
    }

    // 정률 정보 저장
    @PutMapping("/salePolicy/flatRate/update")
    public ResponseEntity updateFlatRateSalePolicy(@Validated @RequestBody SalePolicyDTO.FlatRatePolicyDTO flatRatePolicyDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ResponseMessage(HttpStatus.NOT_ACCEPTABLE, bindingResult.getFieldError().getDefaultMessage()));

        ResponseMessage responseMessage = salePolicyService.updateFlatRateSalePolicy(flatRatePolicyDTO);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }

    // 정액 정보 저장
    @PutMapping("/salePolicy/flat/update")
    public ResponseEntity updateFlatSalePolicy(@Validated @RequestBody SalePolicyDTO.FlatPolicyDTO flatPolicyDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ResponseMessage(HttpStatus.NOT_ACCEPTABLE, bindingResult.getFieldError().getDefaultMessage()));

        ResponseMessage responseMessage = salePolicyService.updateFlatSalePolicy(flatPolicyDTO);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }

    // 정책 적용
    @PutMapping("/salePolicy/apply")
    public ResponseEntity updateScreenSalePolicy(@RequestBody SalePolicyDTO.ApplySalePolicyDTO applySalePolicyDTO) {
        ResponseMessage responseMessage = salePolicyService.updateScreenSalePolicy(applySalePolicyDTO);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }
}
