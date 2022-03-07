import axios from "@/plugins/axios";

export function getArchive() {
    return axios({
        url: '/archive',
        method: 'GET'
    })
}