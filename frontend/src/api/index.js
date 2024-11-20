import axios from 'axios';
import router from '@/router';

const instance = axios.create({
    timeout: 10000,
    withCredentials: true, // 세션 쿠키를 자동으로 포함
});

// 요청 인터셉터
instance.interceptors.request.use(
    (config) => {
        // 세션 인증은 쿠키 기반으로 처리되므로 추가 작업은 불필요
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 응답 인터셉터
instance.interceptors.response.use(
    (response) => {
        // 정상 응답인 경우
        if (response.status === 200) {
            return response;
        }
        if (response.status === 404) {
            return Promise.reject('404: 페이지 없음 ' + response.request);
        }
        return response;
    },
    async (error) => {
        // 에러 응답 처리
        if (error.response?.status === 401) {
            // 세션 만료 또는 인증 실패 처리
            router.push('/auth/login?error=session_expired');
            return Promise.reject({ error: '로그인이 필요한 서비스입니다.' });
        }
        return Promise.reject(error);
    }
);

export default instance; // 인터셉터가 적용된 axios 인스턴스
