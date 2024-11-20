package org.hackathon.economy.account.repository;

import org.hackathon.economy.account.domain.Interest ;
import org.springframework.data.jpa.repository.JpaRepository;

// UserRepositry는 JpaRepository 인터페이스를 상속방아 기본 CRUD 작업을 지원
// JpaRepository<User, Long>와 같이 엔티티 타입(User)과 기본 키 타입(Long)을 지정해 두면,
// findAll() 메서드를 통해 자동으로 user 테이블을 조회하여 모든 레코드를 가져올 수 있음
public interface InterestRepositoryInterface extends JpaRepository<Interest, Long> {
}

