<template>
<div class="m-box">
  <div class="ui top attached segment m-padded-lr-big" v-if="about">
    <h2 class="m-text-500" style="text-align: center">{{ about.title }}</h2>
    <meting-js server="netease" type="song" :id="1441758494" theme="#25CCF7" v-if="about.musicId!==''"></meting-js>
    <div class="typo content m-margin-top-large" v-viewer v-html="about.content"></div>
  </div>
  <div class="ui teal attached segment threaded comments">
    <CommentList v-if="about.commentEnabled" :page="1" :blog-id="null"></CommentList>
  </div>
</div>
</template>

<script>
import {getAbout} from "@/api/about";
import CommentList from "@/components/comment/CommentList";
export default {
  name: "About",
  components: {CommentList},
  data(){
    return{
      about:{}
    }
  },
  created() {
    this.getData()
  },
  methods:{
    getData(){
      getAbout().then(res =>{
        if (res.code === 200){
          this.about = res.data
        }else{
          this.msgError(res.msg)
        }
      }).catch(() =>{
        this.msgError("请求失败")
      })
    }
  }
}
</script>

<style scoped>
.content ul li {
  letter-spacing: 1px !important;
}
</style>