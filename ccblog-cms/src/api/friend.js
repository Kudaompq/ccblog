import axios from '@/utils/request'

export function getFriendList(queryInfo) {
    return axios({
        url: '/friend/list',
        method: 'GET',
        params:{
            ...queryInfo
        }
    })
}

export function getFriendInfo(){
    return axios({
        url: '/friend/info',
        method: 'GET'
    })
}

export function updateFriendInfoCommentEnabled(commentEnabled){
    return axios({
        url: '/friend/comment',
        method: 'PUT',
        params:{
            commentEnabled
        }
    })
}

export function updateFriendPublished(id,published) {
    return axios({
        url: '/friend/published',
        method: 'PUT',
        params:{
            id,published
        }
    })
}

export function deleteFriend(id) {
    return axios({
        url:'/friend/delete',
        method: 'DELETE',
        params:{
            id
        }
    })
}

export function updateFriendInfoContent(content){
    return axios({
        url: '/friend/content',
        method: 'PUT',
        params:{
            content
        }
    })
}

export function saveFriend(friend){
    return axios({
        url: '/friend/save',
        method: 'POST',
        data:{
            ...friend
        }
    })
}

export function updateFriend(friend) {
    return axios({
        url: '/friend/update',
        method: 'PUT',
        data:{
            ...friend
        }
    })
}