<script setup>
import InterestInfo from './InterestInfo.vue';
</script>

<template>
  <div class="card account-balance-card text-center mb-4">
    <!-- 클래스 추가 -->
    <div class="card-body">
      <!-- InterestInfo 컴포넌트를 포함 -->
      <InterestInfo />

      <h5 class="card-title mt-3">
        000님의 현재 이율은 <strong>+2.5%</strong>입니다.
      </h5>
      <h2 class="display-4">10,000,000원</h2>
      <p class="text-muted">+124.23922434원 (금일 벌어들인 이자)</p>

      <div class="d-flex justify-content-center mt-4">
        <button class="btn btn-primary mx-2" @click="deposit">입금</button>
        <button class="btn btn-danger mx-2" @click="withdraw">출금</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.account-balance-card {
  background-color: #f8f9fa; /* 밝은 회색 (부트스트랩의 light 회색과 유사) */
}
.card-title {
  font-size: 1.25rem;
}
.display-4 {
  font-size: 2.5rem;
  font-weight: bold;
}
</style>
