package com.term.movie.service;

import com.term.movie.domain.SalePolicy;
import com.term.movie.domain.ScreeningSchedule;
import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ResponseMessage;
import com.term.movie.dto.SalePolicyDTO;
import com.term.movie.dto.ScreenScheduleDTO;
import com.term.movie.repository.SalePolicyCustomRepository;
import com.term.movie.repository.SalePolicyRepository;
import com.term.movie.repository.ScreenScheduleCustomRepository;
import com.term.movie.repository.ScreenScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalePolicyService {
    private final SalePolicyCustomRepository salePolicyCustomRepository;
    private final ScreenScheduleService screenScheduleService;
    private final SalePolicyRepository salePolicyRepository;
    private final ScreenScheduleCustomRepository screenScheduleCustomRepository;
    private final ScreenScheduleRepository screenScheduleRepository;

    // 정책 조회
    public ResponseData readSalePolicy() {
        SalePolicyDTO.ReadSalePolicyManagement readSalePolicyManagement = SalePolicyDTO.ReadSalePolicyManagement.builder()
                    .readSalePolicyDTOList(salePolicyCustomRepository.readSalePolicy())
                    .readScreenScheduleInfoDTOList(screenScheduleService.readScreenMovie().get(0).getReadScreenScheduleInfoDTOList())
                .build();

        return new ResponseData(HttpStatus.OK,"정상적으로 조회되었습니다.", readSalePolicyManagement);
    }

    // 정률 저장
    public ResponseMessage updateFlatRateSalePolicy(SalePolicyDTO.FlatRatePolicyDTO flatRatePolicyDTO) {
//        Optional<SalePolicy> findSalePolicy = salePolicyRepository.findById(flatRatePolicyDTO.getSalePolicyId());
        SalePolicy findSalePolicy = salePolicyCustomRepository.findById(flatRatePolicyDTO.getSalePolicyId());

        findSalePolicy.setValue(flatRatePolicyDTO.getValue());

        salePolicyRepository.save(findSalePolicy);

        return new ResponseMessage(HttpStatus.OK, "정상적으로 수정되었습니다.");
    }

    // 정액 저장
    public ResponseMessage updateFlatSalePolicy(SalePolicyDTO.FlatPolicyDTO flatPolicyDTO) {
//        Optional<SalePolicy> findSalePolicy = salePolicyRepository.findById(flatPolicyDTO.getSalePolicyId());
        SalePolicy findSalePolicy = salePolicyCustomRepository.findById(flatPolicyDTO.getSalePolicyId());

        findSalePolicy.setValue(flatPolicyDTO.getValue());

        salePolicyRepository.save(findSalePolicy);

        return new ResponseMessage(HttpStatus.OK, "정상적으로 수정되었습니다.");
    }

    // 정책 적용
    public ResponseMessage updateScreenSalePolicy(SalePolicyDTO.ApplySalePolicyDTO applySalePolicyDTO) {
        ScreeningSchedule findScreeningSchedule = screenScheduleCustomRepository.findById(applySalePolicyDTO.getScreenScheduleId());

        if (applySalePolicyDTO.getSalePolicyName() == null || applySalePolicyDTO.getSalePolicyName().equals("null"))
            findScreeningSchedule.setSalePolicy(null);
        else {
            SalePolicy findSalePolicy = salePolicyCustomRepository.findBySalePolicyName(applySalePolicyDTO.getSalePolicyName());

            findScreeningSchedule.setSalePolicy(findSalePolicy);
        }

        screenScheduleRepository.save(findScreeningSchedule);

        return new ResponseMessage(HttpStatus.OK, "정상적으로 수정되었습니다.");
    }
}
