package com.term.movie.service;

import com.term.movie.domain.Authority;
import com.term.movie.domain.Gender;
import com.term.movie.domain.Member;
import com.term.movie.domain.Ticketing;
import com.term.movie.dto.MemberDto;
import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ResponseMessage;
import com.term.movie.dto.TicketingDTO;
import com.term.movie.repository.MemberRepository;
import com.term.movie.repository.TicketingCustomRepository;
import com.term.movie.repository.TicketingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Service("memberService")
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TicketingRepository ticketingRepository;
    private final TicketingCustomRepository ticketingCustomRepository;

    public ResponseMessage join(MemberDto.MemberInfoDTO memberInfoDTO){

        Member member = Member.builder()
                        .memberId(memberInfoDTO.getMemberId())
                        .memberPw(memberInfoDTO.getMemberPw())
                        .name(memberInfoDTO.getName())
                        .gender(memberInfoDTO.getGender().equals("male")? Gender.남자:Gender.여자)
                        .age(memberInfoDTO.getAge())
                        .authority(Authority.USER)
                        .phone(memberInfoDTO.getPhone())
                    .build();

        memberRepository.save(member);

        return new ResponseMessage(HttpStatus.OK, "정상적으로 회원가입되었습니다.");
    }

    public ResponseData getMemberInfo(Long memberId){
        return new ResponseData(HttpStatus.OK, "정상적으로 회원 정보를 얻어 왔습니다.", memberRepository.findById(memberId).get());
    }

    public ResponseData getTicketingInfoByMemberId(Long memberId){
        return new ResponseData(HttpStatus.OK,"회원 예매 정보를 얻어 왔습니다",ticketingRepository.findTicketingByMemberId(memberRepository.findById(memberId).get().getId()));
    }

    public ResponseData getMyPageInfo(Long memberId) {
        Member findMember= memberRepository.findById(memberId).get();

        List<TicketingDTO.MemberTicketingInfoDTO> memberTicketingInfoDTOList = ticketingCustomRepository.readMemberTicketing(memberId);

        MemberDto.MyPageDto myPageDto = MemberDto.MyPageDto.builder()
                    .memberInfoDTO(MemberDto.MyPageMemberInfo.builder()
                                .memberId(findMember.getMemberId())
                                .gender(findMember.getGender().name())
                                .age(findMember.getAge())
                                .phone(findMember.getPhone())
                            .build())
                    .ticketingCount(memberTicketingInfoDTOList.size())
                    .memberTicketingInfoDTOList(memberTicketingInfoDTOList)
                .build();

        return new ResponseData(HttpStatus.OK, "정상적으로 조회되었습니다.", myPageDto);
    }

    public ResponseData loginValidation(MemberDto.LoginDTO loginDTO) {

        Optional<Member> findMember = memberRepository.findByMemberIdAndMemberPw(loginDTO.getMemberId(), loginDTO.getMemberPw());

        if (findMember.isEmpty()) return new ResponseData(HttpStatus.NOT_FOUND, "일치하는 아이디와 비번이 존재하지 않습니다.", null);

        return new ResponseData(HttpStatus.OK,
                "정상적으로 조회되었습니다.",
                    MemberDto.ReturnLoginInfoDTO.builder()
                        .authority(findMember.get().getAuthority().name())
                        .name(findMember.get().getName())
                        .id(findMember.get().getId())
                    .build()
                );

    }


}
