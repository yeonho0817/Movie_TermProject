package com.term.movie.repository;

import com.term.movie.domain.Member;
import com.term.movie.domain.Ticketing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends  JpaRepository<Member, Long> {

    Member findByMemberId(String memberId);

    Optional<Member> findById(Long id);

    Optional<Member> findByMemberIdAndMemberPw(String memberId, String memberPw);

    @Query("select t  FROM Ticketing t LEFT JOIN Member m on m = t.member where m.id=?1")
    List<Ticketing> findTicketingByMemberId(Long memberPrivateId);

}
