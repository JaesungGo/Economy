package org.hackathon.economy.batch.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyInterestRepository extends JpaRepository<DailyInterest, Long> {
    DailyInterest findDailyInterestByAccountNo(Long accountNo); // 특정 컬럼으로 검색할 수 있는 메서드 정의 (예: accountNo를 기준으로 검색)
}

