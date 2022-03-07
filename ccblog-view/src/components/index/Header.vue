<template>
  <header ref="header">
    <div class="view">
      <div style="background-image: url('https://cdn.jsdelivr.net/gh/Kudaompq/CDN/cover/20220216161058.jpg');"></div>
    </div>
    <div>
      <h1 class="m-site-title">Kudaompq's Blog</h1>
    </div>
    <div class="wrapper">
      <i class="iconfont icon-xiala" @click="scrollToMain" style="color: white"></i>
    </div>
    <div class="wave1"></div>
    <div class="wave2"></div>
  </header>
</template>

<script>
import {mapState} from 'vuex'
import Typed from 'typed.js'
export default {
  name: "Header",
  computed: {
    ...mapState(['clientSize'])
  },
  watch: {
    'clientSize.clientHeight'() {
      this.setHeaderHeight()
    }
  },
  mounted() {

    this.setHeaderHeight()
  },
  methods: {
    //根据可视窗口高度，动态改变首图大小
    setHeaderHeight() {
      this.$refs.header.style.height = this.clientSize.clientHeight + 'px'
    },
    //平滑滚动至正文部分
    scrollToMain() {
      window.scrollTo({top: this.clientSize.clientHeight, behavior: 'smooth'})
    }
  },
}
</script>

<style scoped>
header {
  --percentage: 0.5;
  user-select: none;
}

.view {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  display: flex;
  justify-content: center;
}
.view div {
  background-position: center center;
  background-size: cover;
  position: absolute;
  width: 120%;
  height: 100%;
}

.wrapper {
  position: absolute;
  width: 100px;
  bottom: 150px;
  left: 0;
  right: 0;
  margin: auto;
  font-size: 26px;
  z-index: 100;
}

.wrapper i {
  font-size: 60px;
  opacity: 0.5;
  cursor: pointer;
  position: absolute;
  top: 55px;
  left: 20px;
  animation: opener .5s ease-in-out alternate infinite;
  transition: opacity .2s ease-in-out, transform .5s ease-in-out .2s;
}

.wrapper i:hover {
  opacity: 1;
}

@keyframes opener {
  100% {
    top: 65px
  }
}
.m-site-title{
  position: absolute;
  top: 40%;
  left: 40%;
  font-size: 2.85em;
  color: #ffffff;
}

</style>