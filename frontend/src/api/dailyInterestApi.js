import api from '@/api';
// import axios from 'axios';

const BASE_URL = '/api/dailyInterest';
const headers = { 'Content-Type': 'application/json' };

export default {
  // 전체 내역 조회
  async getAll() {
    const { data } = await api.get(`${BASE_URL}/`, { headers });
    return data;
  },

  // 누적 이자 조회
  async getTotal() {
    const { data } = await api.get(`${BASE_URL}/total`, { headers });
    return data;
  },

  // 매월 말일 월별 누적 이자 조회
  async getMonthly() {
    const { data } = await api.get(`${BASE_URL}/monthly`, { headers });
    return data;
  },
};
