package com.term.movie.service;

import com.term.movie.dto.ResponseData;
import com.term.movie.dto.ScreenScheduleDTO;
import com.term.movie.repository.ScreenScheduleCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreenScheduleService {
    private final ScreenScheduleCustomRepository screeningScheduleCustomRepository;

    // 전체 시간표 조회
    public ResponseData readScreenSchedule() {

        List<ScreenScheduleDTO.ReadScreenMovieDTO> readScreenScheduleDTOList = readScreenMovie();

        return new ResponseData(HttpStatus.OK, "정상적으로 조회되었습니다", readScreenScheduleDTOList);
    }

    public List<ScreenScheduleDTO.ReadScreenMovieDTO> readScreenMovie() {
        List<ScreenScheduleDTO.ReadScreenMovieDTO> readScreenScheduleDTOList = screeningScheduleCustomRepository.readScreenMovie();


        for (ScreenScheduleDTO.ReadScreenMovieDTO readScreenScheduleDTO : readScreenScheduleDTOList) {
            List<ScreenScheduleDTO.ReadScreenScheduleInfoDTO> readScreenScheduleInfoDTOList;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date currentTime = new Date();
            System.out.println("simpleDateFormat.format(new Date()) = " + simpleDateFormat.format(new Date()));
            try {
                currentTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Date finalCurrentTime = currentTime;

            readScreenScheduleInfoDTOList = screeningScheduleCustomRepository.readScreenSchedule(readScreenScheduleDTO.getMovieId()).stream()
                    .map(readScreenScheduleInfoDTO -> {
                                try {

                                    return ScreenScheduleDTO.ReadScreenScheduleInfoDTO.builder()
                                            .screenScheduleId(readScreenScheduleInfoDTO.getScreenScheduleId())
                                            .theaterName(readScreenScheduleInfoDTO.getTheaterName())
                                            .theaterFloor(readScreenScheduleInfoDTO.getTheaterFloor())
                                            .startTime(readScreenScheduleInfoDTO.getStartTime())
                                            .endTime(readScreenScheduleInfoDTO.getEndTime())
                                            .remainSeat(readScreenScheduleInfoDTO.getRemainSeat())
                                            .salePolicyName(readScreenScheduleInfoDTO.getSalePolicyName())
                                            .salePolicyValue(readScreenScheduleInfoDTO.getSalePolicyValue())
                                            .isClosed((simpleDateFormat.parse(readScreenScheduleInfoDTO.getStartTime()).compareTo(finalCurrentTime))==-1?true:false)
                                            .build();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return readScreenScheduleInfoDTO;
                            }
                    )
                    .sorted(Comparator.comparing(ScreenScheduleDTO.ReadScreenScheduleInfoDTO::getTheaterName)
                            .thenComparing(ScreenScheduleDTO.ReadScreenScheduleInfoDTO::getTheaterFloor))
                    .collect(Collectors.toList());

            readScreenScheduleDTO.setReadScreenScheduleInfoDTOList(readScreenScheduleInfoDTOList);
        }

        return readScreenScheduleDTOList;
    }

}
