<template>
  <!--评论分页-->
  <el-pagination @current-change="handleCurrentChange"
                 :current-page="commentQuery.pageNum"
                 :total="allParentComment"
                 :page-size="commentQuery.pageSize"
                 layout="prev, pager, next" background class="pagination">
  </el-pagination>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: "Pagination",
  computed:{
    ...mapState(['commentQuery','allComment','allParentComment'])
  },
  methods:{
    handleCurrentChange(newPage){
      this.$store.commit('SET_COMMENT_QUERY_PAGE_NUM',newPage)
      this.$store.commit('SET_PARENT_COMMENT_ID',-1)
      this.$store.dispatch('getCommentList')
    }
  }
}
</script>

<style scoped>

</style>