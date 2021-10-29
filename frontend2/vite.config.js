import vue from '@vitejs/plugin-vue'

export default {

    host: '127.0.0.1',
    port: '8000',
    https: false,
    proxy: {
        //  如果是 api 开头，则访问地址如下
        // '/api':'http://127.0.0.1:8000'
    },

    base: './',
    plugins: [vue()],
    optimizeDeps: {
        include: ['schart.js']
    }
}
