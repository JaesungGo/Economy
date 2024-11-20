<script setup>
// import { ref, reactive, computed } from 'vue';
// import { useRoute, useRouter } from 'vue-router';

// const props = defineProps({
//     memberNo: Number,
// });

// const load = async (memberNo) => {
//     try {
//         console.log('memberNo : ', memberNo);
//         questContentObject.value = await questApi.
//     }
// }
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
                            <!-- <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Employed</th> -->
                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- 실제 코드로 사용할 것 -->
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <!-- 퀘스트 종류에 따른 이미지 -->
                                        <img src="../../assets/img/team-2.jpg" class="avatar avatar-sm me-3" alt="user1" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <!-- 퀘스트 내용 -->
                                        <h6 class="mb-0 text-sm">{{ Questcontent }}</h6>
                                        <!-- 퀘스트 세부 내용 -->
                                        <p class="text-xs text-secondary mb-0"></p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <span class="badge bg-gradient-secondary">인증</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <!-- 퀘스트 종류에 따른 이미지 -->
                                        <img src="../../assets/img/team-2.jpg" class="avatar avatar-sm me-3" alt="user1" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <!-- 퀘스트 내용 -->
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <!-- 퀘스트 세부 내용 -->
                                        <p class="text-xs text-secondary mb-0"></p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <span class="badge bg-gradient-secondary">인증</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-3.jpg" class="avatar avatar-sm me-3" alt="user2" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <!-- 퀘스트 내용 -->
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <!-- 퀘스트 세부 내용 -->
                                        <p class="text-xs text-secondary mb-0"></p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <span class="badge bg-gradient-secondary">인증</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-4.jpg" class="avatar avatar-sm me-3" alt="user3" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <!-- 퀘스트 내용 -->
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <!-- 퀘스트 세부 내용 -->
                                        <p class="text-xs text-secondary mb-0"></p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <span class="badge bg-gradient-secondary">인증</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-3.jpg" class="avatar avatar-sm me-3" alt="user4" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <!-- 퀘스트 내용 -->
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <!-- 퀘스트 세부 내용 -->
                                        <p class="text-xs text-secondary mb-0"></p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <span class="badge bg-gradient-secondary">인증</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <!-- 퀘스트 종류에 따른 이미지 -->
                                        <img src="../../assets/img/team-2.jpg" class="avatar avatar-sm me-3" alt="user1" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <!-- 퀘스트 내용 -->
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <!-- 퀘스트 세부 내용 -->
                                        <p class="text-xs text-secondary mb-0"></p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <span class="badge bg-gradient-secondary">인증</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="d-flex px-2 py-1">
                                    <div>
                                        <img src="../../assets/img/team-4.jpg" class="avatar avatar-sm me-3" alt="user6" />
                                    </div>
                                    <div class="d-flex flex-column justify-content-center">
                                        <!-- 퀘스트 내용 -->
                                        <h6 class="mb-0 text-sm">친환경 전문 매장을 방문하여 제품을 그린카드로 구매하세요!</h6>
                                        <!-- 퀘스트 세부 내용 -->
                                        <p class="text-xs text-secondary mb-0">50,000원 이상 구매시 적용이 가능합니다!</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p class="text-xs font-weight-bold mb-0">녹색 소비</p>
                                <p class="text-xs text-secondary mb-0">나무 1그루🌲를 보호하고 6.6kg의 탄소☁️를 상쇄할 수 있습니다!</p>
                            </td>

                            <td class="align-middle text-center">
                                <span class="text-secondary text-xs font-weight-bold">50P</span>
                            </td>

                            <td class="align-middle text-center text-sm">
                                <span class="badge bg-gradient-secondary">인증</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>
