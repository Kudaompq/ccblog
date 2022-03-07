import axios from "@/utils/request";

export function getDashboard(){
    return axios({
        url: '/dashboard',
        method: 'GET'
    })
}