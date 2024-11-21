package org.hackathon.economy.quest.repository;

import jakarta.persistence.LockModeType;
import org.hackathon.economy.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberAuthRepository extends JpaRepository<Member, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Member m WHERE m.memberNo = :memberNo")
    Optional<Member> findByIdForUpdate(@Param("memberNo") Long memberNo);
}