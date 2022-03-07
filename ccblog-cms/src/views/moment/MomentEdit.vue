<template>
  <div class="app-container">
    <el-form :model = "moment" label-position="top" @submit.native.prevent >
      <el-form-item label="动态内容" prop="content">
        <mavon-editor v-model="moment.content"/>
      </el-form-item>
      <el-form-item style="text-align: right;">
        <el-button type="success" @click="addMusic">添加音乐</el-button>
        <el-button type="info" @click="saveMomentPrivate" >仅自己可见</el-button>
        <el-button type="primary" @click="saveMomentPublished" >发布动态</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import Breadcrumb from "@/components/Breadcrumb";
import {getMoment,saveMoment,updateMoment} from '@/api/moment'
export default {
  name: "EditMoment",
  components: {Breadcrumb},
  data(){
    return{
      moment:{
        id: null,
        content: '',
        published: false,
      }
    }
  },
  created() {
    if (this.$route.params.id != null){
      this.getData(this.$route.params.id)
    }
  },
  methods:{
    getData(id){
      getMoment(id).then(res => {
        this.moment.id = res.data.id
        this.moment.content = res.data.content
        this.moment.published = res.data.published
      })
    },
    saveMomentPrivate(){
      this.moment.published = false
      if (this.moment.id == null){
        saveMoment(this.moment).then(res => {
          this.msgSuccess(res.data)
          this.$router.push("/moment/list")
        })
      }else{
        updateMoment(this.moment).then(res => {
          this.msgSuccess(res.data)
          this.$router.push("/moment/list")
        })
      }

    },
    saveMomentPublished(){
      this.moment.published = true
      if (this.moment.id == null){
        saveMoment(this.moment).then(res => {
          this.msgSuccess(res.data)
          this.$router.push("/moment/list")
        })
      }else{
        updateMoment(this.moment).then(res => {
          this.msgSuccess(res.data)
          this.$router.push("/moment/list")
        })
      }
    },
    addMusic(){
      this.moment.content += "<meting-js server=\"netease\" type=\"song\" id=\"1441758494\" theme=\"#25CCF7\"></meting-js>"
    }
  }
}
</script>

<style scoped>

</style>