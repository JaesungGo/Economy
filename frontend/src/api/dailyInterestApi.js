import api from '@/api';
// import axios from 'axios';

const BASE_URL = '/dailyInterest';

export default {
  // 전체 내역 조회
  async getAll() {
    const { data } = await api.get(`${BASE_URL}/`);
    console.log('getAll:', data);
    return data;
  },

  // 누적 이자 조회
  async getTotal() {
    const { data } = await api.get(`${BASE_URL}/total`);
    console.log('getTotal:', data);
    return data;
  },

  // 매월 말일 월별 누적 이자 조회
  async getMonthly() {
    const { data } = await api.get(`${BASE_URL}/monthly`);
    console.log('getMonthly:', data);
    return data;
  },
};
