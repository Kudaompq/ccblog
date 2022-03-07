import axios from "@/plugins/axios";

export function getBlogListByCategoryId(pageNum,id) {
    return axios({
        url: '/category/blogList',
        method: 'GET',
        params:{
            pageNum,
            id
        }
    })
}