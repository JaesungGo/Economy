package org.hackathon.economy.batch.tasklet;

import lombok.extern.log4j.Log4j2;
import org.hackathon.economy.quest.domain.Quest;
import org.hackathon.economy.quest.repository.QuestRepositoryInterface;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Component("weeklyQuestTasklet")
public class WeeklyQuestTasklet implements Tasklet {
    private final QuestRepositoryInterface questRepositoryInterface;

    @Autowired
    public WeeklyQuestTasklet(QuestRepositoryInterface questRepositoryInterface) {
        this.questRepositoryInterface = questRepositoryInterface;
    }

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<Quest> preparedQuestList = questRepositoryInterface.findQuestsByIsActiveAndQuestTypeOrderByQuestFrequencyAsc(false, 1);
        List<Quest> activatedQuestList = questRepositoryInterface.findQuestsByIsActiveAndQuestTypeOrderByQuestFrequencyAsc(true, 1);
        for(int i=0; i < 3 ; i++) {
            activatedQuestList.get(i).setIsActive(false);
            preparedQuestList.get(i).setIsActive(true);
            preparedQuestList.get(i).setQuestFrequency(preparedQuestList.get(i).getQuestFrequency() + 1);
        }

        return RepeatStatus.FINISHED; // 모든 작업이 완료되면 RepeatStatus.FINISHED를 반환
    }
}
