<template>
  <div>
    <div class="ui top segment" style="text-align: center">
      <h2 class="m-text-500">标签 {{ tagName }} 下的文章</h2>
    </div>
    <BlogList :blogList="blogList"></BlogList>
    <div style="text-align: center">
      <el-pagination
          v-if="total !== 0"
          background
          layout="prev, pager, next"
          :total="total"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-size="pageSize"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import {getBlogListByTagId} from "@/api/tag";
import BlogList from "@/components/blog/BlogList";
export default {
  name: "Tag",
  components:{BlogList},
  data(){
    return{
      blogList:[], // 之后通过请求获取
      total: 0,
      pageNum: 1,
      pageSize: 5
    }
  },
  computed:{
    tagId(){
      return parseInt(this.$route.params.id)
    },
    tagName(){
      return this.$route.params.name
    }
  },
  watch:{
    '$route.fullPath'(){
      if (this.$route.name === 'tag') {
        this.getData()
      }
    }
  },
  created() {
    this.getData()
  },
  methods:{
    getData(){
      getBlogListByTagId(this.pageNum,this.tagId).then(res=>{
        if (res.code === 200){
          this.blogList = res.data.data
          this.total = res.data.total
        }else{
          this.msgError(res.msg)
        }
      }).catch(()=>{
        this.msgError("请求失败")
      })
    },
    handleCurrentChange(newPage){
      this.pageNum = newPage
      this.getData()
    }
  }
}
</script>

<style scoped>

</style>