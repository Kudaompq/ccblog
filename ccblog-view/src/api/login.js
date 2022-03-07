import axios from "@/plugins/axios";

export function login(loginForm) {
    return axios({
        url:'/admin/login',
        method: 'POST',
        data:{
            ...loginForm
        }
    })
}