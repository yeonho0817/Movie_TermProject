package com.term.movie.controller;

import com.term.movie.domain.Member;
import com.term.movie.domain.Ticketing;
import com.term.movie.dto.MemberDto;
import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ResponseMessage;
import com.term.movie.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class MemberController {

    private final MemberService memberService;

    private boolean flag = false;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/user/registration")
    public ResponseEntity createForm(@RequestBody MemberDto.MemberInfoDTO memberInfoDto) {

        ResponseMessage  responseMessage =  memberService.join(memberInfoDto);

        return ResponseEntity
                .status(responseMessage.getStatus())
                .body(responseMessage);
    }

    //myPage
    @GetMapping("/user/{memberLoginId}")
    public ResponseEntity getMemberTicketingInfoAndMemberInfo(@PathVariable("memberLoginId") Long memberLoginId){

//        List<ResponseData> responseDataList = new ArrayList<>();
//        responseDataList.add(memberService.getMemberInfo(memberLoginId));
//        responseDataList.add(memberService.getTicketingInfoByMemberId(memberLoginId));
//        if(responseDataList.get(0).getStatus()!=HttpStatus.OK && responseDataList.get(1).getStatus()!=HttpStatus.OK) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        else{
//            return ResponseEntity.status(HttpStatus.OK).body(responseDataList);
//        }

        ResponseData responseData = memberService.getMyPageInfo(memberLoginId);

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);

    }


    @PostMapping("/user/login")
    public ResponseEntity login(@RequestBody MemberDto.LoginDTO loginDTO) {

        ResponseData responseData = memberService.loginValidation(loginDTO);

        return ResponseEntity
                .status(responseData.getStatus())
                .body(responseData);

    }

}
