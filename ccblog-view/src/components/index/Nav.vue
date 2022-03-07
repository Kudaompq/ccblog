<template>
  <div ref="nav" class="ui fixed inverted stackable pointing  menu" :class="{'transparent': $route.name==='home' && clientSize.clientWidth>768}">
    <div class="ui container">
      <router-link to="/">
        <h3 class="ui header item m-grey ">{{ $store.state.siteInfo.blogName }}</h3>
      </router-link>

      <router-link to="/home" class="item right" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='home'}">
        首页
      </router-link>
      <el-dropdown trigger="click" @command="categoryRoute">
				<span class="el-dropdown-link item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='category'}">
					分类<i class="caret down icon"></i>
				</span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item :command="category" v-for="(category,index) in categoryList" :key="index">{{ category.name }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <router-link to="/archives" class="item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='archives'}">
        归档
      </router-link>
      <router-link to="/moments" class="item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='moments'}">
        动态
      </router-link>
      <router-link to="/friends" class="item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='friends'}">
        友人帐
      </router-link>
      <router-link to="/about" class="item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='about'}">
        关于我
      </router-link>
      <el-autocomplete v-model="queryString" :fetch-suggestions="querySearch" placeholder="Search..."
                       class="item m-search"
                       popper-class="m-search-item"
                       @select="handleSelect"
                      :class="{'m-mobile-hide':mobileHide}">
        <i class="search icon el-input__icon" slot="suffix"></i>
        <template slot-scope="{ item }">
          <div class="title">{{ item.title }}</div>
          <span class="content">{{ item.content }}</span>
        </template>
      </el-autocomplete>
      <button class="ui menu black icon button m-right-top m-mobile-show" @click="toggle">
        <i class="sidebar icon"></i>
      </button>
    </div>

  </div>
</template>

<script>
import {getSearchBlogList} from "@/api/blog";
import {mapState} from 'vuex'
export default {
  name: "Nav",
  data(){
    return {
      mobileHide:true, // 用来解决响应式问题
      queryString: '',
      queryResult: [],
      timer: null,
    }
  },
  props:{
    categoryList:{
      type: Array,
      require: true
    }
  },
  computed: {
  ...mapState(['clientSize'])
  },
  watch: {
    // 路由改变时，收起导航栏
    '$route.path'() {
      this.mobileHide = true
    }
  },
  mounted() {
    //监听页面滚动位置，改变导航栏的显示
    window.addEventListener('scroll', () => {
      //首页且不是移动端
      if (this.$route.name === 'home' && this.clientSize.clientWidth > 768) {
        if (window.scrollY > this.clientSize.clientHeight / 2) {
          this.$refs.nav.classList.remove('transparent')
          this.$refs.nav.classList.add('m-background-color')
        } else {
          this.$refs.nav.classList.add('transparent')

        }
      }
    })
    //监听点击事件，收起导航菜单
    document.addEventListener('click', (e) => {
      //遍历冒泡
      let flag = e.path.some(item => {
        if (item === this.$refs.nav) return true
      })
      //如果导航栏是打开状态，且点击的元素不是Nav的子元素，则收起菜单
      if (!this.mobileHide && !flag) {
        this.mobileHide = true
      }
    })
  },
  methods:{
    querySearch(queryString,callback){
      this.timer && clearTimeout(this.timer)
      this.timer = setTimeout(() => this.querySearchAsync(queryString,callback),1000)
    },
    querySearchAsync(queryString,callback){
      if (queryString == null
          || queryString.trim() === ''
          || queryString.indexOf('%') !== -1
          || queryString.indexOf('_') !== -1
          || queryString.indexOf('[') !== -1
          || queryString.indexOf('#') !== -1
          || queryString.indexOf('*') !== -1
          || queryString.trim().length > 20) {
        return
      }
      getSearchBlogList(queryString).then(res => {
        if (res.code === 200){
          this.queryResult = res.data
          if (this.queryResult.length === 0){
            this.queryResult.push({title: '无相关搜索结果'})
          }
          callback(this.queryResult)
        }
      }).catch(()=>{
        this.msgError("请求失败")
      })
    },
    handleSelect(item){
      this.$router.push(`/blog/${item.id}`)
    },
    toggle() {
      this.mobileHide = !this.mobileHide
    },
    categoryRoute(cate){
      this.$router.push({
        name: 'category',
        params: {
          id: cate.id,
          name: cate.name
        }
      })
    }
  }
}
</script>

<style scoped>
.ui.fixed.menu .container {
  width: 1400px !important;
  margin-left: auto !important;
  margin-right: auto !important;
}
.ui.fixed.menu {
  transition: .3s ease-out;
}

.ui.inverted.menu.pointing.transparent {
  background: transparent !important;
}

.ui.inverted.menu.pointing.transparent .active.item:after {
  background: transparent !important;
  transition: .3s ease-out;
}

.ui.inverted.menu.pointing.transparent .active.item:hover:after {
  background: transparent !important;
}

.el-dropdown-link {
  outline-style: none !important;
  outline-color: unset !important;
  height: 100%;
  cursor: pointer;
}

.el-dropdown-menu {
  margin: 7px 0 0 0 !important;
  padding: 0 !important;
  border: 0 !important;
  background: #1b1c1d !important;
}

.el-dropdown-menu__item {
  padding: 0 15px !important;
  color: rgba(255, 255, 255, .9) !important;
}

.el-dropdown-menu__item:hover {
  background: rgba(255, 255, 255, .08) !important;
}

.m-search {
  min-width: 220px;
  padding: 0 !important;
}
.m-search >>> .el-input__inner {
  color: rgba(255, 255, 255, .9);
  border: 0px !important;
  background-color: inherit;
  padding: .67857143em 2.1em .67857143em 1em;
}

.m-search  i {
  color: rgba(255, 255, 255, .9) !important;
}

.m-search-item li {
  line-height: normal !important;
  padding: 8px 10px !important;
}

.m-search-item li .title {
  text-overflow: ellipsis;
  overflow: hidden;
  color: rgba(0, 0, 0, 0.87);
}

.m-search-item li .content {
  text-overflow: ellipsis;
  font-size: 12px;
  color: rgba(0, 0, 0, .70);
}
.ui.header.item:before{
  width: 0px;
}

</style>