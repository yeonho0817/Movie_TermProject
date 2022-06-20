package com.term.movie.repository;

import com.term.movie.domain.Ticketing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketingRepository extends JpaRepository<Ticketing, Long> {

    @Query("select t  FROM Ticketing t LEFT JOIN Member m on m = t.member where m.id=:memberPrivateId")
    List<Ticketing> findTicketingByMemberId(@Param("memberPrivateId") Long memberPrivateId);
}
