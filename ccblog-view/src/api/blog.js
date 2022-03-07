import axios from "@/plugins/axios";

export function getBlogById(id){
    return axios({
        url: '/blog',
        method: 'GET',
        params:{
            id
        }
    })
}

export function getSearchBlogList(query){
    return axios({
        url: '/blog/search',
        method: 'GET',
        params:{
            query
        }
    })
}