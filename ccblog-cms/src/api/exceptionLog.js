import axios  from "@/utils/request";

export function getExceptionLogList(queryInfo) {
    return axios({
        url: '/exceptionLog/list',
        method: 'GET',
        params: {
            ...queryInfo
        }
    })
}

export function deleteExceptionLogById(id){
    return axios({
        url: '/exceptionLog/delete',
        method: 'DELETE',
        params: {
            id
        }
    })
}