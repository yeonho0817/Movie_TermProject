package com.term.movie.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rating")
public class Rating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ticketing ticketing;

    @Column(name = "rating_point")
    private Integer ratingPoint;

    private String contents;

    @Column(name = "likes_number")
    private Integer likesNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_datetime", columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date registerDatetime;

    public void updateLikesNumber() { this.likesNumber++; }
    public void updateRatingPoint(Integer ratingPoint) { this.ratingPoint = ratingPoint; }
    public void updateContents(String contents) { this.contents = contents; }
}
