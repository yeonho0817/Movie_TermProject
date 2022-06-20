package com.term.movie.service;

import com.term.movie.domain.*;
import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ResponseMessage;
import com.term.movie.dto.SeatDTO;
import com.term.movie.dto.TicketingDTO;
import com.term.movie.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketingService {
    private final TicketingRepository ticketingRepository;
    private final TicketingCustomRepository ticketingCustomRepository;
    private final MemberRepository memberRepository;
    private final SeatCustomRepository seatCustomRepository;
    private final ScreenScheduleRepository screenScheduleRepository;
    private final SeatRepository seatRepository;


    // 예매 등록
    public ResponseMessage createTicketing(TicketingDTO.CreateTicketingDTO createTicketingDTO) {
        Optional<Member> findMember = memberRepository.findById(createTicketingDTO.getMemberId());

        if (findMember.isEmpty()) return new ResponseMessage(HttpStatus.NOT_FOUND, "검색한 회원이 존재하지 않습니다.");

        Optional<ScreeningSchedule> findScreenSchedule = screenScheduleRepository.findById(createTicketingDTO.getScreenScheduleId());

        Ticketing ticketing = Ticketing.builder()
                .member(findMember.get())
                .price(createTicketingDTO.getPrice())
                .screeningSchedule(findScreenSchedule.get())
                .ticketingSeatList(null)
                .build();

        List<TicketingSeat> ticketingSeatList = createTicketingDTO.getTicketingSeatDTOList().stream()
                .map(ticketingSeatDTO -> TicketingSeat.builder()
                        .ticketing(ticketing)
                        .seat(seatRepository.findById(ticketingSeatDTO.longValue()).get())
                        .build())
                .collect(Collectors.toList());

        ticketing.setTicketingSeatList(ticketingSeatList);

        ticketingRepository.save(ticketing);

        return new ResponseMessage(HttpStatus.OK, "정상적으로 등록되었습니다.");
    }

    // 예매 자리 가능 조회
    public ResponseData readRemainSeat(Long screenScheduleId) {
        SeatDTO.ReadScreenScheduleSeatDTO readRemainSeat = ticketingCustomRepository.readRemainSeat(screenScheduleId);

        if (readRemainSeat.getSalePolicyName() != null) {
            if (readRemainSeat.getSalePolicyName().name().equals("정액할인"))
                readRemainSeat.setPrice(readRemainSeat.getPrice()-readRemainSeat.getSalePolicyValue());
            else
                readRemainSeat.setPrice((int) (readRemainSeat.getPrice()-( Double.parseDouble(readRemainSeat.getSalePolicyValue().toString())/100*readRemainSeat.getPrice() )));

        }
        List<SeatDTO.ReadSeatDTO> readUsingSeatDTOList = seatCustomRepository.readRemainSeat(screenScheduleId);

        List<SeatDTO.ReadSeatDTO> readAllSeatDTOList = seatCustomRepository.readScreenSeat(screenScheduleId);

        List<SeatDTO.ReadSeatColDTO> readSeatColDTOList = new ArrayList<>();
        List<SeatDTO.ReadSeatDTO> newAllSeatDTOList = new ArrayList<>();

        char colName = 'a';

        for (SeatDTO.ReadSeatDTO readSeatDTO : readAllSeatDTOList) {

            Boolean isUsing = readUsingSeatDTOList.stream().anyMatch(readUsingSeatDTO -> Objects.equals(readUsingSeatDTO.getId(), readSeatDTO.getId()));

            readSeatDTO.setIsUsing(isUsing);

            newAllSeatDTOList.add(readSeatDTO);

            if (readSeatDTO.getId() % 10 == 0) {
//                newAllSeatDTOList.add(readSeatDTO);

                SeatDTO.ReadSeatColDTO readSeatColDTO = SeatDTO.ReadSeatColDTO.builder()
                            .colName(colName)
                            .readSeatDTOList(newAllSeatDTOList)
                        .build();

                readSeatColDTOList.add(readSeatColDTO);

                newAllSeatDTOList = new ArrayList<>();

                colName++;
            }
        }


        readRemainSeat.setSeatColDTOS(readSeatColDTOList);

        return new ResponseData(HttpStatus.OK, "정상적으로 조회되었습니다.", readRemainSeat);
    }

}
