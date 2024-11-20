import { createStore } from 'vuex';

export default createStore({
    state: {
        hideConfigButton: false,
        isPinned: false,
        showConfig: false,
        sidebarType: 'bg-white',
        isRTL: false,
        mcolor: '',
        darkMode: false,
        isNavFixed: false,
        isAbsolute: false,
        showNavs: true,
        showSidenav: true,
        showNavbar: true,
        showFooter: true,
        showMain: true,
        layout: 'default',
        isLoggedIn: false, // 로그인 여부 상태
    },
    mutations: {
        toggleConfigurator(state) {
            state.showConfig = !state.showConfig;
        },
        sidebarMinimize(state) {
            let sidenav_show = document.querySelector('#app');
            if (state.isPinned) {
                sidenav_show.classList.add('g-sidenav-hidden');
                sidenav_show.classList.remove('g-sidenav-pinned');
                state.isPinned = false;
            } else {
                sidenav_show.classList.add('g-sidenav-pinned');
                sidenav_show.classList.remove('g-sidenav-hidden');
                state.isPinned = true;
            }
        },
        sidebarType(state, payload) {
            state.sidebarType = payload;
        },
        navbarFixed(state) {
            state.isNavFixed = !state.isNavFixed;
        },
        setLoggedIn(state, status) {
            state.isLoggedIn = status; // 로그인 여부 업데이트
        },
    },
    actions: {
        toggleSidebarColor({ commit }, payload) {
            commit('sidebarType', payload);
        },
        updateLoginStatus({ commit }, status) {
            commit('setLoggedIn', status); // 액션으로 로그인 상태 변경
        },
    },
    getters: {
        isLoggedIn: (state) => state.isLoggedIn, // 로그인 여부 getter
    },
});
