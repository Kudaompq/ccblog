import axios from "@/plugins/axios";

export function getMomentList(pageNum) {
    return axios({
        url: '/moment/list',
        method: 'GET',
        params:{
            pageNum
        }
    })
}

export function likeMoment(id){
    return axios({
        url: `/moment/like/${id}`,
        method: 'POST'
    })
}