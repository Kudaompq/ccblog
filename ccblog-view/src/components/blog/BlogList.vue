<template>
  <div>
    <BlogItem v-for="blog in blogList" :key="blog.id" :blog = "blog" v-viewer></BlogItem>
  </div>
</template>

<script>
import BlogItem from "@/components/blog/BlogItem";
export default {
  name: "BlogList",
  components: {BlogItem},
  props:{
    blogList:{
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>

</style>