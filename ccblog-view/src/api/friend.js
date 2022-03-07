import axios from "@/plugins/axios";

export function getFriendListAndInfo() {
    return axios({
        url: '/friend/list',
        method: 'GET'
    })
}