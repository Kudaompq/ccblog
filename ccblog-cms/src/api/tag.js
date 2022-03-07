import axios from "@/utils/request"

export function getTagListByQueryInfo(queryInfo) {
    return axios({
        url: '/tag/list',
        method: 'GET',
        params: {
            ...queryInfo
        }
    })
}

export function saveTag(tagForm){
    return axios({
        url: '/tag/save',
        method: 'POST',
        data:{
            ...tagForm
        }
    })
}

export function editTag(tagForm){
    return axios({
        url: '/tag/update',
        method: 'PUT',
        data:{
            ...tagForm
        }
    })
}

export function deleteTag(id) {
    return axios({
        url: '/tag/delete',
        method: 'DELETE',
        params: {
            id
        }
    })
}