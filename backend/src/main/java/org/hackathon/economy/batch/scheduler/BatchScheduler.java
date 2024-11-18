package org.hackathon.economy.batch.scheduler;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Log4j2
@Configuration
@EnableScheduling  // 스케줄링 기능 활성화 애노테이션
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job dailyUpdateInterestJob;
    private final Job dailyInsertInterestJob;
    private final Job weeklyUpdateInterestJob;
    private final Job monthlyUpdateInterestJob;

    @Autowired
    public BatchScheduler(JobLauncher jobLauncher, Job dailyUpdateInterestJob, Job dailyInsertInterestJob ,Job weeklyUpdateInterestJob, Job monthlyUpdateInterestJob) {
        this.jobLauncher = jobLauncher;
        this.dailyUpdateInterestJob = dailyUpdateInterestJob;
        this.dailyInsertInterestJob = dailyInsertInterestJob;
        this.weeklyUpdateInterestJob = weeklyUpdateInterestJob;
        this.monthlyUpdateInterestJob = monthlyUpdateInterestJob;
    }

//    @Scheduled(cron = "0 0 12 * * ?") // 매일 12시 - cron 표현식: [초] [분] [시간] [일] [월] [요일]
//    public void runJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addDate("runDate", new Date())
//                .toJobParameters();
//
//        jobLauncher.run(updateInterestJob, jobParameters);
//    }

    // cron 표현식: [초] [분] [시간] [일] [월] [요일]
    // 매일 자정에 실행되는 작업
    //@Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 58 23 * * *")
    public void runDailyUpdateInterestJob() throws Exception {
        log.info("-------------------runDailyUpdateInterestJob-------------------");
        runJob(dailyUpdateInterestJob);
    }
    // 매일 0시 10분에 실행되는 작업
    //@Scheduled(cron = "0 10 0 * * *")
    @Scheduled(cron = "0 52 17 * * *")
    public void runDailyInsertInterestJob() throws Exception {
        log.info("-------------------runDailyInsertInterestJob-------------------");
        runJob(dailyInsertInterestJob);
    }

    // 매주 월요일 자정에 실행되는 작업
    //@Scheduled(cron = "0 0 0 * * 1")
    @Scheduled(cron = "0 1 0 * * *")
    public void runWeeklyUpdateInterestJob() throws Exception {
        log.info("-------------------runWeeklyUpdateInterestJob-------------------");
        runJob(weeklyUpdateInterestJob);
    }

    // 매월 1일 자정에 실행되는 작업
    //@Scheduled(cron = "0 0 0 1 * *")
    @Scheduled(cron = "0 1 0 * * *")
    public void runMonthlyUpdateInterestJob() throws Exception {
        log.info("-------------------runMonthlyUpdateInterestJob-------------------");
        runJob(monthlyUpdateInterestJob);
    }

    public void runJob(Job job) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder() // JobParameters에 run.id를 추가하여 매번 고유한 실행 ID를 생성해줌.(중복 실행 방지에 유용)
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters); // jobLauncher.run()을 사용해 job을 실행
    }
}

