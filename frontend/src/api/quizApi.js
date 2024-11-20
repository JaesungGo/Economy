import api from '@/api';

const BASE_URL = '/quiz';

export default {
  getDailyQuiz() {
    return api.get(`${BASE_URL}/daily`);
  },

  getQuizOptions(quizPk) {
    return api.get(`${BASE_URL}/options/${quizPk}`);
  },

  checkQuizCompletion(memberNo) {
    return api.get(`${BASE_URL}/check-completion`, {
      params: { memberNo },
    });
  },

  submitQuizAnswer(payload) {
    return api.post(`${BASE_URL}/submit`, payload, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
  },
};
