<script setup>
import { ref, onUnmounted } from 'vue';
// import { useRoute, useRouter } from 'vue-router';
import Swal from 'sweetalert2';
import jsQR from 'jsqr';

const videoRef = ref(null);
const canvasRef = ref(null);
const streaming = ref(false);

// questApi 작성후 살리기
// import questApi from '@/api/questApi';

// const props = defineProps({
//     memberNo: Number,
// });

// const load = async (memberNo) => {
//     try {
//         console.log('memberNo : ', memberNo);
//         questContentObject.value = await questApi.
//     }
// }

const startQrScanner = async () => {
  try {
    streaming.value = true;
    
    // 모바일과 데스크톱 모두 지원하는 설정
    const constraints = {
      video: {
        facingMode: 'environment',
        width: { min: 640, ideal: 1280, max: 1920 },
        height: { min: 480, ideal: 720, max: 1080 },
        aspectRatio: { ideal: 1.7777777778 }
      }
    };
    
    const stream = await navigator.mediaDevices.getUserMedia(constraints);
    if (videoRef.value) {
      videoRef.value.srcObject = stream;
      await videoRef.value.play();
      requestAnimationFrame(scanQRCode); // 즉시 스캔 시작
    }
  } catch (error) {
    console.error('Camera error:', error);
    Swal.fire('에러', '카메라 접근에 실패했습니다.', 'error');
    streaming.value = false;
  }
};

const scanQRCode = () => {
  if (!streaming.value || !videoRef.value || !canvasRef.value) return;

  const video = videoRef.value;
  const canvas = canvasRef.value;
  const context = canvas.getContext('2d', { willReadFrequently: true });

  // 비디오가 준비되지 않았으면 다시 시도
  if (video.readyState !== video.HAVE_ENOUGH_DATA) {
    requestAnimationFrame(scanQRCode);
    return;
  }

  // 캔버스 크기를 비디오 크기에 맞춤
  canvas.width = video.videoWidth;
  canvas.height = video.videoHeight;

  try {
    // 비디오 프레임을 캔버스에 그림
    context.drawImage(video, 0, 0, canvas.width, canvas.height);
    const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
    
    // QR 코드 스캔 시도
    const code = jsQR(imageData.data, imageData.width, imageData.height, {
      inversionAttempts: "dontInvert",
    });

    if (code) {
      // QR 코드 발견
      stopQrScanner();
      verifyQrCode(code.data);
    } else {
      // 계속 스캔
      requestAnimationFrame(scanQRCode);
    }
  } catch (error) {
    console.error('Scanning error:', error);
    requestAnimationFrame(scanQRCode);
  }
};

const verifyQrCode = async (qrData) => {
  try {
    console.log('Scanned QR code:', qrData); // 디버깅용
    const response = await fetch(`/api/qr/verify/${qrData}`, {
      method: 'POST',
      credentials: 'include'
    });

    if (response.ok) {
      Swal.fire('성공', 'QR 코드 인증이 완료되었습니다.', 'success');
    } else {
      const error = await response.text();
      Swal.fire('실패', error || 'QR 코드 인증에 실패했습니다.', 'error');
    }
  } catch (error) {
    console.error('Verification error:', error);
    Swal.fire('에러', '서버 연결에 실패했습니다.', 'error');
  }
};

const stopQrScanner = () => {
  if (videoRef.value?.srcObject) {
    videoRef.value.srcObject.getTracks().forEach(track => track.stop());
  }
  streaming.value = false;
};

const handleQuestAchieve = async (questContent, questId, isQr) => {
  try {
    const result = await Swal.fire({
      title: `${questContent} 인증`,
      text: '인증하시겠습니까?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: '확인',
      cancelButtonText: '취소'
    });

    if (result.isConfirmed) {
      if (isQr === 'true') {
        await startQrScanner();
      } else {
        console.log(`퀘스트 ${questId} 인증 진행`);
        Swal.fire('인증 완료!', '퀘스트 인증이 성공적으로 완료되었습니다.', 'success');
      }
    }
  } catch (error) {
    console.error('Quest error:', error);
    Swal.fire('에러', '처리 중 오류가 발생했습니다.', 'error');
  }
};

