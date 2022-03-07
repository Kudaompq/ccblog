import axios from "@/plugins/axios";

export function getBlogList(pageNum) {
    return axios({
        url: '/blog/list',
        method: 'GET',
        params:{
            pageNum
        }
    })

}