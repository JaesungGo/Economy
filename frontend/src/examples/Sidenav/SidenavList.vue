<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import SidenavItem from './SidenavItem.vue';
import SidenavCard from './SidenavCard.vue';
import auth from '@/store/auth';

const isLoggedIn = ref(false); //로그인 여부
const userInfo = ref(null); //사용자 정보

const getRoute = () => {
    const route = useRoute();
    const routeArr = route.path.split('/');
    return routeArr[1];
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
    <div class="collapse navbar-collapse w-auto h-auto h-100" id="sidenav-collapse-main">
        <ul class="navbar-nav">
            <li class="nav-item">
                <sidenav-item to="/dashboard-default" :class="getRoute() === 'dashboard-default' ? 'active' : ''" navText="Home">
                    <template v-slot:icon>
                        <i class="ni ni-tv-2 text-primary text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <!-- my page -->
            <li class="nav-item">
                <sidenav-item to="/mypage" :class="getRoute() === 'mypage' ? 'active' : ''" navText="My page">
                    <template v-slot:icon>
                        <i class="ni ni-calendar-grid-58 text-info text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <!-- Quest -->
            <li class="nav-item">
                <sidenav-item to="/quest" :class="getRoute() === 'quest' ? 'active' : ''" navText="퀘스트">
                    <template v-slot:icon>
                        <i class="ni ni-trophy text-warning text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <!-- Interest -->
            <li class="nav-item">
                <sidenav-item to="/interest" :class="getRoute() === 'interest' ? 'active' : ''" navText="이자 내역">
                    <template v-slot:icon>
                        <i class="ni ni-money-coins text-danger text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <!-- Report -->
            <li class="nav-item">
                <sidenav-item to="/report" :class="getRoute() === 'report' ? 'active' : ''" navText="레포트">
                    <template v-slot:icon>
                        <i class="ni ni-ruler-pencil text-primary text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <!-- About -->
            <li class="nav-item">
                <sidenav-item to="/about" :class="getRoute() === 'about' ? 'active' : ''" navText="그린카드란?">
                    <template v-slot:icon>
                        <i class="ni ni-credit-card text-success text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <!-- ACCOUNT PAGES -->
            <li class="mt-3 nav-item" v-if="!isLoggedIn">
                <h6 class="text-xs ps-4 text-uppercase font-weight-bolder opacity-6 ms-2">ACCOUNT PAGES</h6>
            </li>

            <li class="nav-item" v-if="!isLoggedIn">
                <sidenav-item to="/signin" :class="getRoute() === 'signin' ? 'active' : ''" navText="Sign In">
                    <template v-slot:icon>
                        <i class="ni ni-single-copy-04 text-danger text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <li class="nav-item" v-if="!isLoggedIn">
                <sidenav-item to="/signup" :class="getRoute() === 'signup' ? 'active' : ''" navText="Sign Up">
                    <template v-slot:icon>
                        <i class="ni ni-collection text-info text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>
        </ul>
    </div>

    <div class="pt-3 mx-3 mt-3 sidenav-footer">
        <sidenav-card
            :card="{
                title: 'Need Help?',
                description: 'Please check our docs',
                links: [
                    {
                        label: 'Documentation',
                        route: 'https://www.creative-tim.com/learning-lab/vue/overview/argon-dashboard/',
                        color: 'dark',
                    },
                    {
                        label: 'Buy now',
                        route: 'https://www.creative-tim.com/product/vue-argon-dashboard-pro?ref=vadp',
                        color: 'success',
                    },
                ],
            }"
        />
    </div>
</template>
