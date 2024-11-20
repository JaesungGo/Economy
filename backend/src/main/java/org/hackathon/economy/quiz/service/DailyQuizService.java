package org.hackathon.economy.quiz.service;

import org.hackathon.economy.quiz.domain.DailyQuiz;
import org.hackathon.economy.quiz.domain.Quiz;
import org.hackathon.economy.quiz.repository.DailyQuizRepository;
import org.hackathon.economy.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class DailyQuizService {

    private final QuizRepository quizRepository;
    private final DailyQuizRepository dailyQuizRepository;

    public DailyQuizService(QuizRepository quizRepository, DailyQuizRepository dailyQuizRepository) {
        this.quizRepository = quizRepository;
        this.dailyQuizRepository = dailyQuizRepository;
    }

    // 새로운 퀴즈 선택 및 업데이트 (시간기준 --> 추후 localDateTime 을 없애야한다!!
    @Transactional
    public void updateDailyQuiz() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current time: " + now);

//        // 모든 퀴즈 목록에서 무작위로 하나를 선택
//        List<Quiz> allQuizzes = quizRepository.findAll();
        // `quiz_cumulated`가 가장 적은 퀴즈들 가져오기
        List<Quiz> leastCumulatedQuizzes = quizRepository.findQuizzesWithMinCumulated();

        if (!leastCumulatedQuizzes.isEmpty()) {
            // 무작위로 하나의 퀴즈 선택
            Quiz randomQuiz = leastCumulatedQuizzes.get(new Random().nextInt(leastCumulatedQuizzes.size()));

            // 오늘의 퀴즈 업데이트
            DailyQuiz dailyQuiz = new DailyQuiz();
            dailyQuiz.setQuiz(randomQuiz);
//            dailyQuiz.setQuizDate(LocalDate.now());
            dailyQuiz.setQuizDateTime(now);

            dailyQuizRepository.save(dailyQuiz);
            System.out.println("Updated quiz at time: " + now + ", Quiz: " + randomQuiz.getQuizTitle());
        }
    }


    // 오늘의 퀴즈 가져오기
    public Quiz getTodayQuiz() {
        Long quizPk = dailyQuizRepository.findLatestQuizPk();
        return quizRepository.findQuizById(quizPk);
    }
}
