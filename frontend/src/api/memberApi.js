import api from '@/api';

const BASE_URL = '/member';

export default {
    // 회원가입
    async create(member) {
        const { data } = await api.post(`${BASE_URL}/join`, member);
        return data;
    },

    // 회원 정보 조회
    async getMember() {
        const { data } = await api.get(`${BASE_URL}/`);
        console.log('GET Member:', data);
        return data; // 회원 정보 반환
    },

    // 회원탈퇴
    async deleteMember(memberNo) {
        const { data } = await api.delete(`${BASE_URL}/${memberNo}`);
        console.log('DELETE Member:', data);
        return data; // 회원탈퇴 성공 여부 반환
    },
};
