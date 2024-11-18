package org.hackathon.economy.account.repository;

import org.hackathon.economy.account.domain.DailyInterest ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyInterestRepositoryInterface extends JpaRepository<DailyInterest, Long> {
    Optional<DailyInterest> findDailyInterestByAccount_AccountNoAndTodayDate(Long accountNo, LocalDate date); // 특정 컬럼으로 검색할 수 있는 메서드 정의 (예: Account를 기준으로 검색)
}

