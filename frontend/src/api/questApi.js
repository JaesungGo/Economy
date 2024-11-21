import api from '@/api';

const BASE_URL = '/achieve';

export default {
    // 전체 퀘스트 조회
    async getTotalAchieve() {
        const { data } = await api.get(`${BASE_URL}/total`);
        return data;
    },

    // 오늘 퀘스트 조회
    async fetchTodayQuests() {
        const { data } = await api.get(`${BASE_URL}/today`);
        return data;
    },

    // 주간 퀘스트 조회
    async fetchWeekQuests() {
        const { data } = await api.get(`${BASE_URL}/week`);
        return data;
    },

    // 월간 퀘스트 조회
    async fetchMonthQuests() {
        const { data } = await api.get(`${BASE_URL}/month`);
        return data;
    },
};
