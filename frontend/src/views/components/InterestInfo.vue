<template>
  <div class="interest-info">
    <span class="info-icon" @click="toggleModal">?</span>

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
  </div>
</template>

<script>
export default {
  name: 'InterestInfo',
  data() {
    return {
      showModal: false, // 모달 표시 여부
    };
  },
  methods: {
    toggleModal() {
      this.showModal = !this.showModal; // 모달 표시 상태 토글
    },
  },
};
</script>

<style scoped>
/* 스타일링 */

.interest-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}
.info-icon {
  background-color: yellow;
  border-radius: 50%;
  padding: 5px;
  margin-right: 10px;
  font-weight: bold;
  cursor: pointer;
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
