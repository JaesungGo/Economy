<template>
  <div class="quest-container">
    <!-- 완료한 퀘스트 목록 -->
    <div class="card mb-3">
      <div class="card-header">완료한 퀘스트 목록</div>
      <ul class="list-group list-group-flush">
        <li class="list-group-item">-</li>
        <li class="list-group-item">-</li>
        <li class="list-group-item">-</li>
      </ul>
    </div>

    <!-- 찜한 퀘스트 목록 -->
    <div class="card mb-3">
      <div class="card-header">찜한 퀘스트 목록</div>
      <ul class="list-group list-group-flush">
        <li class="list-group-item">-</li>
        <li class="list-group-item">-</li>
        <li class="list-group-item">-</li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuestList',
};
</script>

<style scoped>
/* Flexbox로 2분할 */
.quest-container {
  display: flex;
  justify-content: space-between; /* 좌우로 공간 분배 */
  gap: 20px; /* 카드 사이 간격 */
}

/* 카드 크기 균일하게 조정 */
.card {
  flex: 1;
  max-width: 48%; /* 좌우 간격 조정 */
}

.card-header {
  background-color: #f8f9fa;
  font-weight: bold;
  text-align: center;
}

.list-group-item {
  text-align: center;
}
</style>
