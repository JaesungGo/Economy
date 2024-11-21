package org.hackathon.economy.quest.repository;

import org.hackathon.economy.member.domain.Member;
import org.hackathon.economy.quest.domain.Quest;
import org.hackathon.economy.quest.domain.QuestAchieve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestAchieveRepository extends JpaRepository<QuestAchieve, Long> {

    @Query(value = """
        SELECT COUNT(*)
        FROM quest_achieve
        WHERE member_no = :memberNo
          AND quest_no = :questNo
          AND achieve_date_time BETWEEN (NOW() - INTERVAL 30 SECOND) AND NOW()
        """, nativeQuery = true)
    Integer countRecentAchievement(@Param("memberNo") Long memberNo, @Param("questNo") Long questNo);

    boolean existsByQuestAndMember(Quest quest, Member member);
}
