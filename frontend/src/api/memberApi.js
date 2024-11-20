import api from '@/api';
// import axios from 'axios';

// const api = axios.create({
//   baseURL: '/api/member', // 백엔드 API 기본 URL
//   withCredentials: true, // 세션 쿠키를 포함하기 위함
// });

// export default api;

const BASE_URL = '/api/member';
const headers = { 'Content-Type': 'application/json' };

export default {
  // 회원가입
  async create(member) {
    const { data } = await api.post(`${BASE_URL}/join`, member, headers);
    return data;
  },

  // 회원탈퇴
  async delete() {
    const response = await api.delete(`${BASE_URL}/{no}`);
    return response;
  },
};
