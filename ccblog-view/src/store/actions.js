import {getCommentList, submitComment} from "@/api/comment";
import {Message, Notification} from "element-ui";

export default {
    getCommentList({commit,rootState}){
        const adminToken = window.localStorage.getItem('adminToken')
        getCommentList(adminToken,rootState.commentQuery).then(res => {
            if (res.code === 200){
                commit('SAVE_COMMENT_RESULT',res.data)
            }
        })
    },
    submitCommentForm({rootState, dispatch, commit}, token) {
        let form = {...rootState.commentForm}
        form.page = rootState.commentQuery.page
        form.blogId = rootState.commentQuery.blogId
        form.parentCommentId = rootState.parentCommentId
        submitComment(token, form).then(res => {
            if (res.code === 200) {
                Notification({
                    title: res.msg,
                    type: 'success'
                })
                commit('SET_PARENT_COMMENT_ID', -1)
                commit('RESET_COMMENT_FORM')
                dispatch('getCommentList')
            } else {
                Notification({
                    title: '评论失败',
                    message: res.msg,
                    type: 'error'
                })
            }
        }).catch(() => {
            Notification({
                title: '评论失败',
                message: '异常错误',
                type: 'error'
            })
        })
    },
}