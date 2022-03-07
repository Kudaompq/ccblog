<template>
  <div class="ui segments m-box">
    <div class="ui segment"><i class="folder open outline icon"></i>分类</div>
    <div class="ui teal segment">
      <div class="ui middle  selection list">
        <div class="item" v-for="cate in categoryList" :key="cate.id">
          <div class="content">
            <div @click="toPath(cate)">
              <div class="left floated">{{cate.name}}</div>
              <div class=" right floated">{{cate.count}}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Categories",
  data(){
    return{

    }
  },
  props:{
    categoryList:{
      type: Array,
      require: true
    }
  },
  methods:{
    toPath(cate){
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

</style>