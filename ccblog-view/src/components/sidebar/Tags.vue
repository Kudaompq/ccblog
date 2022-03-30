<template>
  <div class="ui segments m-box">
    <div class="ui segment"><i class="tags icon"></i>标签云</div>
    <div class="ui segment m-padded-none card-tag-cloud text-center">
      <a href="" @click.prevent="clickTagItem(tag)" v-for="tag in tagList" :key="tag.id">{{tag.name}}</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "Tags",
  data(){
    return {
    }
  },
  props:{
    tagList:{
      type: Array,
      require:true
    }
  },
  methods:{
    clickTagItem (tag) {
      this.$router.push({
        name: 'tag',
        params: {
          id: tag.id,
          name: tag.name
        }
      })
    }
  }
}
</script>

<style scoped>
/*card-tag-cloud*/
.card-tag-cloud{
  list-style-type: none;
}
.card-tag-cloud a {
  color: #333;
  font-size: 1em !important;
  margin: 1px !important;
  display: inline-block;
  padding: 0 0.1rem;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -o-transition: all 0.3s;
  -ms-transition: all 0.3s;
  transition: all 0.3s;
}
.card-tag-cloud a:hover {
  color: #49b1f5 !important;
  -webkit-transform: scale(1.1);
  -moz-transform: scale(1.1);
  -o-transform: scale(1.1);
  -ms-transform: scale(1.1);
  transform: scale(1.1);
}
@media screen and (max-width: 768px) {
  .card-tag-cloud a {
    zoom: 0.85;
  }
}
</style>