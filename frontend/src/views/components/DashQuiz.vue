<template>
    <div class="quiz-status">
        <!-- 퀴즈를 풀지 않은 경우 -->
        <div v-if="!hasCompletedQuiz">
            <p>아직 퀴즈를 풀지 않으셨군요!</p>
            <button @click="openQuizModal" class="btn btn-primary">퀴즈 풀기</button>
        </div>

        <!-- 퀴즈를 완료한 경우 -->
        <div v-else>
            <p>오늘의 일일 퀘스트를 완료했습니다!</p>
        </div>

        <!-- 퀴즈 모달 -->
        <teleport to="body">
            <div v-if="showQuizModal" class="quiz-modal">
                <quiz @close="closeQuizModal" />
            </div>
        </teleport>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Swal from 'sweetalert2';
import quizApi from '@/api/quizApi';
import memberApi from '@/api/memberApi';
import Quiz from '../Quiz.vue';

const hasCompletedQuiz = ref(false); // 퀴즈 완료 상태
const showQuizModal = ref(false); // 퀴즈 모달 상태
const memberNo = ref(null); // 동적으로 가져올 memberNo

// 회원 정보 조회 및 퀴즈 완료 여부 확인
const fetchQuizStatus = async () => {
    try {
        // 회원 정보 조회
        const memberData = await memberApi.getMember();
        memberNo.value = memberData?.memberNo;

        if (!memberNo.value) {
            throw new Error('회원 번호를 가져오지 못했습니다.');
        }

        // 퀴즈 완료 여부 확인
        const response = await quizApi.checkQuizCompletion(memberNo.value);
        hasCompletedQuiz.value = response?.data?.completed || false;
    } catch (error) {
        console.error('Error fetching quiz status:', error);
        Swal.fire('에러', '퀴즈 상태를 확인하지 못했습니다.', 'error');
    }
};

// 모달 열기
const openQuizModal = () => {
    showQuizModal.value = true;
};

// 모달 닫기
const closeQuizModal = () => {
    showQuizModal.value = false;
};

// DashQuiz 초기화
onMounted(() => {
    fetchQuizStatus();
});
</script>

<style scoped>
.quiz-status {
    text-align: center;
    padding: 20px;
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

.quiz-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}
</style>
