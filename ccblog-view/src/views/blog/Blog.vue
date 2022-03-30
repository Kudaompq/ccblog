<template>
  <div class="m-box">
    <div class="ui padded attached segment m-padded-tb-large">
      <div class="ui large red right corner label" v-if="blog.top">
        <i class="arrow alternate circle up icon"></i>
      </div>
      <div class="ui middle aligned mobile reversed stackable">
        <div class="ui grid m-margin-lr">
        <!--标题-->
          <div class="row m-padded-tb-small">
            <h2 class="ui header m-center">{{blog.title}}</h2>
          </div>
        <!--文章简要信息-->
          <div class="row m-padded-tb-small">
            <div class="ui horizontal link list m-center">
              <div class="item ">
                <i class="small calendar icon m-datetime"></i><span>{{ blog.createTime | dateFormat('YYYY-MM-DD') }}</span>
              </div>
              <div class="item ">
                <i class="small eye icon m-views"></i><span>{{ blog.views }}</span>
              </div>
              <div class="item" v-for="tag in blog.tags" :key="tag.id">
                <a @click="toTag(tag)"> <i class="small tag icon m-tag"  :style="'color:'+getColor(tag.id)" ></i>{{tag.name}}</a>
              </div>
              <a class="item m-common-black" @click.prevent="bigFontSize=!bigFontSize">
                <div data-inverted="" data-tooltip="点击切换字体大小" data-position="top center">
                  <i class="font icon"></i>
                </div>
              </a>
            </div>
          </div>
          <!--分类-->
          <a @click="categoryRoute(blog.category)" class="ui orange large ribbon label" v-if="blog.category">
            <i class="small folder open icon"></i><span class="m-text-500">{{ blog.category.name }}</span>
          </a>
        <!--文章Markdown正文-->
          <div class="typo js-toc-content m-padded-tb-small match-braces rainbow-braces" v-viewer :class="{'m-big-fontsize':bigFontSize}" v-html="blog.content"></div>
          <div style="margin: 2em auto">
            <el-popover placement="top" width="220" trigger="click" v-if="blog.appreciation">
              <div class="ui orange basic label" style="width: 100%">
                <div class="image">
                  <img :src="$store.state.siteInfo.reward" alt="" class="ui rounded bordered image" style="width: 100%">
                </div>
              </div>
              <el-button slot="reference" class="ui orange inverted circular button m-text-500">赞赏</el-button>
            </el-popover>
          </div>
        </div>
      </div>
      <!--博客信息-->
      <div class="ui attached positive message">
        <ul class="list">
          <li>作者：{{ $store.state.introduction.name }}
            <router-link to="/about">（联系作者）</router-link>
          </li>
          <li>发表时间：{{ blog.createTime | dateFormat('YYYY-MM-DD HH:mm') }}</li>
          <li>最后修改：{{ blog.updateTime | dateFormat('YYYY-MM-DD HH:mm') }}</li>
          <li>本站点采用<a href="https://creativecommons.org/licenses/by/4.0/" target="_blank"> 署名 4.0 国际 (CC BY 4.0) </a>创作共享协议。可自由转载、引用，并且允许商业性使用。但需署名作者且注明文章出处。</li>
        </ul>
      </div>
      <div class="ui bottom teal attached segment threaded comments">
        <CommentList :page="0" :blogId="blogId" v-if="blog.commentEnabled"/>
      </div>
    </div>
  </div>
</template>

<script>
import {getBlogById} from "@/api/blog";
import CommentList from "@/components/comment/CommentList";
import {mapState} from 'vuex'
export default {
  name: "Blog",
  components:{CommentList},
  data(){
    return{
      blog:{},
      bigFontSize: false
    }
  },
  computed:{
    blogId(){
      return parseInt(this.$route.params.id)
    },
    ...mapState(['siteInfo'])
  },
  beforeRouteEnter(to,from,next){
    next(vm =>{
      // 当 beforeRouteEnter 钩子执行前，组件实例尚未创建
      // vm 就是当前组件的实例，可以在 next 方法中把 vm 当做 this用
      vm.$store.commit('SET_IS_BLOG_RENDER_COMPLETE',false)
    })
  },
  beforeRouteLeave(to,from,next){
    tocbot.destroy()
    next()
  },
  beforeRouteUpdate(to,from,next){
    // 一般有两种情况会触发这个钩子
    // ①当前文章页面跳转到其它文章页面
    // ②点击目录跳转锚点时，路由hash值会改变，导致当前页面会重新加载，这种情况是不希望出现的
    // 在路由 beforeRouteUpdate 中判断路径是否改变
    // 如果跳转到其它页面，to.path!==from.path 就放行 next()
    // 如果是跳转锚点，path不会改变，hash会改变，to.path===from.path, to.hash!==from.path 不放行路由跳转，就能让锚点正常跳转
    if (to.path !== from.path){
      this.getBlog(to.params.id)
      this.$store.commit('SET_COMMENT_QUERY_BLOG_ID',false)
      next()
    }
  },
  created() {
    this.getBlog(this.blogId)
  },
  methods:{
    getColor(id){
      let colors = ['red','orange','yellow','olive','green','teal','blue','violet','purple','pink','grey']
      return colors[id % colors.length]
    },
    getBlog(id){
      getBlogById(id).then(res=>{
        if (res.code === 200){
          this.blog = res.data
          document.title = this.blog.title + this.siteInfo.webTitleSuffix
          //v-html渲染完毕后，渲染代码块样式
          this.$nextTick(() => {
            Prism.highlightAll()
            //将文章渲染完成状态置为 true
            this.$store.commit('SET_IS_BLOG_RENDER_COMPLETE', true)
          })
        }else{
          console.log(res)
          this.msgError(res.msg)
        }
      }).catch(() => {
        this.msgError("请求失败")
      })
    },
    categoryRoute(cate){
      this.$router.push({
        name: 'category',
        params: {
          id: cate.id,
          name: cate.name
        }
      })
    },
    toTag(tag){
      this.$router.push({
        name: 'tag',
        params: {
          id: tag.id,
          name: tag.name
        }
      })
    }
  }
}
</script>

<style scoped>
.el-divider {
  margin: 1rem 0 !important;
}

h1::before, h2::before, h3::before, h4::before, h5::before, h6::before {
  display: block;
  content: " ";
  height: 55px;
  margin-top: -55px;
  visibility: hidden;
}
</style>