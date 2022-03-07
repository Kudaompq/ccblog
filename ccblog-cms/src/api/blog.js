import axios from "@/utils/request"

export function getBlogInfoByQuery(queryInfo){
    return axios({
        url: '/blog/list',
        method: 'GET',
        params: {
            ...queryInfo
        }
    })
}

export function updateBlogTop(id,top){
    return axios({
        url: '/blog/top',
        method: 'PUT',
        params:{
            id,
            top
        }
    })
}
export function updateBlogPublished(id,status){
    return axios({
        url: '/blog/published',
        method: 'PUT',
        params:{
            id,
            status
        }
    })
}

export function updateBlogRecommend(id,recommend){
    return axios({
        url: '/blog/recommend',
        method: 'PUT',
        params:{
            id,
            recommend
        }
    })
}

export function deleteBlogById(id){
    return axios({
        url: '/blog/delete',
        method: 'DELETE',
        params:{
            id
        }
    })
}

export function getCategoryAndTag(){
    return axios({
        url: '/blog/categoryAndTag',
        method: 'GET'
    })
}

export function saveBlog(blog) {
    return axios({
        url: '/blog/save',
        method: 'POST',
        data: {
            ...blog
        }
    })
}

export function updateBlogVisibility(id,visForm){
    return axios({
        url: `/blog/${id}/visibility`,
        method: 'PUT',
        data:{
            ...visForm
        }
    })
}

export function getBlog(id){
    return axios({
        url: `/blog/${id}`,
        method: 'GET'
    })
}
