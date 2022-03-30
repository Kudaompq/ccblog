<template>
  <div >
    <div class="ui top segment" style="text-align: center">
      <h2 class="m-text-500">分类 {{ categoryName }} 下的文章</h2>
    </div>
    <BlogList :blogList="blogList"></BlogList>
    <div style="text-align: center">
      <el-pagination v-if="total !== 0"
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
import {getBlogListByCategoryId} from "@/api/category";
import BlogList from "@/components/blog/BlogList";
export default {
  name: "Category",
  components:{BlogList},
  data(){
    return{
      blogList:[],
      total: 0,
      pageNum: 1,
      pageSize: 5
    }
  },
  computed:{
    categoryId(){
      return parseInt(this.$route.params.id)
    },
    categoryName(){
      return this.$route.params.name
    }
  },
  watch:{
    '$route.fullPath'(){
      if (this.$route.name === 'category') {
        this.getData()
      }
    }
  },
  created() {
    this.getData()
  },
  methods:{
    getData(){
      getBlogListByCategoryId(this.pageNum,this.categoryId).then(res=>{
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