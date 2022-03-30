<template>
  <div align="center" class="footer-wrap ">
      <p v-text="siteInfo.copyright.title"></p>
      <a href="https://www.miit.gov.cn/" target="_blank" style="color: white">
        <img class="icp-icon" src="https://s1.ax1x.com/2018/09/29/ilmwIH.png" data-original="https://s1.ax1x.com/2018/09/29/ilmwIH.png">
        <span>备案号：{{siteInfo.beian}}</span>
      </a>

  </div>
</template>

<script>
export default {
  name: "Footer",
  props:{
    siteInfo:{
      type: Object,
      require: true
    }
  },
  created() {
  }
}
</script>

<style scoped>
.icp-icon {
  padding: 0 4px;
  vertical-align: text-bottom;
  max-height: 1.4em;
  width: auto;
}
</style>