<template>
  <div>
    <Comment></Comment>
    <Pagination></Pagination>
  </div>
</template>

<script>
import Comment from "@/components/comment/Comment";
import Pagination from "@/components/comment/Pagination";
export default {
  name: "CommentList",
  components: {Pagination, Comment},
  props:{
    page:{
      type:Number,
      require:true
    },
    blogId:{
      type:Number,
      require: true
    }
  },
  created() {
    this.init()
  },
  methods:{
    init(){
      // 重置评论表单位置
      this.$store.commit('SET_PARENT_COMMENT_ID',-1)
      this.$store.commit('SET_COMMENT_QUERY_PAGE',this.page)
      this.$store.commit('SET_COMMENT_QUERY_BLOG_ID',this.blogId)
      this.$store.commit('SET_COMMENT_QUERY_PAGE_NUM',1)
      this.$store.dispatch('getCommentList')
    }
  }
}
</script>

<style scoped>
.pagination {
  margin-top: 2em;
  text-align: center;
}
</style>