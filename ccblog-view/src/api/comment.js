import axios from "@/plugins/axios";

export function getCommentList(token,query){
    return axios({
        url: '/comment/list',
        method: 'GET',
        headers: {
          Authorization: token
        },
        params: {
            ...query
        }
    })
}

export function submitComment(token,form){
    return axios({
        url: '/comment/submit',
        method: 'POST',
        headers:{
            Authorization: token
        },
        data:{
            ...form
        }
    })
}