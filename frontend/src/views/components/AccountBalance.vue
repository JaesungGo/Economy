<template>
  <div class="card account-balance-card text-center mb-4">
    <!-- 카드 본문 -->
    <div class="card-body">
      <!-- 이율 정보 아이콘 -->
      <div class="interest-info">
        <span class="info-icon" @click="toggleModal">?</span>
      </div>

      <!-- 모달 -->
      <div v-if="showModal" class="modal-backdrop" @click.self="toggleModal">
        <div class="modal-content">
          <h3>이율 설명</h3>
          <p>
            이율은 예금액에 대해 발생하는 금액의 비율입니다. 예를 들어, 이율이
            2.5%라면 1,000,000원에 대해 25,000원의 이자가 발생합니다.
          </p>
          <button class="close-button" @click="toggleModal">닫기</button>
        </div>
      </div>

      <!-- 현재 이율 정보 -->
      <h5 class="card-title mt-3">
        000님의 현재 이율은 <strong>+2.5%</strong>입니다.
      </h5>
      <h2 class="display-4">10,000,000원</h2>
      <p class="text-muted">+124.23922434원 (금일 벌어들인 이자)</p>

      <!-- 입금 및 출금 버튼 -->
      <div class="d-flex justify-content-center mt-4">
        <button class="btn btn-primary mx-2" @click="deposit">입금</button>
        <button class="btn btn-danger mx-2" @click="withdraw">출금</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'InterestInfoCard',
  data() {
    return {
      showModal: false, // 모달 표시 여부
    };
  },
  methods: {
    toggleModal() {
      this.showModal = !this.showModal; // 모달 표시 상태 토글
    },
    deposit() {
      alert('입금 기능이 호출되었습니다.');
    },
    withdraw() {
      alert('출금 기능이 호출되었습니다.');
    },
  },
};
</script>

<style scoped>
/* 카드 스타일 */
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

/* 이율 정보 스타일 */
.interest-info {
  display: flex;
  align-items: center;
  position: relative;
}

.info-icon {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 32px; /* 아이콘 크기 */
  height: 32px;
  border-radius: 50%; /* 원형으로 만들기 */
  background-color: #4caf50; /* 배경색 (녹색) */
  color: white; /* 텍스트 색상 */
  font-size: 1.2rem; /* 글자 크기 */
  font-weight: bold; /* 글자 굵기 */
  cursor: pointer; /* 클릭 가능 표시 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 약간의 그림자 효과 */
  transition: background-color 0.3s ease; /* 배경색 전환 효과 */
}

.info-icon:hover {
  background-color: #45a049; /* 호버시 조금 더 어두운 녹색 */
}

/* 모달 스타일 */
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  width: 300px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-content h3 {
  margin-bottom: 10px;
}

.close-button {
  margin-top: 20px;
  padding: 10px 20px;
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}
.close-button:hover {
  background-color: #0056b3;
}
</style>
