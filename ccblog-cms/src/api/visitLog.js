import axios from '@/utils/request'

export function getVisitLogList(queryInfo) {
    return axios({
        url: '/visitLog/list',
        method: 'GET',
        params: {
            ...queryInfo
        }
    })
}

export function deleteVisitLogById(id){
    return axios({
        url: '/visitLog/delete',
        method: 'DELETE',
        params: {
            id
        }
    })
}