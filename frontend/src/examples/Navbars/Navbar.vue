<script setup>
import { computed, ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRoute, useRouter } from 'vue-router';
import Breadcrumbs from '../Breadcrumbs.vue';
import auth from '@/store/auth';

const showMenu = ref(false);
const store = useStore();
const router = useRouter();
const route = useRoute();

const currentRouteName = computed(() => {
    return route.name;
});
const currentDirectory = computed(() => {
    let dir = route.path.split('/')[1];
    return dir.charAt(0).toUpperCase() + dir.slice(1);
});

const isLoggedIn = ref(false); //로그인 여부
const userInfo = ref(null); //사용자 정보
const minimizeSidebar = () => store.commit('sidebarMinimize');
const toggleConfigurator = () => store.commit('toggleConfigurator');

const closeMenu = () => {
    setTimeout(() => {
        showMenu.value = false;
    }, 100);
};

const logout = async () => {
    try {
        await auth.logout(); // auth.js의 logout 메서드 호출
        store.commit('setLoggedIn', false); // Vuex 상태 업데이트
        router.push('/signin'); // 로그아웃 후 로그인 페이지로 이동
    } catch (error) {
        console.error('Logout failed:', error);
    }
};

// 로그인 상태 확인
const fetchLoginStatus = async () => {
    try {
        const status = await auth.checkLogin(); // auth.js의 checkLogin 호출
        isLoggedIn.value = true;
        userInfo.value = { email: status.split(' ')[2] }; // "Logged in as: email@example.com"에서 이메일 추출
    } catch (error) {
        isLoggedIn.value = false;
        userInfo.value = null;
        console.error('로그인 상태 확인 실패:', error.message);
    }
};

// 컴포넌트 로드 시 로그인 상태 확인
onMounted(fetchLoginStatus);
</script>

<template>
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl top-0 position-sticky z-index-sticky" v-bind="$attrs" id="navbarBlur" data-scroll="true">
        <div class="px-3 py-1 container-fluid">
            <breadcrumbs :current-page="currentRouteName" :current-directory="currentDirectory" />

            <div class="mt-2 collapse navbar-collapse mt-sm-0 me-md-0 me-sm-4" id="navbar">
                <div class="pe-md-3 d-flex align-items-center ms-md-auto"></div>
                <ul class="navbar-nav justify-content-end">
                    <li class="nav-item d-flex align-items-center">
                        <!-- 로그인 여부에 따라 버튼 변경 -->
                        <router-link v-if="!isLoggedIn" :to="{ name: 'Signin' }" class="px-0 nav-link font-weight-bold text-white" target="_blank">
                            <i class="fa fa-user me-sm-2"></i>
                            <span class="d-sm-inline d-none">Sign In</span>
                        </router-link>
                        <button v-else @click="logout" class="btn btn-link px-0 nav-link font-weight-bold text-white">
                            <i class="fa fa-sign-out-alt me-sm-2"></i>
                            <span class="d-sm-inline d-none">Logout</span>
                        </button>
                    </li>
                    <li class="nav-item d-xl-none ps-3 d-flex align-items-center">
                        <a href="#" @click="minimizeSidebar" class="p-0 nav-link text-white" id="iconNavbarSidenav">
                            <div class="sidenav-toggler-inner">
                                <i class="sidenav-toggler-line bg-white"></i>
                                <i class="sidenav-toggler-line bg-white"></i>
                                <i class="sidenav-toggler-line bg-white"></i>
                            </div>
                        </a>
                    </li>
                    <li class="px-3 nav-item d-flex align-items-center">
                        <a class="p-0 nav-link text-white" @click="toggleConfigurator">
                            <i class="cursor-pointer fa fa-cog fixed-plugin-button-nav"></i>
                        </a>
                    </li>
                    <li class="nav-item dropdown d-flex align-items-center pe-2">
                        <a
                            href="#"
                            class="p-0 nav-link text-white"
                            :class="[showMenu ? 'show' : '']"
                            id="dropdownMenuButton"
                            data-bs-toggle="dropdown"
                            aria-expanded="false"
                            @click="showMenu = !showMenu"
                            @blur="closeMenu"
                        >
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>
