import axios from "@/plugins/axios";

export function getSite() {
    return axios({
        url: '/site',
        method: 'GET'
    })
}