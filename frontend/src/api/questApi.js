import api from '@/api'; // Axios 인스턴스 임포트

const BASE_URL = '/quest'; // Quest API 기본 URL

export default {
  // 진행 중인 퀘스트 전체 조회
  async getActiveQuests() {
    try {
      const response = await api.get(`${BASE_URL}/active`);
      return response.data; // API 응답 데이터 반환
    } catch (error) {
      console.error('Error fetching active quests:', error);
      throw error; // 상위로 에러 전달
    }
  },

  // 진행 중인 일일 퀘스트 조회
  async getActiveDailyQuests() {
    try {
      const response = await api.get(
        `${BASE_URL}/active/daily`
      );
      return response.data;
    } catch (error) {
      console.error('Error fetching daily quests:', error);
      throw error;
    }
  },

  // 진행 중인 주간 퀘스트 조회
  async getActiveWeeklyQuests() {
    try {
      const response = await api.get(
        `${BASE_URL}/active/weekly`
      );
      return response.data;
    } catch (error) {
      console.error('Error fetching weekly quests:', error);
      throw error;
    }
  },

  // 진행 중인 월간 퀘스트 조회
  async getActiveMonthlyQuests() {
    try {
      const response = await api.get(
        `${BASE_URL}/active/monthly`
      );
      return response.data;
    } catch (error) {
      console.error(
        'Error fetching monthly quests:',
        error
      );
      throw error;
    }
  },
};
