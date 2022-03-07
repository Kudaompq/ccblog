<template>
  <div class="ui segments m-box">
    <div class="ui segment"><i class="tags icon"></i>标签云</div>
    <div class="ui teal segment m-padded-none">
      <tag-cloud
          :data="tagList"
          @clickTag="clickTagItem"
          :config="config"
          style="margin-left: -10px"
      >
      </tag-cloud>
    </div>
  </div>
</template>

<script>
export default {
  name: "Tags",
  data(){
    return {
      config: {
        radius: 100, // 滚动半径，单位px
        maxFont: 24, // 最大字体大小
        color: null, // 字体颜色。为空时随机
        rotateAngleXbase: 1000, // 默认旋转速度基数，数越小速度越快
        rotateAngleYbase: 1000,
        hover: false // 是否开启悬浮联动
      }

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
</style>