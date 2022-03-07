import axios from 'axios'
import {Message} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import router from "../router";

const request = axios.create({
	baseURL: 'https://api.kudaompq.top/ccblog/admin',
	// baseURL: 'http://localhost:9003/ccblog/admin',
	timeout: 5000
})


// request interceptor
request.interceptors.request.use(config => {
		NProgress.start()
		// 用来请求后台
		const token = window.localStorage.getItem('token')
		if (token) {
			config.headers.Authorization = token
		}
		return config
	},
	error => {
		return Promise.reject(error)
	}
)

// response interceptor
request.interceptors.response.use(response => {
		NProgress.done()
		const res = response.data
		if (res.code !== 200) {
			Message({
				message: res.msg || 'Error',
				type: 'error',
				duration: 5000
			})
			return Promise.reject(new Error(res.msg || 'Error'))
		}
		return res
	},
	error => {
		console.log(error)
		Message({
			message: error.message,
			type: 'error',
			duration: 5000
		})
		return Promise.reject(error)
	}
)

export default request