<template>
  <div>
    <div class="ui padded attached segment m-padded-tb-large m-margin-bottom-big m-box">
      <div class="ui middle aligned mobile reversed stackable">
        <div class="ui grid m-margin-lr text-center">
          <!--&lt;!&ndash;标题&ndash;&gt;-->
          <!--<div class="row m-padded-tb-small">-->
          <!--  <h2 class="ui header m-center m-title-up">-->
          <!--    <a href="javascript:;" @click.prevent="toBlog(blog.id)" class="m-black">{{ blog.title }}</a>-->
          <!--  </h2>-->
          <!--</div>-->
          <!--&lt;!&ndash;文章简要信息&ndash;&gt;-->
          <!--<div class="row m-padded-tb-small">-->
          <!--  <div class="ui horizontal link list m-center">-->
          <!--    <div class="item">-->
          <!--      <i class="small calendar icon m-datetime"></i><span>{{ blog.createTime | dateFormat('YYYY-MM-DD')}}</span>-->
          <!--    </div>-->
          <!--    <div class="item">-->
          <!--      <i class="small eye icon m-views" ></i><span>{{ blog.views }}</span>-->
          <!--    </div>-->
          <!--    <div class="item" v-for="tag in blog.tagList" :key="tag.id">-->
          <!--      <router-link :to="`/tag/${tag.id}`">-->
          <!--        <i class="small tag icon m-tag"  :style="'color:'+getColor(tag.id)" ></i>{{tag.name}}-->
          <!--      </router-link>-->
          <!--    </div>-->
          <!--  </div>-->
          <!--</div>-->
          <!--&lt;!&ndash;分类&ndash;&gt;-->
          <!--<router-link :to="`/category/${blog.category.id}`" class="ui orange large ribbon label">-->
          <!--  <i class="small folder open icon"></i><span class="m-text-500">{{ blog.category.name }}</span>-->
          <!--</router-link>-->
          <!--&lt;!&ndash;文章Markdown描述&ndash;&gt;-->
          <!--<div class="typo m-padded-tb line-numbers match-braces rainbow-braces" v-html="blog.description"></div>-->
          <!--<div class="m-padded-tb-small">-->
          <!--  <img :src="blog.firstPicture" class="typo image" alt="">-->
          <!--</div>-->
          <!--阅读全文按钮-->
          <!--<div class="row m-padded-tb-small m-margin-top">-->
          <!--  <a href="javascript:;" @click.prevent="toBlog(blog.id)" class="color-btn">阅读全文</a>-->
          <!--</div>-->

          <div class="article-index">
            <div class=" ">
                <h2 class="m-center m-title-up">
                  <a href="javascript:;" @click.prevent="toBlog(blog.id)" class="m-black">{{ blog.title }}</a>
                </h2>
            </div>
            <div class="article-index-tags">
              <span class="article-category">
                <router-link :to="`/category/${blog.category.id}`">
                  {{ blog.category.name }}
                </router-link>
              </span>
              <span class="article-date">{{ blog.createTime | dateFormat('YYYY-MM-DD')}}</span>
              <span >{{ blog.views }} 阅读</span>
            </div>
            <div class="article-index-content">
              <p>{{blog.description}}</p>
              <div class="read-more cl-effect-14">
                <a href="javascript:;" @click.prevent="toBlog(blog.id)" class="more-link" style="color: black">继续阅读 <span class="meta-nav">→</span></a>
              </div>
            </div>
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
.article-index{
  width: 100%;
}
.article-index-tags, .article-index-tags a{
  color: #858585;
  font-size: 11px !important;
  margin: 20px 0px !important;
}

.article-index-content{
  font-size: 15px;
  line-height: 1.9;
  font-weight: 400;
  text-align: left !important;
}
.font-size-article-header{
  font-size: 1.9em;
  line-height: 1.6;
}
.article-category::after,
.article-date::after,
.article-author::after,
.article-link::after {
  content: ' ·';
  color: #000;
}


.read-more {
  font-family: 'Ubuntu', sans-serif;
  font-weight: 400;
  word-spacing: 1px;
  letter-spacing: 0.01em;
  text-align: center;
  margin-top: 20px;
}

.cl-effect-14 a {
  padding: 0 20px;
  height: 45px;
  line-height: 45px;
  position: relative;
  display: inline-block;
  margin: 15px 25px;
  letter-spacing: 1px;
  font-weight: 400;
  text-shadow: 0 0 1px rgba(255, 255, 255, 0.3);
}

.cl-effect-14 a::before,
.cl-effect-14 a::after {
  position: absolute;
  width: 45px;
  height: 1px;
  background: #C3C3C3;
  content: '';
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  transition: all 0.3s;
  pointer-events: none;
}

.cl-effect-14 a::before {
  top: 0;
  left: 0;
  -webkit-transform: rotate(90deg);
  -moz-transform: rotate(90deg);
  transform: rotate(90deg);
  -webkit-transform-origin: 0 0;
  -moz-transform-origin: 0 0;
  transform-origin: 0 0;
}

.cl-effect-14 a::after {
  right: 0;
  bottom: 0;
  -webkit-transform: rotate(90deg);
  -moz-transform: rotate(90deg);
  transform: rotate(90deg);
  -webkit-transform-origin: 100% 0;
  -moz-transform-origin: 100% 0;
  transform-origin: 100% 0;
}

.cl-effect-14 a:hover::before,
.cl-effect-14 a:hover::after,
.cl-effect-14 a:focus::before,
.cl-effect-14 a:focus::after {
  background: #000;
}

.cl-effect-14 a:hover::before,
.cl-effect-14 a:focus::before {
  left: 50%;
  -webkit-transform: rotate(0deg) translateX(-50%);
  -moz-transform: rotate(0deg) translateX(-50%);
  transform: rotate(0deg) translateX(-50%);
}

.cl-effect-14 a:hover::after,
.cl-effect-14 a:focus::after {
  right: 50%;
  -webkit-transform: rotate(0deg) translateX(50%);
  -moz-transform: rotate(0deg) translateX(50%);
  transform: rotate(0deg) translateX(50%);
}
@media (max-width: 431px) {
  .logo h1 {
    margin-top: 8px;
    font-size: 24px;
  }

  .post {
    background: #fff;
    padding: 0 10px 0;
  }

  .more-link {
    font-size: 0.9em;
    line-height: 100%;
  }
}
</style>