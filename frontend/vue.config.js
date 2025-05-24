// vue.config.js

const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false,         // 멀티워드 에러 방지

    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8081',
                changeOrigin: true,   // 호스트 헤더를 target URL에 맞춰 변경
                // pathRewrite: { '^/api': '/api' }  // 필요시 재작성
            }
        }
    }
})
