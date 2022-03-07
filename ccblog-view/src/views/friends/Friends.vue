<template>
  <div>
    <div class="ui top attached segment" style="text-align: center">
      <h2 class="m-text-500">友情链接</h2>
    </div>
    <div class="ui attached segment">
      <div class="ui link three doubling cards">
        <a :href="item.website" target="_blank" class="card" :style="randomRGB()"
        v-for="(item,index) in friendList" :key="index">
          <div class="image">
            <img :src="item.avatar" alt="">
          </div>
          <div class="content">
            <div class="header">{{item.nickname}}</div>
            <div class="description">{{item.description}}</div>
          </div>
        </a>
      </div>
    </div>
    <div class="ui teal attached segment">
      <div class="typo content" v-viewer v-html="friendInfo.content"></div>
    </div>
    <div class="ui teal attached segment threaded comments">
      <CommentList v-if="friendInfo.commentEnabled" :page="2" :blog-id="null"></CommentList>
    </div>
  </div>
</template>

<script>
import {getFriendListAndInfo} from "@/api/friend";
import CommentList from "@/components/comment/CommentList";
export default {
  name: "Friends",
  components:{CommentList},
  data(){
    return {
      friendList:[],
      bgColor: [
        '#1abc9c', '#2ecc71', '#3498db', '#9b59b6',
        '#34495e', '#f1c40f', '#e67e22', '#e74c3c',
        '#ee5a24', '#9980fa', '#8c7ae6', '#f79f1f'
      ],
      friendInfo:{
        content: '<h1>冲冲冲</h1><p>欢迎添加友链</p>',
        commentEnabled: 'true'
      }
    }
  },
  created() {
    this.getData()
  },
  methods:{
    randomRGB() {
      const index = Math.floor((Math.random() * this.bgColor.length))
      return {backgroundColor: this.bgColor[index]}
    },
    getData(){
      getFriendListAndInfo().then(res => {
        if (res.code === 200){
          this.friendList = res.data.friendList
          this.friendInfo = res.data.friendInfo
        }else{
          this.msgError(res.msg)
        }
      }).catch(()=>{
        this.msgError("请求失败")
      })
    }
  }
}
</script>

<style scoped>
  .card .image{
    width: 70px !important;
    margin: 10px auto 0px;
    background: unset !important;
  }
  .card .image img{
    border-radius: 100% !important;
    width: 70px;
    height: 70px;
  }
  .card .content{
    text-align: center;
    padding: 10px 14px !important;
    border-top: 0!important;
  }
  .card .content *{
    color: #ffffff !important;
  }
  .card .content .header {
    font-size: 16px !important;
  }

  .card .content .description {
    font-size: 12px !important;
  }
  .card .content .description {
    margin-top: 5px !important;
    margin-bottom: 7px;
  }

</style>