// const handleQuestAchieve = async (questContent) => {
//   try {
//     const result = await Swal.fire({
//       title: ${questContent} 인증,
//       text: 'QR 코드를 스캔하시겠습니까?',
//       icon: 'question',
//       showCancelButton: true,
//       confirmButtonText: '스캔',
//       cancelButtonText: '취소'
//     });

//     if (result.isConfirmed) {
//       await startQrScanner();
//     }
//   } catch (error) {
//     console.error('Quest error:', error);
//     Swal.fire('에러', '처리 중 오류가 발생했습니다.', 'error');
//   }
// };

onUnmounted(() => {
  stopQrScanner();
});
</script>

<template>
    <div class="card">
        <div class="card-header pb-0">
            <h6>오늘의 퀘스트</h6>
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
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-2.jpg" class="avatar avatar-sm me-3" alt="user1" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0"></p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <button class="badge bg-gradient-success border-0" @click="handleQuestAchieve('친환경 제품 구매', 1, 'true')">인증</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-3.jpg" class="avatar avatar-sm me-3" alt="user2" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0"></p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <button class="badge bg-gradient-success border-0" @click="handleQuestAchieve('친환경 제품 구매', 1)">인증</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-4.jpg" class="avatar avatar-sm me-3" alt="user3" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0"></p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <button class="badge bg-gradient-success border-0" @click="handleQuestAchieve('친환경 제품 구매', 1)">인증</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-3.jpg" class="avatar avatar-sm me-3" alt="user4" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0"></p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <button class="badge bg-gradient-success border-0" @click="handleQuestAchieve('친환경 제품 구매', 1)">인증</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-2.jpg" class="avatar avatar-sm me-3" alt="user1" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0"></p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <button class="badge bg-gradient-success border-0" @click="handleQuestAchieve('친환경 제품 구매', 1)">인증</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-4.jpg" class="avatar avatar-sm me-3" alt="user6" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0"></p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <button class="badge bg-gradient-success border-0" @click="handleQuestAchieve('친환경 제품 구매', 1)">인증</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- QR 스캐너 오버레이 -->
    <div v-if="streaming" class="qr-scanner-overlay">
      <div class="qr-scanner-container">
        <video ref="videoRef" autoplay playsinline class="qr-video"></video>
        <canvas ref="canvasRef" class="qr-canvas"></canvas>
        <div class="scanner-guide"></div>
        <button @click="stopQrScanner" class="close-scanner">닫기</button>
      </div>
    </div>
</template>

<style scoped>
/* 기존 스타일 (테이블, 카드 등) */
.card {
    background-color: var(--bs-card-bg);
    border: 0 solid transparent;
    margin-bottom: 30px;
    box-shadow: 0 0 2rem 0 rgb(136 152 170 / 15%);
    position: relative;
    display: flex;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    background-clip: border-box;
    border-radius: 0.75rem;
}

.card-header {
    padding: 1.5rem;
    margin-bottom: 0;
    background-color: transparent;
    border-bottom: 0 solid transparent;
}

.card-body {
    flex: 1 1 auto;
    padding: var(--bs-card-spacer-y) var(--bs-card-spacer-x);
}

.table-responsive {
    overflow-x: auto;
    -webkit-overflow-scrolling: touch;
}

.table {
    margin-bottom: 0;
}

.avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
}

/* QR 스캐너 관련 새로운 스타일 */
.qr-scanner-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.8);
    z-index: 1000;
    display: flex;
    justify-content: center;
    align-items: center;
}

.qr-scanner-container {
    position: relative;
    width: 80%;
    max-width: 500px;
}

.qr-video {
    width: 100%;
    border-radius: 10px;
}

.close-scanner {
    position: absolute;
    top: 10px;
    right: 10px;
    background: white;
    border: none;
    padding: 5px 10px;
    border-radius: 5px;
    cursor: pointer;
}
</style>