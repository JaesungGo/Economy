<script setup>
import Swal from 'sweetalert2';
import { ref, computed } from 'vue';

// 샘플 퀘스트 데이터
const quests = ref([
    { questNo: 1, questType: 0, questContent: '일간 퀘스트 1', questPoint: 10 },
    { questNo: 2, questType: 1, questContent: '주간 퀘스트 1', questPoint: 20 },
    { questNo: 3, questType: 2, questContent: '월간 퀘스트 1', questPoint: 30 },
    { questNo: 4, questType: 0, questContent: '일간 퀘스트 2', questPoint: 15 },
    { questNo: 5, questType: 1, questContent: '주간 퀘스트 2', questPoint: 25 },
]);

// 선택된 퀘스트 타입 (기본값: null => 모든 퀘스트)
const selectedQuestType = ref(null);

// 선택된 타입에 따른 퀘스트 필터링
const filteredQuests = computed(() => {
    if (selectedQuestType.value === null) {
        return quests.value; // 선택된 타입이 없으면 모든 퀘스트 표시
    }
    return quests.value.filter((quest) => quest.questType === selectedQuestType.value);
});

// 퀘스트 타입 토글 핸들러
const toggleQuestType = (type) => {
    selectedQuestType.value = selectedQuestType.value === type ? null : type; // 동일 버튼 클릭 시 전체 보기로 전환
};

// 퀘스트 인증 처리 함수
const handleQuestAchieve = async (questContent, questId) => {
    try {
        const result = await Swal.fire({
            title: `${questContent}을(를) 인증하시겠습니까?`,
            text: '이 작업은 되돌릴 수 없습니다!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            customClass: {
                confirmButton: 'swal-confirm-btn', // 커스텀 클래스 이름 변경
                cancelButton: 'swal-cancel-btn', // 커스텀 클래스 이름 변경
            },
            buttonsStyling: false, // SweetAlert2 기본 스타일 비활성화
        });

        if (result.isConfirmed) {
            console.log(`퀘스트 ${questId} 인증 진행`);
            await Swal.fire('인증 완료!', '퀘스트 인증이 성공적으로 완료되었습니다.', 'success');
        }
    } catch (error) {
        await Swal.fire('에러', '서버 오류로 인해 인증이 실패했습니다.', 'error');
    }
};
</script>

<template>
    <div class="card">
        <div class="card-header pb-0 d-flex justify-content-between align-items-center">
            <h6>오늘의 퀘스트</h6>
            <!-- 퀘스트 타입 필터링 버튼 -->
            <div class="btn-group">
                <button
                    class="btn btn-outline-secondary btn-xs py-1 px-3 custom-hover"
                    :class="{ 'btn-success text-white': selectedQuestType === null }"
                    @click="toggleQuestType(null)"
                >
                    전체
                </button>
                <button class="btn btn-outline-secondary btn-xs py-1 px-3 custom-hover" :class="{ 'btn-success text-white': selectedQuestType === 0 }" @click="toggleQuestType(0)">
                    일간
                </button>
                <button class="btn btn-outline-secondary btn-xs py-1 px-3 custom-hover" :class="{ 'btn-success text-white': selectedQuestType === 1 }" @click="toggleQuestType(1)">
                    주간
                </button>
                <button class="btn btn-outline-secondary btn-xs py-1 px-3 custom-hover" :class="{ 'btn-success text-white': selectedQuestType === 2 }" @click="toggleQuestType(2)">
                    월간
                </button>
            </div>
        </div>
        <div class="card-body px-0 pt-0 pb-2">
            <div class="table-responsive p-0">
                <table class="table align-items-center mb-0">
                    <thead>
                        <tr>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">퀘스트</th>
                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">타입</th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">포인트</th>
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="quest in filteredQuests" :key="quest.questNo">
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <!-- 퀘스트 종류에 따른 이미지 -->
                                        <img src="../../assets/img/team-2.jpg" class="avatar avatar-sm me-3" alt="quest" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <!-- 퀘스트 내용 -->
                                        <h6 class="mb-0 text-sm">{{ quest.questContent }}</h6>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0 text-secondary">
                                    {{ quest.questType === 0 ? '일간' : quest.questType === 1 ? '주간' : '월간' }}
                                </p>
                            </td>
                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">{{ quest.questPoint }}P</span>
                            </td>
                            <td class="align-middle text-center text-sm">
                                <button class="badge bg-gradient-success border-0" @click="handleQuestAchieve(quest.questContent, quest.questNo)">인증</button>
                            </td>
                        </tr>
                        <tr v-if="filteredQuests.length === 0">
                            <td colspan="4" class="text-center">해당 퀘스트가 없습니다.</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<style>
/* 기본 상태를 유지하며 호버 시 효과 적용 */
.custom-hover:hover {
    background-color: #ffffff !important; /* 흰 배경 */
    color: #40a578 !important; /* success 색상 */
    border-color: #40a578 !important; /* 테두리 색 변경 */
    transition: all 0.3s ease; /* 부드러운 애니메이션 */
}

/* 클릭된 상태 유지 */
.btn-success.text-white {
    background-color: #40a578 !important; /* 배경 초록색 */
    color: #ffffff !important; /* 흰 글자색 */
    border-color: #40a578 !important; /* 테두리 초록색 */
}

/* SweetAlert2 확인 버튼 */
.swal-confirm-btn {
    background: linear-gradient(90deg, #40a578, #3085d6); /* 그라디언트 색상 */
    color: white;
    font-size: 14px;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
    margin-right: 10px; /* 버튼 간격 */
}

.swal-confirm-btn:hover {
    background: linear-gradient(90deg, #3085d6, #40a578); /* 호버 시 색상 반전 */
    opacity: 0.9;
}

/* SweetAlert2 취소 버튼 */
.swal-cancel-btn {
    background: linear-gradient(90deg, #d33, #b52c2c); /* 그라디언트 색상 */
    color: white;
    font-size: 14px;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.swal-cancel-btn:hover {
    background: linear-gradient(90deg, #b52c2c, #d33); /* 호버 시 색상 반전 */
    opacity: 0.9;
}
</style>
