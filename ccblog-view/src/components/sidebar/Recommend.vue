<template>
  <div class="ui segments m-box">
    <div class="ui segment"><i class="bookmark icon"></i>推荐文章</div>
    <div class="ui teal segment">
      <div class="ui divided items">
        <div class="m-item" v-for="blog in randomBlogList" :key="blog.id" @click.prevent="toBlog(blog.id)">
          <div class="img" :style="{'background-image':'url(' + blog.firstPicture + ')'}"></div>
          <div class="info">
            <div class="date">{{ blog.createTime | dateFormat('YYYY-MM-DD') }}</div>
            <div class="title">{{ blog.title }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Recommend",
  data(){
    return{
    }
  },
  props:{
    randomBlogList:{
      type:Array,
      require:true
    }
  },
  methods:{
    toBlog(id){
      this.$router.push(`/blog/${id}`)
    }
  }
}
</script>

<style scoped>
.ui.divided.items .m-item:first-child {
  margin-top: 0;
}

.ui.divided.items .m-item {
  margin-top: 1rem;
  height: 7rem;
  position: relative;
  overflow: hidden;
  border-radius: 5px;
  cursor: pointer;
  user-select: none;
}

.ui.divided.items .m-item .img {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  object-fit: cover;
  background-position-x: center;
  background-position-y: center;
  background-size: cover;
}

.ui.divided.items .m-item .info {
  z-index: 1;
  background: linear-gradient(to bottom, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.8));
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  padding: .5rem !important;
  font-size: 12px;
  color: white;
}

.ui.divided.items .m-item .info .title {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  word-break: break-word;
}
</style>