package org.hackathon.economy.account.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hackathon.economy.account.domain.Account;
import org.hackathon.economy.account.domain.Transaction;
import org.hackathon.economy.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class AccountRepository {

    @PersistenceContext
    private EntityManager em;

    // 계좌 생성
    public Account create(Account account) {
        em.persist(account);
        return account;
    }

    // 멤버로 계좌 찾기
    public Account findByMember(Member member) {
        try {
            TypedQuery<Account> query = em.createQuery("select a from Account a where a.member = :member", Account.class);
            query.setParameter("member", member);
            return query.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    // 입금(잔고 반환)
    public Long deposit(Account account, Long amount) {
        Long newBalance = account.deposit(amount);

        // 거래 기록 생성
        Transaction transaction = Transaction.builder()
                .transactionPrice(amount)
                .transactionDate(new Date())
                .transactionType(0) // 입금 타입
                .transactionBalance(newBalance)
                .build();
        // 거래 기록 저장
        account.getTransactions().add(transaction);

        return newBalance;
    }

    // 출금(잔고 반환 / 잔액 부족 시 null 반환)
    public Long withdraw(Account account, Long amount) {
        Long newBalance = account.withdraw(amount);

        // 거래 기록 생성
        Transaction transaction = Transaction.builder()
                .transactionPrice(amount)
                .transactionDate(new Date())
                .transactionType(1) // 출금 타입
                .transactionBalance(newBalance)
                .build();
        // 거래 기록 저장
        account.getTransactions().add(transaction);

        return newBalance;
    }
}
