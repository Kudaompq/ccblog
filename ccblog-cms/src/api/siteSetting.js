import axios from "@/utils/request";


export function getSiteSettingList() {
    return axios({
        url: '/siteSetting/list',
        method: 'GET'
    })
}

export function updateSiteSetting(settings,deleteIds){
    return axios({
        url: '/siteSetting/update',
        method:'POST',
        data: {
            settings,
            deleteIds
        }
    })
}