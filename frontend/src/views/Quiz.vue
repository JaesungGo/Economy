<template>
    <div class="quiz-container">
        <button class="close-btn" @click="close">X</button>
        <!-- Quiz Completion -->
        <div v-if="hasCompleted">
            <div class="quiz-completed">
                <p>오늘의 퀴즈를 이미 완료하셨습니다.</p>
            </div>
        </div>
        <!-- Quiz In Progress -->
        <div v-else>
            <div v-if="quiz">
                <h2>{{ quiz.quizTitle }}</h2>
                <p>{{ quiz.quizDesc }}</p>
                <!-- Quiz Options -->
                <div v-for="option in options" :key="option.optionId" class="quiz-option">
                    <input type="radio" :id="'option-' + option.optionId" :value="option.optionId" v-model="selectedOption" />
                    <label :for="'option-' + option.optionId">{{ option.optionText }}</label>
                </div>
                <!-- Submit Button -->
                <button @click="submitAnswer" :disabled="!selectedOption">답안 제출하기</button>
            </div>
            <div v-else>
                <p>퀴즈 데이터를 불러오는 중입니다...</p>
            </div>
        </div>
    </div>
</template>
<script>
import quizApi from '@/api/quizApi';
import Swal from 'sweetalert2';
export default {
    emits: ['close', 'redirect'], // Emit close and redirect events
    data() {
        return {
            quiz: null,
            options: [],
            selectedOption: null,
            result: null,
            hasCompleted: false,
        };
    },
    async created() {
        try {
            await this.checkDailyQuizCompletion();
            if (!this.hasCompleted) {
                await this.fetchQuizData();
            }
        } catch (error) {
            console.error('Initialization error:', error);
        }
    },
    mounted() {
        // Add event listener for "Esc" key to close the modal
        document.addEventListener('keydown', this.handleKeydown);
    },
    beforeUnmount() {
        // Remove event listener when component is destroyed
        document.removeEventListener('keydown', this.handleKeydown);
    },
    methods: {
        async fetchQuizData() {
            try {
                const { data: quiz } = await quizApi.getDailyQuiz();
                this.quiz = quiz;
                const { data: options } = await quizApi.getQuizOptions(this.quiz.quizPk);
                this.options = options;
            } catch (error) {
                console.error('Failed to fetch quiz data:', error);
                this.quiz = null;
            }
        },
        async checkDailyQuizCompletion() {
            try {
                const memberNo = 1;
                const { data: hasCompleted } = await quizApi.checkQuizCompletion(memberNo);
                this.hasCompleted = hasCompleted;
            } catch (error) {
                console.error('Failed to check completion status:', error);
            }
        },
        async submitAnswer() {
            if (!this.selectedOption) return;
            try {
                const payload = {
                    memberNo: 1,
                    quizPk: this.quiz.quizPk,
                    userAnswer: this.selectedOption,
                };
                const { data: isCorrect } = await quizApi.submitQuizAnswer(payload);
                this.result = isCorrect;
                if (isCorrect) {
                    await Swal.fire('정답입니다. 홈으로 이동합니다.');
                    this.hasCompleted = true;
                    this.$emit('redirect'); // Emit redirect event to parent
                } else {
                    Swal.fire('다시 시도해보세요!');
                }
            } catch (error) {
                console.error('Submission error:', error);
                Swal.fire('에러', '서버 연결에 실패했습니다.', 'error');
            }
        },
        close() {
            this.$emit('close'); // Emit close event to parent
        },
        handleKeydown(event) {
            if (event.key === 'Escape') {
                this.close(); // Close modal on "Esc" key press
            }
        },
    },
};
</script>
<style scoped>
.quiz-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
    position: relative;
}
.close-btn {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: transparent;
    border: none;
    font-size: 18px;
    cursor: pointer;
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
