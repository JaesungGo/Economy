package org.hackathon.economy.quest.repository;

import org.hackathon.economy.account.domain.Interest;
import org.hackathon.economy.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    @Query("SELECT i FROM Interest i WHERE i.account.member = :member")
    Optional<Interest> findByMember(@Param("member") Member member);
}