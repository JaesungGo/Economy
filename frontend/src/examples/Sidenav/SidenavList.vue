<script setup>
import { useRoute } from 'vue-router';
import SidenavItem from './SidenavItem.vue';
import SidenavCard from './SidenavCard.vue';

const getRoute = () => {
    const route = useRoute();
    const routeArr = route.path.split('/');
    return routeArr[1];
};
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

            <!-- Community -->
            <li class="nav-item">
                <sidenav-item to="/community" :class="getRoute() === 'community' ? 'active' : ''" navText="커뮤니티">
                    <template v-slot:icon>
                        <i class="ni ni-world-2 text-dark text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <li class="nav-item">
                <sidenav-item to="/tables" :class="getRoute() === 'tables' ? 'active' : ''" navText="Tables(예시)">
                    <template v-slot:icon>
                        <i class="ni ni-calendar-grid-58 text-warning text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <li class="nav-item">
                <sidenav-item to="/billing" :class="getRoute() === 'billing' ? 'active' : ''" navText="Billing(예시)">
                    <template v-slot:icon>
                        <i class="ni ni-credit-card text-success text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <li class="mt-3 nav-item">
                <h6 class="text-xs ps-4 text-uppercase font-weight-bolder opacity-6 ms-2">ACCOUNT PAGES</h6>
            </li>

            <li class="nav-item">
                <sidenav-item to="/profile" :class="getRoute() === 'profile' ? 'active' : ''" navText="Profile">
                    <template v-slot:icon>
                        <i class="ni ni-single-02 text-dark text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <li class="nav-item">
                <sidenav-item to="/signin" :class="getRoute() === 'signin' ? 'active' : ''" navText="Sign In">
                    <template v-slot:icon>
                        <i class="ni ni-single-copy-04 text-danger text-sm opacity-10"></i>
                    </template>
                </sidenav-item>
            </li>

            <li class="nav-item">
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
