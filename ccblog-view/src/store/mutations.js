export default {
    SAVE_SITE_INFO(state,siteInfo){
        state.siteInfo = siteInfo
    },
    SAVE_INTRODUCTION(state, introduction) {
        state.introduction = introduction
    },
    SAVE_CLIENT_SIZE(state,clientSize){
        state.clientSize = clientSize
    },
    SAVE_COMMENT_RESULT(state,data){
        state.allComment = data.allComment
        state.closeComment = data.closeComment
        state.commentTotalPage = data.comments.totalPage
        state.comments = data.comments.data
        state.allParentComment =data.comments.total
    },
    SET_COMMENT_QUERY_PAGE(state, page) {
        state.commentQuery.page = page
    },
    SET_COMMENT_QUERY_BLOG_ID(state, blogId) {
        state.commentQuery.blogId = blogId
    },
    SET_COMMENT_QUERY_PAGE_NUM(state, pageNum) {
        state.commentQuery.pageNum = pageNum
    },
    SET_PARENT_COMMENT_ID(state, parentCommentId) {
        state.parentCommentId = parentCommentId
    },
    RESET_COMMENT_FORM(state) {
        const commentForm = {
            nickname: state.commentForm.nickname,
            email: state.commentForm.email,
            website: state.commentForm.website
        }
        //保存访客信息，下次评论时自动填充表单
        window.localStorage.setItem('commentForm', JSON.stringify(commentForm))
        state.commentForm.content = ''
        state.commentForm.notice = true
    },
    RESTORE_COMMENT_FORM(state) {
        const lastForm = JSON.parse(window.localStorage.getItem('commentForm'))
        if (lastForm) {
            state.commentForm.nickname = lastForm.nickname
            state.commentForm.email = lastForm.email
            state.commentForm.website = lastForm.website
        }
    },
    SET_IS_BLOG_TO_HOME(state,isBlogToHome){
        state.isBlogToHome = isBlogToHome
    },
    SET_IS_BLOG_RENDER_COMPLETE(state,isBlogRenderComplete){
        state.isBlogRenderComplete = isBlogRenderComplete
    }
}