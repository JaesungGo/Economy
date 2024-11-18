package org.hackathon.economy.batch.tasklet;

//import org.hackathon.economy.batch.domain.DailyInterest;
import lombok.extern.log4j.Log4j2;
import org.hackathon.economy.account.domain.Account;
import org.hackathon.economy.account.domain.DailyInterest ;
import org.hackathon.economy.account.repository.AccountRepositoryInterface;
import org.hackathon.economy.account.repository.DailyInterestRepositoryInterface;
import org.hackathon.economy.account.repository.InterestRepositoryInterface;
import org.hackathon.economy.member.domain.Member;
import org.hackathon.economy.member.repository.MemberRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Log4j2
@Component("dailyInterestTasklet")
public class DailyInterestTasklet implements Tasklet {

    private final InterestRepositoryInterface interestRepositoryInterface;
    private final DailyInterestRepositoryInterface dailyInterestRepositoryInterface;
    private final AccountRepositoryInterface accountRepositoryInterface;
    private final MemberRepository memberRepository;

    @Autowired
    public DailyInterestTasklet(InterestRepositoryInterface interestRepositoryInterface, DailyInterestRepositoryInterface dailyInterestRepositoryInterface, AccountRepositoryInterface accountRepositoryInterface, MemberRepository memberRepository) {
        this.interestRepositoryInterface = interestRepositoryInterface;
        this.dailyInterestRepositoryInterface = dailyInterestRepositoryInterface;
        this.accountRepositoryInterface = accountRepositoryInterface;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional // 전체 메서드를 하나의 트랜잭션으로 묶음
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("-------------------DailyInterestTasklet started-------------------");

        interestRepositoryInterface.findAll().forEach(interest -> {

            Account account = accountRepositoryInterface.findAccountByAccountNo(interest.getAccount().getAccountNo());
            Member member = account.getMember();
            //log.info("-------------------Account: {}", account);
            //log.info("-------------------Member: {}", member);
            DailyInterest dailyInterest = new DailyInterest(); // 새로운 DailyInterest 생성 및 저장

            dailyInterest.setAccount(account); // 계좌 번호
            dailyInterest.setTodayBalance(account.getAccountBalance()); // 당일 잔고
            dailyInterest.setTodayGrade(member.getMemberGrade()); // 당일 등급

            dailyInterest.setCurrentDaily(interest.getCurrentDaily());  // 일일 이자 적용 여부
            dailyInterest.setCurrentWeekly(interest.getCurrentWeekly()); // 주간 이자 적용 여부
            dailyInterest.setCurrentMonthly(interest.getCurrentMonthly()); // 월간 이자 적용 여부

            // 당일 이자 = 기본 이율 + 등급에 따른 이율 + (일일 퀘스트 보상 + 주간 퀘스트 보상 + 월간 퀘스트 보상)
            double todayRate = 2.0; // 기본 이율
            todayRate += dailyInterest.getTodayGrade() * 0.5; // 등급에 따른 이율
            if(dailyInterest.getCurrentDaily()) {
                todayRate += 0.2;
            }
            if(dailyInterest.getCurrentWeekly()) {
                todayRate += 0.5;
            }
            if(dailyInterest.getCurrentMonthly()) {
                todayRate += 0.3;
            }

            dailyInterest.setTodayRate(todayRate); // 당일 이자율
            account.setAccountRate(todayRate); // Account 테이블의 account_date도 업데이트

            Long todayInterest = (long) (dailyInterest.getTodayBalance() * todayRate / 100 / 365);
            dailyInterest.setTodayInterest(todayInterest); // 당일 이자
            account.setAccountBalance(account.getAccountBalance() + todayInterest); // Account 테이블의 account_balance도 업데이트

            // 결과가 있으면 Optional<DailyInterest>, 없으면 Optional.empty() 반환
            LocalDate yesterday = LocalDate.now().minusDays(1);
            Optional<DailyInterest> yesterdayInterest = dailyInterestRepositoryInterface.findDailyInterestByAccount_AccountNoAndTodayDate(account.getAccountNo(), yesterday);
            yesterdayInterest.ifPresentOrElse(
                    dailyInterestFromYesterday -> {
                        // 어제의 이자 누적량이 있으면, 오늘의 이자와 더해서 누적
                        dailyInterest.setTotalInterest(dailyInterestFromYesterday.getTotalInterest() + todayInterest);
                    },
                    () -> {
                        // 어제의 이자 정보가 없으면, 오늘의 이자만 적용
                        dailyInterest.setTotalInterest(todayInterest);
                    }
            );

            dailyInterest.setTodayDate(LocalDate.now()); // 오늘 날짜

            dailyInterestRepositoryInterface.save(dailyInterest); // DB에 저장
            log.info("New Daily_Interest record inserted");
        });

        return RepeatStatus.FINISHED; // 모든 작업이 완료되면 RepeatStatus.FINISHED를 반환
    }
}
