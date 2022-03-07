export default {
    clientSize:{
        clientHeight: 0,
        clientWidth: 1080
    },
    siteInfo: {
        reward: ''
    },
    introduction:{
        avatar: '',
        name: '',
        rollText: ''
    },
    commentQuery:{
        // 0：博客 1：关于我 2：友链
        page: 0,
        blogId: null,
        pageNum: 1,
        pageSize: 5
    },
    allComment: 0,
    allParentComment: 0,
    closeComment: 0,
    commentTotalPage:0,
    comments: [],
    parentCommentId: -1,
    commentForm: {
        content: '',
        nickname: '',
        email: '',
        website: '',
        notice: true
    },
    isBlogToHome: false,
    // 博客文章渲染完成标记
    isBlogRenderComplete: false
}