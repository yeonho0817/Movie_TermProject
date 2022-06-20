package com.term.movie.dto;

import com.sun.istack.NotNull;
import com.term.movie.domain.Authority;
import com.term.movie.domain.Gender;
import com.term.movie.domain.Member;
import com.term.movie.domain.Ticketing;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.util.List;


public class MemberDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginDTO {
        private String memberId;
        private String memberPw;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReturnLoginInfoDTO {
        private Long id;
        private String name;
        private String authority;
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberInfoDTO {
        @NotNull
        @NotEmpty
        private String memberId;

        @NotNull
        @NotEmpty
        private String memberPw;

        @NotNull
        @NotEmpty
        private String name;

        @NotNull
        @NotEmpty
        private String gender;

        @NotNull
        @NotEmpty
        private Integer age;

        @NotNull
        @NotEmpty
        private Authority authority;

        @NotNull
        @NotEmpty
        private String phone;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MyPageDto{
        private MyPageMemberInfo memberInfoDTO;
        private List<TicketingDTO.MemberTicketingInfoDTO> memberTicketingInfoDTOList;

        private Integer ticketingCount;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MyPageMemberInfo {
        private String memberId;
        private String gender;
        private Integer age;
        private String phone;
    }


}
