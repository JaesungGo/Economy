package org.hackathon.economy.member.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hackathon.economy.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getMemberNo();
    }

    public Member findOne(Long no) {
        return em.find(Member.class, no);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Member findByEmail(String email) {
        TypedQuery<Member> query = em.createQuery("select m from Member m where m.memberEmail = :email", Member.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    public void delete(Long no) {
        Member member = em.find(Member.class, no);  // 삭제할 엔티티 조회
        if (member != null) {
            em.remove(member);  // 엔티티 삭제
        }
    }
}
