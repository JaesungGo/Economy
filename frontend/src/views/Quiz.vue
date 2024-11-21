<template>
    <div class="quiz-container">
        <!-- 퀴즈 완료 여부 확인 -->
        <div v-if="hasCompleted">
            <div class="quiz-completed">
                <p>오늘의 퀴즈를 이미 완료하셨습니다.</p>
            </div>
        </div>

        <!-- 퀴즈 진행 화면 -->
        <div v-else>
            <div v-if="quiz">
                <h2>{{ quiz.quizTitle }}</h2>
                <p>{{ quiz.quizDesc }}</p>

                <!-- 퀴즈 옵션 렌더링 -->
                <div v-for="option in options" :key="option.optionId" class="quiz-option">
                    <input type="radio" :id="'option-' + option.optionId" :value="option.optionId" v-model="selectedOption" />
                    <label :for="'option-' + option.optionId">
                        {{ option.optionText }}
                    </label>
                </div>

                <!-- 제출 버튼 -->
                <button @click="submitAnswer" :disabled="!selectedOption">답안 제출하기</button>
            </div>

            <!-- 퀴즈 데이터가 없을 경우 -->
            <div v-else>
                <p>퀴즈 데이터를 불러오는 중입니다...</p>
            </div>
        </div>
    </div>
</template>

<script>
import { useRouter } from 'vue-router';

export default {
    emits: ['close'],
    setup() {
        const router = useRouter(); // Vue Router 인스턴스
        return { router };
    },
    data() {
        return {
            quiz: null, // 퀴즈 정보
            options: [], // 퀴즈 옵션
            selectedOption: null, // 선택된 옵션
            result: null, // 결과 (정답 여부)
            hasCompleted: false, // 완료 여부
        };
    },
    async created() {
        try {
            // 샘플 데이터로 대체
            await this.checkDailyQuizCompletion();
            if (!this.hasCompleted) {
                this.fetchQuizData();
            }
        } catch (error) {
            console.error('초기화 중 오류 발생:', error);
        }
    },
    methods: {
        // 샘플 데이터: 퀴즈 정보 가져오기
        fetchQuizData() {
            try {
                this.quiz = {
                    quizPk: 1,
                    quizTitle: '오늘의 퀴즈',
                    quizDesc: '다음 중 올바른 답을 선택하세요.',
                };
                this.options = [
                    { optionId: 1, optionText: '보기 1' },
                    { optionId: 2, optionText: '보기 2 (정답)' },
                    { optionId: 3, optionText: '보기 3' },
                    { optionId: 4, optionText: '보기 4' },
                ];
            } catch (error) {
                console.error('퀴즈 데이터를 가져오는 데 실패했습니다:', error);
                this.quiz = null;
            }
        },

        // 샘플 데이터: 퀴즈 완료 여부 확인
        checkDailyQuizCompletion() {
            try {
                // 기본값으로 미완료 상태 설정
                this.hasCompleted = false;
            } catch (error) {
                console.error('퀴즈 완료 여부 확인 실패:', error);
            }
        },

        // 답안 제출 처리
        submitAnswer() {
            if (!this.selectedOption) return;

            try {
                // 정답 검증: 샘플 데이터 기준으로 2번이 정답
                const isCorrect = this.selectedOption === 2;
                this.result = isCorrect;

                if (isCorrect) {
                    alert('축하합니다! 정답입니다. 대시보드로 이동합니다.');
                    this.hasCompleted = true; // 정답 시 완료 상태로 변경
                    this.router.push('/dashboard-default'); // 대시보드로 리디렉션
                } else {
                    alert('틀렸습니다. 다시 시도해보세요!');
                }
            } catch (error) {
                console.error('답안 제출 중 오류 발생:', error);
            }
        },

        // // ######################### AXIOS 통신이 된다면?!?@#?!@?#!@?#!@
        // methods: {
        // async fetchQuizData() {
        //   try {
        //     const { data: quiz } = await quizApi.getDailyQuiz();
        //     this.quiz = quiz;
        //     console.log('hello');

        //     const { data: options } =
        //       await quizApi.getQuizOptions(this.quiz.quizPk);
        //     this.options = options;
        //   } catch (error) {
        //     console.error('Failed to fetch quiz data:', error);
        //     this.quiz = null;
        //   }
        // },
        // async checkDailyQuizCompletion() {
        //   try {
        //     const { data: hasCompleted } =
        //       await quizApi.checkQuizCompletion(1); // memberNo = 1
        //     this.hasCompleted = hasCompleted;
        //   } catch (error) {
        //     console.error(
        //       'Failed to check quiz completion:',
        //       error
        //     );
        //   }
        // },
        // // 답안 제출 처리
        // async submitAnswer() {
        //   if (!this.selectedOption) return;

        //   try {
        //     const payload = {
        //       memberNo: 1,
        //       quizPk: this.quiz.quizPk,
        //       userAnswer: this.selectedOption,
        //     };

        //     const { data: isCorrect } =
        //       await quizApi.submitQuizAnswer(payload);
        //     this.result = isCorrect;

        //     // 정답일 경우 퀴즈 완료 상태 업데이트
        //     if (isCorrect) {
        //   alert(
        //         '축하합니다! 정답입니다. 대시보드로 이동합니다.'
        //       );
        //       this.hasCompleted = true; // 정답 시 완료 상태로 변경
        //       this.router.push('/dashboard-default'); // 대시보드로 리디렉션
        //     } else {
        //       alert('틀렸습니다. 다시 시도해보세요!');
        //     }
        //   } catch (error) {
        //     console.error('답안 제출 중 오류 발생:', error);
        //   }
        // },
    },
};
</script>

<style scoped>
/* Quiz.vue 높이를 카드 크기에 맞춤 */
:deep(.quiz-container) {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
}
.quiz-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
}

.quiz-option {
    margin-bottom: 12px;
}

button {
    padding: 10px 20px;
    color: white;
    background-color: #4caf50;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}
</style>
