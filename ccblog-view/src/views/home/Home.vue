<template>
  <div>
    <BlogList :blogList="blogList"></BlogList>
    <div style="text-align: center">
      <el-pagination
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
import {getBlogList} from "@/api/home";
import BlogList from "@/components/blog/BlogList";
export default {
  name: "Home",
  components:{BlogList},
  data(){
    return{
      blogList:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      getBlogListFinish: false
    }
  },
  beforeRouteEnter(to,from,next){
    next(vm =>{
      if (from.name !== 'blog') {
        //其它页面跳转到首页时，重新请求数据
        //设置一个flag，让首页的分页组件指向正确的页码
        vm.$store.commit('SET_IS_BLOG_TO_HOME', false)
        vm.getBlogList()
      } else {
        //如果文章页面是起始访问页，首页将是第一次进入，即缓存不存在，要请求数据
        if (!vm.getBlogListFinish) {
          vm.getBlogList()
        }
        //从文章页面跳转到首页时，使用首页缓存
        vm.$store.commit('SET_IS_BLOG_TO_HOME', true)
      }
    })
  },
  methods:{
    getBlogList(){
      getBlogList(this.pageNum).then(res => {
        if (res.code === 200){
          this.blogList = res.data.data
          this.total = res.data.total
          this.$nextTick(() => {
            Prism.highlightAll()
          })
          this.getBlogListFinish = true
        }else{
          this.msgError(res.msg)
        }
      }).catch(()=>{
        this.msgError("请求失败")
      })
    },
    //监听页码改变的事件
    handleCurrentChange(newPage) {
      this.pageNum = newPage
      this.getData()
    }
  }
}
</script>

<style scoped>

</style>