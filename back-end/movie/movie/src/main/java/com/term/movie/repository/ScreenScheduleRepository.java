package com.term.movie.repository;

import com.term.movie.domain.ScreeningSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenScheduleRepository extends JpaRepository<ScreeningSchedule, Long> {
}
