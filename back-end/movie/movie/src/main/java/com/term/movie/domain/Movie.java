package com.term.movie.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "age_limit")
    private Integer ageLimit;

    private Integer runtime;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String contents;

    private Integer cost;

    private String image;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "opening_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date openingTime;



    @OneToMany(mappedBy = "movie")
    private List<MovieActor> movieActorList;

    @OneToMany(mappedBy = "movie")
    private List<ScreeningSchedule> screeningScheduleList;

}
