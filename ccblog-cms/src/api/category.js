import axios from "@/utils/request"

export function getCategoryList(queryInfo) {
    return axios({
        url: '/category/list',
        method: 'GET',
        params: {
            ...queryInfo
        }
    })
}

export function saveCategory(category) {
    return axios({
        url: '/category/save',
        method: 'POST',
        data: {
            ...category
        }
    })
}

export function editCategory(category) {
    return axios({
        url: '/category/update',
        method: 'PUT',
        data: {
            ...category
        }
    })
}


export function deleteCategory(id) {
    return axios({
        url: '/category/delete',
        method: 'DELETE',
        params: {
            id
        }
    })
}