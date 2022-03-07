import axios from '@/utils/request'

export function getCommentListByPage(queryInfo) {
    return axios({
        url: '/comments/list',
        method: 'GET',
        params:{
            ...queryInfo
        }
    })
}

export function getBlogList(){
    return axios({
        url: '/comments/blogIdAndTitle',
        method:'GET'
    })
}

export function updatePublished(id,published) {
    return axios({
        url: '/comments/published',
        method: 'PUT',
        params:{
            id,published
        }
    })
}

export function updateNotice(id,notice) {
    return axios({
        url: '/comments/notice',
        method: 'PUT',
        params:{
            id,notice
        }
    })
}

export function deleteComment(id){
    return axios({
        url: '/comments/delete',
        method: 'DELETE',
        params:{
            id
        }
    })
}

export function updateComment(comment){
    return axios({
        url: '/comments/update',
        method: 'PUT',
        data:{
            ...comment
        }
    })
}