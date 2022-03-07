import axios  from "@/utils/request";

export function getLoginLogList(queryInfo) {
    return axios({
        url: '/loginLog/list',
        method: 'GET',
        params: {
            ...queryInfo
        }
    })
}

export function deleteLoginLogById(id){
    return axios({
        url: '/loginLog/delete',
        method: 'DELETE',
        params: {
            id
        }
    })
}