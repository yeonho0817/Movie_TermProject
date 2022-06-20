package com.term.movie.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "ticketing")
public class Ticketing {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ScreeningSchedule screeningSchedule;

    private Integer price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_datetime", columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date registerDatetime;

    //
    @OneToMany(mappedBy = "ticketing", cascade = CascadeType.ALL)
    List<TicketingSeat> ticketingSeatList;
}
