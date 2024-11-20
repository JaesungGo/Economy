import api from '@/api'; // Axios 인스턴스 가져오기

export default {
    // 로그인
    async login(loginDTO) {
        const { data } = await api.post('/member/login', loginDTO);
        console.log('POST MemberLogin:', data);
        return data; // 로그인 성공 시 반환된 데이터 (ex: 회원 ID)
    },

    // 로그아웃
    async logout() {
        const { data } = await api.post('/member/logout');
        console.log('POST Logout:', data);
        return data; // 로그아웃 성공 메시지
    },

    // 로그인 상태 확인
    async checkLogin() {
        const { data } = await api.get('/member/check-login');
        console.log('GET CheckLogin:', data);
        return data; // 로그인 상태 메시지 (ex: "Logged in as: email@example.com")
    },
};
