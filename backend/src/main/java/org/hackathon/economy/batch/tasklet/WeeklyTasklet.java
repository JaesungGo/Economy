package org.hackathon.economy.batch.tasklet;

import org.hackathon.economy.batch.domain.InterestRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("weeklyTasklet")
public class WeeklyTasklet implements Tasklet {

    private final InterestRepository interestRepository;

    @Autowired
    public WeeklyTasklet(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    @Transactional // 전체 메서드를 하나의 트랜잭션으로 묶음
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        interestRepository.findAll().forEach(interest -> {
            if (interest.getWeeklyQuest()) { // 위클리 퀘스트 달성 확인
                interest.setCurrentWeekly(true);
                interest.setWeeklyQuest(false);
                interestRepository.save(interest);
            }
        });

        return RepeatStatus.FINISHED; // 모든 작업이 완료되면 RepeatStatus.FINISHED를 반환
    }
}
