import axios from "@/plugins/axios";

export function getBlogListByTagId(pageNum,id) {
    return axios({
        url: '/tag/blogList',
        method: 'GET',
        params:{
            pageNum,
            id
        }
    })
}