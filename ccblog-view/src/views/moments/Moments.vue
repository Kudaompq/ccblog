<template>
  <div class="m-box">
    <div class="ui top attached segment" style="text-align: center">
      <h2 class="m-text-500">我的动态</h2>
    </div>
    <div class="ui attached segment">
      <div class="moments">
        <div class="moment" v-for="(moment,index) in momentList" :key="index">
          <div class="avatar">
            <img :src="$store.state.introduction.avatar" alt="">
          </div>
          <div class="ui card">
            <div class="content m-top">
              <span style="font-weight: 700">{{$store.state.introduction.name}}</span>
              <span class="right floated">{{moment.createTime | dateFromNow}}</span>
            </div>
            <div class="content typo" v-html="moment.content"></div>
            <div class="extra content">
              <a class="left floated" @click="like(moment.id)">
                <i class="heart icon" :class="isLike ? 'like-color':'outline'">{{moment.likes}}</i>
              </a>
            </div>
          </div>
        </div>
      </div>
      <div style="text-align: center; margin-top: 20px">
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

  </div>
</template>

<script>
import {getMomentList,likeMoment} from "@/api/moment";
export default {
  name: "Moments",
  data(){
    return{
      momentList: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      likeMomentIds: JSON.parse(window.localStorage.getItem('likeMomentIds') || '[]')
    }
  },
  created() {
    this.getData()
  },
  watch:{
    likeMomentIds(newValue){
      // 存储最新的已点击的ID
      window.localStorage.setItem('likeMomentIds',JSON.stringify(newValue))
    }
  },
  methods:{
    getData(){
      getMomentList(this.pageNum).then(res =>{
        if (res.code === 200){
          this.momentList = res.data.data;
          this.total = res.data.total
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
    },
    like(id){
      if (this.likeMomentIds.indexOf(id) > -1){
        this.$notify({
          title: '不可以重复点赞哦',
          type: 'warning'
        })
        return
      }
      likeMoment(id).then(res =>{
        if (res.code === 200){
          this.$notify({
            title: res.msg,
            type: 'success'
          })
          this.likeMomentIds.push(id)
          this.momentList.forEach(item => {
            if (item.id === id) {
              return item.likes++
            }
          })
        }else{
          this.$notify({
            title: res.msg,
            type: 'warning'
          })
        }
      }).catch(()=>{
        this.$notify({
          title: '请求错误',
          type: 'error'
        })
      })
    },
    isLike(id){
      if (this.likeMomentIds.indexOf(id) > -1) return true;
      return false;
    }
  },

}
</script>

<style scoped>
.avatar {
  margin-left: -62.5px;
  float: left !important;
}

.avatar img {
  height: 45px;
  width: 45px;
  border-radius: 500px;
}

.moments {
  margin-left: 26px !important;
  padding-left: 40px !important;
  border-left: 1px solid #dee5e7 !important;
}

.moment {
  margin-top: 30px;
}

.moment:first-child {
  margin-top: 0 !important;
}

.card {
  width: 100% !important;
}

.card:before {
  border-width: 0 0 1px 1px !important;
  transform: translateX(-50%) translateY(-50%) rotate(45deg) !important;
  bottom: auto !important;
  right: auto !important;
  top: 22px !important;
  left: 0 !important;
  position: absolute !important;
  content: '' !important;
  background-image: none !important;
  z-index: 2 !important;
  width: 12px !important;
  height: 12px !important;
  transition: background .1s ease !important;
  background-color: inherit !important;
  border-style: solid !important;
  border-color: #d4d4d5 !important;
}

.content.m-top {
  padding: 10px 14px !important;
}

.content .right.floated {
  font-size: 12px !important;
}

.content.typo * {
  font-size: 14px !important;
}

.extra.content {
  padding: 5px 14px !important;
}

.extra.content a {
  color: rgba(0, 0, 0, 0.7) !important;
  font-size: 12px !important;
}

.extra.content a:hover {
  color: red !important;
}

.extra.content .like-color {
  color: red !important;
}

.extra.content i {
  font-size: 12px !important;
}
</style>