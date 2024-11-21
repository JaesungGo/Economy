package org.hackathon.economy.account.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hackathon.economy.account.domain.DailyInterestUtil;
import org.hackathon.economy.account.domain.Account;
import org.hackathon.economy.account.domain.DailyInterest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DailyInterestRepository {

    @PersistenceContext
    private EntityManager em;

    // 전체 내역 조회
    public List<DailyInterest> getAll(Account account) {
        try {
            TypedQuery<DailyInterest> query = em.createQuery("select d from DailyInterest d where d.account = :account", DailyInterest.class);
            query.setParameter("account", account);
            return query.getResultList();
        } catch(NoResultException e) {
            return null;
        }
    }

    // 누적이자 조회
    public Long getTotal(Account account) {
        try {
            TypedQuery<DailyInterest> query = em.createQuery(
                    "select d from DailyInterest d where d.account = :account order by d.dailyInterestNo desc",
                    DailyInterest.class
            );
            query.setParameter("account", account);
            query.setMaxResults(1); // 결과를 하나로 제한
            DailyInterest result = query.getSingleResult();
            return result.getTotalInterest();
        } catch(NoResultException e) {
            return null;
        }
    }


    // 매월 말일 월별 누적이자 조회
    public List<DailyInterestUtil> getMonthly(Account account) {
        try {
            TypedQuery<DailyInterestUtil> query = em.createQuery(
                    "SELECT d.todayBalance, d.todayRate, d.todayInterest, d.monthlyInterest ,d.totalInterest, d.todayGrade, d.todayDate FROM DailyInterest d " +
                            "WHERE d.todayDate IN (" +
                            "    SELECT MAX(subD.todayDate) " +
                            "    FROM DailyInterest subD " +
                            "    GROUP BY FUNCTION('YEAR', subD.todayDate), FUNCTION('MONTH', subD.todayDate)" +
                            ")",
                    DailyInterestUtil.class
            );
            return query.getResultList();
        } catch(NoResultException e) {
            return null;
        }
    }
}
