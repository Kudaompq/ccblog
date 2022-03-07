import axios  from "@/utils/request";

export function getOperationLogList(queryInfo) {
    return axios({
        url: '/operationLog/list',
        method: 'GET',
        params: {
            ...queryInfo
        }
    })
}

export function deleteOperationLogById(id){
    return axios({
        url: '/operationLog/delete',
        method: 'DELETE',
        params: {
            id
        }
    })
}