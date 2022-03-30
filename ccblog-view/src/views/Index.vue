<template>
  <div>
    <Nav :categoryList="categoryList"></Nav>
    <!--<div class="m-mobile-hide">-->
    <!--  <Header v-if="$route.name==='home'"></Header>-->
    <!--</div>-->
    <div class="main">
      <div class="m-padded-tb-big">
        <div class="ui container">
          <div class="ui stackable grid">
            <!--左侧个人面板-->
            <div class="four wide column m-mobile-hide">
              <Introduce v-if="$route.name !== 'blog'"></Introduce>
              <Recommend :randomBlogList="randomBlogList" v-if="$route.name !== 'blog'"></Recommend>
              <Categories :categoryList="categoryList" v-if="$route.name !== 'blog'"></Categories>
              <Tags :tagList="tagList" v-if="$route.name !== 'blog'"></Tags>
              <Tocbot v-if="$route.name==='blog'" />
            </div>
            <!--中间主要内容-->
            <div class="twelve wide column">
              <keep-alive include="Home">
                <router-view/>
              </keep-alive>
            </div>

          </div>
        </div>
      </div>
      <el-backtop>
        <div
            style="{
        height: 100%;
        width: 100%;
        text-align: center;
        line-height: 40px;
      }"
        >
          UP
        </div>
      </el-backtop>
    </div>

    <Footer :siteInfo="siteInfo"></Footer>

  </div>
</template>

<script>
import Nav from "@/components/index/Nav";
import Header from "@/components/index/Header";
import Introduce from "@/components/sidebar/Introduce";
import Categories from "@/components/sidebar/Categories";
import Tags from "@/components/sidebar/Tags";
import Recommend from "@/components/sidebar/Recommend";
import Footer from "@/components/index/Footer";
import Tocbot from "@/components/sidebar/Tocbot";
import {getSite} from "@/api/index";
import {mapState} from 'vuex'
export default {
  name: "Index",
  components: {Introduce, Header, Nav,Tags,Categories,Recommend,Tocbot,Footer},
  data(){
    return{
      siteInfo: {
        blogName: ''
      },
      categoryList: [],
      tagList: [],
      randomBlogList: [],
    }
  },
  created() {
    this.getData()
  },
  mounted() {
    this.$store.commit("SAVE_CLIENT_SIZE",{clientHeight:document.body.clientHeight , clientWidth: document.body.clientWidth})
    window.onresize = () => {
      this.$store.commit("SAVE_CLIENT_SIZE", {clientHeight: document.body.clientHeight, clientWidth: document.body.clientWidth})
    }
  },
  watch:{
    '$route.name'(){
      this.scrollToTop();
    }
  },
  methods:{
    getData(){
      getSite().then(res => {
        if (res.code === 200){
          this.siteInfo = res.data.siteInfo
          this.categoryList = res.data.categoryList
          this.tagList = res.data.tagList
          this.randomBlogList = res.data.randomBlogList
          this.$store.commit('SAVE_SITE_INFO',this.siteInfo)
          this.$store.commit('SAVE_INTRODUCTION',res.data.introduction)
          document.title = this.$route.meta.title + this.siteInfo.webTitleSuffix
        }
      })
    }
  }
}
</script>

<style scoped>
.site {
  display: flex;
  min-height: 100vh; /* 没有元素时，也把页面撑开至100% */
  flex-direction: column;
}

.main {
  margin-top: 60px;
  flex: 1;
}

.main .ui.container {
  width: 1110px !important;
  margin-left: auto !important;
  margin-right: auto !important;
}

.ui.grid .three.column {
  padding: 0;
}

.ui.grid .ten.column {
  padding-top: 0;
}

.m-display-none {
  display: none !important;
}
</style>