// import axios from 'axios';
// import router from '@/router';

// const instance = axios.create({
//     timeout: 10000,
//     withCredentials: true, // 세션 쿠키를 자동으로 포함
// });

// // 요청 인터셉터
// instance.interceptors.request.use(
//     (config) => {
//         // 세션 인증은 쿠키 기반으로 처리되므로 추가 작업은 불필요
//         return config;
//     },
//     (error) => {
//         return Promise.reject(error);
//     }
// );

// // 응답 인터셉터
// instance.interceptors.response.use(
//     (response) => {
//         // 정상 응답인 경우
//         if (response.status === 200) {
//             return response;
//         }
//         if (response.status === 404) {
//             return Promise.reject('404: 페이지 없음 ' + response.request);
//         }
//         return response;
//     },
//     async (error) => {
//         // 에러 응답 처리
//         if (error.response?.status === 401) {
//             // 세션 만료 또는 인증 실패 처리
//             router.push('/auth/login?error=session_expired');
//             return Promise.reject({ error: '로그인이 필요한 서비스입니다.' });
//         }
//         return Promise.reject(error);
//     }
// );

// export default instance; // 인터셉터가 적용된 axios 인스턴스

import axios from 'axios';
import router from '@/router';

// Axios 인스턴스 생성
const instance = axios.create({
    baseURL: '/api', // 프록시 경로와 일치
    timeout: 10000,
    withCredentials: true, // 세션 쿠키를 자동으로 포함 (필요한 경우)
    headers: {
        'Content-Type': 'application/json', // JSON 데이터를 처리하기 위한 기본 헤더
    },
});

// 요청 인터셉터
instance.interceptors.request.use(
    (config) => {
        // 요청 전 처리 (인증 토큰 추가 가능)
        // 예: config.headers.Authorization = `Bearer ${token}`;
        return config;
    },
    (error) => {
        console.error('Request Error:', error);
        return Promise.reject(error);
    }
);

// 응답 인터셉터
instance.interceptors.response.use(
    (response) => response, // 정상 응답 처리
    (error) => {
        const status = error.response?.status;

        if (status === 401) {
            console.warn('Unauthorized: Redirecting to login page');
            router.push('/auth/login?error=session_expired');
            return Promise.reject({ error: '로그인이 필요한 서비스입니다.' });
        } else if (status === 404) {
            console.warn('Not Found:', error.response?.request);
            return Promise.reject('404: 요청한 페이지를 찾을 수 없습니다.');
        } else if (status === 500) {
            console.error('Internal Server Error:', error.response?.data);
            return Promise.reject('500: 서버에 오류가 발생했습니다.');
        } else if (status === 403) {
            console.warn('Forbidden:', error.response?.data);
            return Promise.reject('403: 권한이 없습니다.');
        }

        console.error('Unhandled Error:', error.response?.data || error.message);
        return Promise.reject(error);
    }
);

export default instance; // 인터셉터가 적용된 Axios 인스턴스
