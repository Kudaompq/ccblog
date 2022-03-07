<template>
  <div>
    <div class="ui padded attached segment m-padded-tb-large m-margin-bottom-big m-box">
      <div class="ui large red right corner label" v-if="blog.top">
        <i class="arrow alternate circle up icon"></i>
      </div>
      <div class="ui middle aligned mobile reversed stackable">
        <div class="ui grid m-margin-lr">
          <!--标题-->
          <div class="row m-padded-tb-small">
            <h2 class="ui header m-center m-title-up">
              <a href="javascript:;" @click.prevent="toBlog(blog.id)" class="m-black">{{ blog.title }}</a>
            </h2>
          </div>
          <!--文章简要信息-->
          <div class="row m-padded-tb-small">
            <div class="ui horizontal link list m-center">
              <div class="item">
                <i class="small calendar icon m-datetime"></i><span>{{ blog.createTime | dateFormat('YYYY-MM-DD')}}</span>
              </div>
              <div class="item">
                <i class="small eye icon m-views" ></i><span>{{ blog.views }}</span>
              </div>
              <div class="item" v-for="tag in blog.tagList" :key="tag.id">
                <router-link :to="`/tag/${tag.id}`">
                  <i class="small tag icon m-tag"  :style="'color:'+getColor(tag.id)" ></i>{{tag.name}}
                </router-link>
              </div>
            </div>
          </div>
          <!--分类-->
          <router-link :to="`/category/${blog.category.id}`" class="ui orange large ribbon label">
            <i class="small folder open icon"></i><span class="m-text-500">{{ blog.category.name }}</span>
          </router-link>
          <!--文章Markdown描述-->
          <div class="typo m-padded-tb line-numbers match-braces rainbow-braces" v-html="blog.description"></div>
          <div class="m-padded-tb-small">
            <img :src="blog.firstPicture" class="typo image" alt="">
          </div>
          <!--阅读全文按钮-->
          <div class="row m-padded-tb-small m-margin-top">
            <a href="javascript:;" @click.prevent="toBlog(blog.id)" class="color-btn">阅读全文</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BlogItem",
  props:{
    blog:{
      type: Object,
      required: true
    }
  },
  methods:{
    getColor(id){
      let colors = ['red','orange','yellow','olive','green','teal','blue','violet','purple','pink','grey']
      return colors[id % colors.length]
    },
    toBlog(id){
      this.$router.push(`/blog/${id}`)
    }
  }
}
</script>

<style scoped>

</style>