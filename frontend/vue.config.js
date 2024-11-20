// vue.config.js
const path = require('path'); // path 모듈 임포트

module.exports = {
    configureWebpack: {
        resolve: {
          alias: {
            '@': path.resolve(__dirname, 'src')  // 'src' 폴더를 '@'로 별칭 설정
          }
        }
    },
    devServer: {
        port: 3000, // 프론트엔드 개발 서버 포트를 3000으로 변경
        proxy: {
            '/api': {
                target: 'http://localhost:8080', // 백엔드 서버는 여전히 8080에서 동작
                changeOrigin: true,
                //pathRewrite: { '^/api': '' }, // '/api' 프리픽스를 제거
            },
        },
    },
};
