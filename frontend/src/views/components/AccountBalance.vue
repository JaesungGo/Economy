<!-- <script setup>
import accountApi from '@/api/accountApi';
import memberApi from '@/api/memberApi';
import { ref, computed, onMounted  } from 'vue';


const now = new Date(); // 현재 시각
const hours = now.getHours(); // 시 (0 ~ 23)
const minutes = now.getMinutes(); // 분 (0 ~ 59)

const accountObject = ref({
  accountBalance: 0, // 기본값
  accountRate: 0, // 기본값
});
const myAccount = computed(() => accountObject.value);
const myBalance = computed(() => myAccount.value.accountBalance);
const myRate = computed(() => myAccount.value.accountRate);

const memberObject = ref({});
const myMember = computed(() => memberObject.value);

// 이자 초기값 설정
const myTotalInterest = computed(
  () =>
    myAccount.value.accountBalance * (myAccount.value.accountRate / 100 / 365)
);
const myCurrentInterest = computed(
  () =>
    myAccount.value.accountBalance *
    (myAccount.value.accountRate / 100 / 365 / (24 * 60 * 60)) *
    ((hours * 60 + minutes) * 60)
);
const myInterest = computed(
  () =>
    myAccount.value.accountBalance *
    (myAccount.value.accountRate / 100 / 365 / (24 * 60 * 60))
);
const countInterest = ref(0);


// 매초마다 이자금을 더함
const startInterestCount = () => {
  const interval = setInterval(() => {
    if (countInterest.value < myTotalInterest.value) {
      countInterest.value += myInterest.value;
    } else {
      clearInterval(interval); // 카운트가 myTotalInterest에 도달하면 타이머 종료
    }
  }, 1000); // 1000ms(1초)마다 실행
};

const load = async () => {
  try {
    const accountData = await accountApi.findAccount();
    accountObject.value = accountData;
    console.log('accountObject: ', accountObject.value);
    console.log('accountObject.accountBalance: ', myAccount.value.accountRate);

    const memberData = await memberApi.getMember();
    memberObject.value = memberData;
    console.log('memberObject: ', memberObject.value);
    console.log('memberObject.memberName: ', myMember.value.memberName);

    // 데이터 로드 후 countInterest를 초기화
    countInterest.value = myCurrentInterest.value;
  } catch (error) {
    console.error('Error finding account: ', error);
  }
};

// 컴포넌트가 마운트될 때 카운트다운 시작
onMounted(() => {
  load();

  startInterestCount();
});

</script>


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
            이율은 예금액에 대해 발생하는 금액의 비율입니다. <br>예를 들어, 이율이
            2.5%라면 1,000,000원에 대해 25,000원의 이자가 발생합니다.
          </p>
          <button class="close-button" @click="toggleModal">닫기</button>
        </div>
      </div>

      <!-- 현재 이율 정보 -->
      <h5 class="card-title mt-3">
        {{ myMember.memberName }}님의 현재 이율은 <strong>{{ myRate }}%</strong>입니다.<br>
      </h5>
      <h2 class="display-4">{{ accountBalance.toLocaleString() }}원</h2>
      <p class="text-muted">
        {{
          typeof dailyInterest === 'number'
            ? dailyInterest.toFixed(4)
            : '0.0000'
        }}원 (금일 벌어들인 이자)
      </p>

      <!-- 입금 및 출금 버튼 -->
      <div class="d-flex justify-content-center mt-4">
        <button class="btn btn-primary mx-2" @click="handleDeposit">
          입금
        </button>
        <button class="btn btn-danger mx-2" @click="handleWithdraw">
          출금
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from '@/api/accountApi';

export default {
  name: 'InterestInfoCard',
  data() {
    return {
      showModal: false, // 모달 표시 여부
      userName: '000', // 사용자 이름
      interestRate: '2.5', // 현재 이율
      accountBalance: 0, // 계좌 잔액
      dailyInterest: 0, // 금일 이자
    };
  },
  methods: {
    toggleModal() {
      this.showModal = !this.showModal; // 모달 표시 상태 토글
    },
    async handleDeposit() {
      const amount = prompt('입금할 금액을 입력하세요:');
      if (!amount || isNaN(amount) || Number(amount) <= 0) {
        alert('유효한 금액을 입력하세요.');
        return;
      }
      try {
        const response = await accountApi.deposit(Number(amount));
        alert(response); // 성공 메시지
        this.updateAccountInfo();
      } catch (error) {
        alert('입금에 실패했습니다: ' + error.response?.data || error.message);
      }
    },
    async handleWithdraw() {
      const amount = prompt('출금할 금액을 입력하세요:');
      if (!amount || isNaN(amount) || Number(amount) <= 0) {
        alert('유효한 금액을 입력하세요.');
        return;
      }
      try {
        const response = await accountApi.withdraw(Number(amount));
        alert(response); // 성공 메시지
        this.updateAccountInfo();
      } catch (error) {
        alert('출금에 실패했습니다: ' + error.response?.data || error.message);
      }
    },
    async updateAccountInfo() {
      try {
        const account = await accountApi.findAccount();
        this.accountBalance = account.accountBalance; // 계좌 잔액 업데이트
        this.dailyInterest = parseFloat(account.dailyInterest) || 0; // 숫자로 변환
      } catch (error) {
        alert('계좌 정보를 불러오지 못했습니다.');
      }
    },
  },
  async mounted() {
    // 컴포넌트가 로드되면 계좌 정보 로드
    await this.updateAccountInfo();
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

.container {
  display: flex;
  justify-content: center; /* 중앙 정렬 */
  align-items: center; /* 세로 정렬 */
  gap: 8px; /* <h3>와 <p> 사이의 간격 */
}
.text-color {
  /*margin-top: 20px;*/ /* 위쪽 간격을 늘림 */
  /*font-size: 16px;*/ /* 글씨 크기 조정 (필요시 변경) */
  color: #088a68; /* 글씨 색상 유지 */
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
  width: 700px;
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
