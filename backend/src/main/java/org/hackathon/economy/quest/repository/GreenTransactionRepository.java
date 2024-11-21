package org.hackathon.economy.quest.repository;

import org.hackathon.economy.greenttransaction.domain.GreenTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GreenTransactionRepository extends JpaRepository<GreenTransaction, Long> {
    @Query("SELECT COUNT(g) FROM GreenTransaction g WHERE g.transactionCategory = :category")
    Long countByTransactionCategory(@Param("category") String category);
}