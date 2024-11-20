<script setup>
import { computed, ref, onBeforeUnmount, onBeforeMount } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import Navbar from '@/examples/PageLayout/Navbar.vue';
import ArgonInput from '@/components/ArgonInput.vue';
// import ArgonSwitch from '@/components/ArgonSwitch.vue';
import ArgonButton from '@/components/ArgonButton.vue';
import auth from '@/store/auth';

const email = ref('');
const password = ref('');
const router = useRouter();

const body = document.getElementsByTagName('body')[0];

const store = useStore();
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

const login = async () => {
    const loginDTO = {
        email: email.value,
        password: password.value,
    };

    try {
        const response = await auth.login(loginDTO);
        console.log('Login success : ', response);

        router.push('/');
    } catch (error) {
        console.error('로그인 실패 : ', error.response?.data || error.message);
        alert('로그인에 실패했습니다. 이메일과 비밀번호를 확인하세요.');
    }
};

// 버튼 비활성화
const disableSubmit = computed(() => !(email.value && password.value));
</script>

<template>
    <div class="container top-0 position-sticky z-index-sticky">
        <div class="row">
            <div class="col-12">
                <navbar isBlur="blur  border-radius-lg my-3 py-2 start-0 end-0 mx-4 shadow" v-bind:darkMode="true" isBtn="bg-gradient-success" />
            </div>
        </div>
    </div>
    <main class="mt-0 main-content">
        <section>
            <div class="page-header min-vh-100">
                <div class="container">
                    <div class="row">
                        <div class="mx-auto col-xl-4 col-lg-5 col-md-7 d-flex flex-column mx-lg-0">
                            <div class="card card-plain">
                                <div class="pb-0 card-header text-start">
                                    <h4 class="font-weight-bolder">로그인</h4>
                                    <p class="mb-0">이메일과 비밀번호를 입력하여 로그인하세요.</p>
                                </div>
                                <div class="card-body">
                                    <form role="form" @submit.prevent="login">
                                        <div class="mb-3">
                                            <argon-input id="email" type="email" placeholder="Email" name="email" size="lg" v-model="email" />
                                        </div>
                                        <!-- 비밀번호 입력 -->
                                        <div class="mb-3">
                                            <argon-input id="password" type="password" placeholder="Password" name="password" size="lg" v-model="password" />
                                        </div>
                                        <!-- <argon-switch id="rememberMe" name="remember-me"
                      >Remember me</argon-switch
                    > -->
                                        <!-- 로그인 버튼 -->
                                        <div class="text-center">
                                            <argon-button class="mt-4" variant="gradient" color="success" fullWidth size="lg" type="submit" :disabled="disableSubmit"
                                                >로그인</argon-button
                                            >
                                        </div>
                                    </form>
                                </div>
                                <div class="px-1 pt-0 text-center card-footer px-lg-2">
                                    <p class="mx-auto mb-4 text-sm">
                                        계정이 없으신가요?
                                        <a href="javascript:;" class="text-success text-gradient font-weight-bold">회원가입</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="top-0 my-auto text-center col-6 d-lg-flex d-none h-100 pe-0 position-absolute end-0 justify-content-center flex-column">
                            <div
                                class="position-relative bg-gradient-primary h-100 m-3 px-7 border-radius-lg d-flex flex-column justify-content-center overflow-hidden"
                                :style="{
                                    backgroundImage: `url(${require('@/assets/img/login-background.png')})`,
                                    backgroundSize: 'cover',
                                    backgroundPosition: 'center',
                                    backgroundRepeat: 'no-repeat',
                                }"
                            >
                                <span class="mask" style="background-color: rgba(0, 0, 0, 0.5); position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 1"></span>
                                <h4 class="mt-5 text-white font-weight-bolder position-relative" style="text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8); z-index: 2">Welcome!</h4>
                                <p
                                    class="text-lead text-white position-relative"
                                    style="font-size: 1rem; line-height: 1.8; text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.7); z-index: 2"
                                >
                                    탄소 발자국을 줄이고 환경을 지키는 여정을 함께하세요.<br />
                                    탄소 리포트 제공, 녹색 소비 보상, 퀘스트와 챌린지로<br />
                                    작은 실천이 큰 변화를 만듭니다.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
</template>
