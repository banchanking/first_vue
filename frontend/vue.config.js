const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false // should always be multi-word 에러 해당 설정 추가
})

module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8081',
                }
            }
        }
    }
