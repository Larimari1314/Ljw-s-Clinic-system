module.exports = {
    devServer: {
        proxy: {
            //配置跨域
            '/api': {
                target: 'http://localhost:8000/', //这里填写项目真实的后台接口地址
                secure: false,  //https请求需要这个参数,
                changOrigin: true, //设置允许跨域
                pathRewrite: {//这个重写不可省略！因为我们真正请求的地址并不含 /api
                    '^/api': ''
                }
            }
        }
    }
}
