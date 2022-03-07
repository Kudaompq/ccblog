import axios from "@/utils/request"

export function getMomentList(queryInfo) {
    return axios({
        url: '/moment/list',
        method: 'GET',
        params:{
            ...queryInfo
        }
    })
}

export function updateMomentPublished(id,published){
    return axios({
        url: '/moment/publish',
        method: 'PUT',
        params:{
            id,published
        }
    })
}

export function deleteMoment(id) {
    return axios({
        url: '/moment/delete',
        method: 'DELETE',
        params:{
            id
        }
    })
}

export function getMoment(id){
    return axios({
        url: `/moment/${id}`,
        method: 'GET'
    })
}

export function saveMoment(moment){
    return axios({
        url: '/moment/save',
        method: 'POST',
        data:{
            ...moment
        }
    })
}

export function updateMoment(moment){
    return axios({
        url: '/moment/update',
        method: 'PUT',
        data:{
            ...moment
        }
    })
}