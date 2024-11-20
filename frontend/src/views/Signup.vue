<script setup>
import { ref, onBeforeMount, onBeforeUnmount, reactive } from 'vue';
import { useStore } from 'vuex';
// import axios from 'axios'; // Axios 임포트'
import memberApi from '@/api/memberApi';

import Navbar from '@/examples/PageLayout/Navbar.vue';
import AppFooter from '@/examples/PageLayout/Footer.vue';
import ArgonInput from '@/components/ArgonInput.vue';
import ArgonButton from '@/components/ArgonButton.vue';

// Vuex와 body 스타일링 관련 초기 설정
const body = document.getElementsByTagName('body')[0];
const store = useStore();

// const name = ref(''); //
// const email = ref(''); // 이메일 입력값
// const password = ref(''); // 비밀번호 입력값
const message = ref(''); // 결과 메시지
const member = reactive({
  name: '',
  email: '',
  password: '',
});

// API 요청 함수
const register = async () => {
  try {
    const response = await memberApi.create(member);
    message.value = `회원가입 성공! ID: ${response.data}`;
  } catch (error) {
    console.error(error);
    message.value = `회원가입 실패: ${error.response?.data || '서버 오류'}`;
  }
};

onBeforeMount(() => {
  store.state.hideConfigButton = true;
  store.state.showNavbar = false;
  store.state.showSidenav = false;
  store.state.showFooter = false;
  body.classList.remove('bg-gray-100');
});

onBeforeUnmount(() => {
  store.state.hideConfigButton = false;
  store.state.showNavbar = true;
  store.state.showSidenav = true;
  store.state.showFooter = true;
  body.classList.add('bg-gray-100');
});
</script>

<template>
  <div class="container top-0 position-sticky z-index-sticky">
    <div class="row">
      <div class="col-12">
        <navbar isBtn="bg-gradient-light" />
      </div>
    </div>
  </div>
  <main class="main-content mt-0">
    <div
      class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg"
      :style="{
        backgroundImage: `url(${require('@/assets/img/login-background.png')})`,
        backgroundPosition: 'top',
      }"
    >
      <span class="mask bg-gradient-dark opacity-6"></span>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5 text-center mx-auto">
            <h1 class="text-white mb-2 mt-5">Welcome!</h1>
            <p class="text-lead text-white">
              탄소 발자국을 줄이고 환경을 지키는 여정을 함께하세요.<br />
              탄소 리포트 제공, 녹색 소비 보상, 퀘스트와 챌린지로<br />
              작은 실천이 큰 변화를 만듭니다.
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row mt-lg-n10 mt-md-n11 mt-n10 justify-content-center">
        <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
          <div class="card z-index-0">
            <div class="card-header text-center pt-4">
              <h5>회원가입</h5>
            </div>
            <div class="card-body">
              <form @submit.prevent="register" role="form">
                <!-- API 요청 -->
                <argon-input
                  id="name"
                  v-model="member.name"
                  type="text"
                  placeholder="Name"
                  aria-label="Name"
                />
                <argon-input
                  id="email"
                  v-model="member.email"
                  type="email"
                  placeholder="Email"
                  aria-label="Email"
                />
                <argon-input
                  id="password"
                  v-model="member.password"
                  type="password"
                  placeholder="Password"
                  aria-label="Password"
                />
                <div class="text-center">
                  <argon-button
                    fullWidth
                    color="dark"
                    variant="gradient"
                    class="my-4 mb-2"
                    type="submit"
                    >회원가입</argon-button
                  >
                </div>
                <p class="text-sm mt-3 mb-0">
                  이미 계정이 있으신가요?
                  <a href="javascript:;" class="text-dark font-weight-bolder"
                    >로그인</a
                  >
                </p>
              </form>
              <p v-if="message" class="text-center mt-3">{{ message }}</p>
              <!-- 결과 메시지 -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
  <app-footer />
</template>
