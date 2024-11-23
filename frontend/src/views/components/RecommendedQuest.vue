<template>
    <div class="card recommended-quest text-center p-3 mb-4">
        <h5 class="card-title">오늘의 추천 퀘스트 📋</h5>
        <p class="card-text">쌀쌀한 오늘, 카페에서 텀블러로 따뜻한 커피를 테이크 아웃하는 것은 어떨까요?</p>
    </div>
</template>

<script>
export default {
    name: 'RecommendedQuest',
};
</script>

<style scoped>
/* 부트스트랩 배경 색상이 아닌 커스텀 색상을 사용 */
.recommended-quest {
    background-color: #d1e7dd; /* 연한 녹색 배경 */
    border-color: #badbcc; /* 테두리 색 */
    color: #0f5132; /* 짙은 녹색 텍스트 */
}
</style>
