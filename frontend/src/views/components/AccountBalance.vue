<script setup>
import accountApi from '@/api/accountApi';
import memberApi from '@/api/memberApi';
import Swal from 'sweetalert2';
import { ref, computed, onMounted } from 'vue';

// 모달 표시 여부
const showModal = ref(false);
// const amount = ref('');
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

// 모달 토글 함수
const toggleModal = () => {
  showModal.value = !showModal.value;
};

// 입금 기능 함수 (SweetAlert 사용)
async function depositAmount() {
  try {
    // 사용자 입력 받기 (SweetAlert 사용)
    const { value: amount } = await Swal.fire({
      title: '입금하기',
      input: 'number',
      inputLabel: '입금할 금액을 입력하세요:',
      inputPlaceholder: '금액 입력',
      showCancelButton: true, // 취소 버튼 활성화
      confirmButtonText: '입금', // 확인 버튼 텍스트
      cancelButtonText: '취소', // 취소 버튼 텍스트
      inputValidator: (value) => {
        if (!value || isNaN(value) || value <= 0) {
          return '올바른 금액을 입력하세요.';
        }
      },
      customClass: {
        input: 'swal2-centered-input', // 입력 필드 중앙 정렬 클래스
        actions: 'swal2-centered-actions', // 버튼 중앙 정렬 클래스
        popup: 'swal2-centered-popup', // 팝업 자체 정렬 클래스
        confirmButton: 'swal2-button-confirm', // 입금 버튼 스타일
        cancelButton: 'swal2-button-cancel', // 취소 버튼 스타일
      },
    });

    // 사용자가 취소 버튼을 누른 경우 처리
    if (!amount) {
      return;
    }

    console.log('입금 금액:', amount);

    // API 호출
    const response = await accountApi.deposit(amount);
    console.log('API 응답:', response);

    // 응답 처리
    if (response.ok) {
      const data = await response.json(); // 응답 데이터를 JSON으로 파싱
      await Swal.fire({
        icon: 'success',
        title: '입금 성공!',
        text: `새로운 잔액: ${data.balance}원`,
        customClass: {
          title: 'swal2-centered-text',
          content: 'swal2-centered-text',
        },
      });
    } else {
      const error = await response.text();
      await Swal.fire({
        icon: 'error',
        title: '입금 실패',
        text: error,
        customClass: {
          title: 'swal2-centered-text',
          content: 'swal2-centered-text',
        },
      });
    }
  } catch (error) {
    console.error('Error:', error);
    await Swal.fire({
      icon: 'error',
      title: '오류 발생',
      text: '입금 처리 중 오류가 발생했습니다.',
      customClass: {
        title: 'swal2-centered-text',
        content: 'swal2-centered-text',
      },
    });
  }
}

// // 출금 기능 함수
// const withdraw = () => {
//   alert('출금 기능이 호출되었습니다.');
// };

// const deposit = async () => {
//   const amount = parseFloat(prompt('입금할 금액을 입력하세요:'));
//   if (!amount || isNaN(amount) || Number(amount) <= 0) {
//     alert('유효한 금액을 입력해주세요.');
//     return;
//   }

//   try {
//     const response = await accountApi.deposit(Number(amount));
//     const updatedAccount = response.data; // 서버에서 반환된 최신 계좌 정보
//     accountObject.value = updatedAccount; // 최신 데이터로 업데이트
//     alert(
//       `입금 성공! 현재 잔액: ${updatedAccount.accountBalance.toLocaleString()}원`
//     );
//   } catch (error) {
//     const errorMessage = error.response?.data || '입금에 실패했습니다.';
//     alert(errorMessage);
//   }
// };

// 출금 기능 함수
const withdraw = async () => {
  try {
    // 사용자 입력 받기 (SweetAlert 사용)
    const { value: amount } = await Swal.fire({
      title: '출금하기',
      input: 'number',
      inputLabel: '출금할 금액을 입력하세요:',
      inputPlaceholder: '금액 입력',
      showCancelButton: true,
      confirmButtonText: '출금',
      cancelButtonText: '취소',
      inputValidator: (value) => {
        if (!value || isNaN(value) || value <= 0) {
          return '유효한 금액을 입력해주세요.';
        }
      },
      customClass: {
        input: 'swal2-centered-input', // 입력 필드 중앙 정렬
        actions: 'swal2-centered-actions', // 버튼 중앙 정렬
        popup: 'swal2-centered-popup', // 팝업 중앙 정렬
      },
    });

    // 사용자가 취소를 누른 경우 처리
    if (!amount) return;

    console.log('출금 요청 금액:', amount);

    // API 호출
    const response = await accountApi.withdraw(Number(amount));
    const updatedAccount = response.data; // 서버에서 반환된 최신 계좌 정보
    accountObject.value = updatedAccount; // 최신 데이터로 업데이트

    // 성공 메시지
    await Swal.fire({
      icon: 'success',
      title: '출금 성공!',
      text: `출금이 완료되었습니다. 현재 잔액: ${updatedAccount.accountBalance.toLocaleString()}원`,
      customClass: {
        title: 'swal2-centered-text',
        content: 'swal2-centered-text',
      },
    });
  } catch (error) {
    console.error('Error during withdrawal:', error);

    // 실패 메시지
    const errorMessage = error.response?.data || '출금에 실패했습니다.';
    await Swal.fire({
      icon: 'error',
      title: '출금 실패',
      text: errorMessage,
      customClass: {
        title: 'swal2-centered-text',
        content: 'swal2-centered-text',
      },
    });
  }
};

// 계좌 및 사용자 정보 로드
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
            이율은 예금액에 대해 발생하는 금액의 비율입니다. <br />예를 들어,
            이율이 2.5%라면 1,000,000원에 대해 25,000원의 이자가 발생합니다.
          </p>
          <button class="close-button" @click="toggleModal">닫기</button>
        </div>
      </div>

      <!-- 현재 이율 정보 -->
      <h5 class="card-title mt-3">
        {{ myMember.memberName }}님의 현재 이율은
        <strong>{{ myRate }}%</strong>입니다.<br />
      </h5>
      <h2 class="display-4">
        {{ myBalance ? myBalance.toLocaleString() : 'N/A' }}원
      </h2>
      <!--<p class="text-muted">+124.23922434원 (금일 벌어들인 이자)</p>-->
      <div>
        <h3 class="text-color">+{{ countInterest.toFixed(4) }}원</h3>
        <p class="text-muted inline-container">(금일 벌어들인 이자)</p>
      </div>

      <!-- 입금 및 출금 버튼 -->
      <div class="d-flex justify-content-center mt-4">
        <button class="btn btn-primary mx-2" @click="depositAmount">
          입금
        </button>
        <button class="btn btn-danger mx-2" @click="withdraw">출금</button>
      </div>
    </div>
  </div>
</template>

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
/* SweetAlert2 커스텀 스타일 */

/* 팝업 자체를 중앙에 정렬 */
.swal2-centered-popup {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

/* 입력 필드 중앙 정렬 */
.swal2-centered-input {
  display: block;
  margin: 10px auto;
  text-align: center;
  width: 60%;
  min-width: 250px;
}

/* 버튼 영역 중앙 정렬 */
.swal2-centered-actions {
  display: flex;
  justify-content: center;
  gap: 15px;
}

/* 입금 버튼 스타일 */
.swal2-button-confirm {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.swal2-button-confirm:hover {
  background-color: #0056b3;
}

/* 취소 버튼 스타일 */
.swal2-button-cancel {
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.swal2-button-cancel:hover {
  background-color: #5a6268;
}
</style>
