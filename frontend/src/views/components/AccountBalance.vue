<script setup>
import accountApi from '@/api/accountApi';
import InterestInfo from './InterestInfo.vue';
import { ref, computed, onMounted  } from 'vue';

const now = new Date(); // 현재 시각
const hours = now.getHours(); // 시 (0 ~ 23)
const minutes = now.getMinutes(); // 분 (0 ~ 59)

const accountObject = ref({
  accountBalance: 0, // 기본값
  accountRate: 0,    // 기본값
});
const myAccount = computed(() => accountObject.value);
const myBalance = computed(() => myAccount.value.accountBalance);
const myRate = computed(() => myAccount.value.accountRate);

// 이자 초기값 설정
const myTotalInterest = computed(() => 
  (myAccount.value.accountBalance * (myAccount.value.accountRate / 100 / 365))
);
const myCurrentInterest = computed(() => 
  (myAccount.value.accountBalance * (myAccount.value.accountRate / 100 / 365 / (24 * 60 * 60))) * (((hours * 60) + minutes) * 60)
);
const myInterest = computed(() => 
  (myAccount.value.accountBalance * (myAccount.value.accountRate / 100 / 365 / (24 * 60 * 60)))
);
const countInterest = ref(0);

// 매초마다 이자금을 더함
const startInterestCount = () => {
  const interval = setInterval(() => {
    if (countInterest.value < myTotalInterest.value) {
      countInterest.value += myInterest.value;
    } else {
      clearInterval(interval);  // 카운트가 myTotalInterest에 도달하면 타이머 종료
    }
  }, 1000);  // 1000ms(1초)마다 실행
};


const load = async () => {
  try {
    const accountData = await accountApi.findAccount();
    accountObject.value = accountData;
    console.log('accountObject: ', accountObject.value);
    console.log('accountObject.accountBalance: ', myAccount.value.accountRate);

    // 데이터 로드 후 countInterest를 초기화
    countInterest.value = myCurrentInterest.value;

  } catch (error) {
    console.error('Error finding account: ', error);
  }
};

// 컴포넌트가 마운트될 때 카운트다운 시작
onMounted(() => {
  startInterestCount();
});


load();
</script>

<template>
  <div class="card account-balance-card text-center mb-4">
    <!-- 클래스 추가 -->
    <div class="card-body">
      <!-- InterestInfo 컴포넌트를 포함 -->
      <InterestInfo />

      <h5 class="card-title mt-3">
        000님의 현재 이율은 <strong>{{ myRate }}%</strong>입니다.<br>
        
      </h5>
      <h2 class="display-4">{{ myBalance ? myBalance.toLocaleString() : 'N/A' }}원</h2>
      <!--<p class="text-muted">+124.23922434원 (금일 벌어들인 이자)</p>-->
      <div >
        <h3 class="text-color">+{{ countInterest.toFixed(4) }}원</h3>
        <p class="text-muted inline-container">(금일 벌어들인 이자)</p>
      </div>

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
.container {
    display: flex;
    justify-content: center; /* 중앙 정렬 */
    align-items: center; /* 세로 정렬 */
    gap: 8px; /* <h3>와 <p> 사이의 간격 */
}
.text-color {
    /*margin-top: 20px;*/ /* 위쪽 간격을 늘림 */
    /*font-size: 16px;*/ /* 글씨 크기 조정 (필요시 변경) */
    color: #088A68; /* 글씨 색상 유지 */
}
</style>
