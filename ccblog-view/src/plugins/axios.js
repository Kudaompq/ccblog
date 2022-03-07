import axios from "axios";
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const request = axios.create({
    // baseURL: 'https://api.kudaompq.top/ccblog',
    baseURL: 'http://localhost:9003/ccblog',
    timeout: 10000
})

// 请求拦截
request.interceptors.request.use(
    config => {
        NProgress.start()
        const identification = window.localStorage.getItem('identification')
        if (identification && !(config.url.startsWith('http://') || config.url.startsWith('https://'))){
            config.headers.identification = identification
        }
        return config
    }
)

// 响应拦截
request.interceptors.response.use(
    config => {
        NProgress.done()
        const identification = config.headers.identification
        if (identification) {
            //保存身份标识到localStorage
            window.localStorage.setItem('identification', identification)
        }
        return config.data
    }
)

export default request