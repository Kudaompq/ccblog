<template>
  <div class="app-container">
    <el-form :model="blog" :rules="blogRules" ref="blogRef" label-position="top">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="文章标题" prop="title">
            <el-input v-model="blog.title" placeholder="请输入标题"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="文章封面图URL" prop="firstPicture">
            <el-input v-model="blog.firstPicture" placeholder="文章封面图"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="文章描述" prop="description">
        <el-input
            v-model="blog.description"
            type="textarea"
            :rows="4"
            placeholder="请输入文章描述"
            ></el-input>
      </el-form-item>

      <el-form-item label="文章正文" prop="content">
        <mavon-editor v-model="blog.content"/>
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="分类" prop="categoryId">
            <el-select v-model="blog.categoryId" placeholder="请选择分类" :allow-create="true" :filterable="true" style="width: 100%;">
              <el-option :label="item.name" :value="item.id" v-for="item in categoryList" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="标签" prop="tagList">
            <el-select v-model="blog.tagList" placeholder="请选择标签" :allow-create="true" :filterable="true" :multiple="true" style="width: 100%;">
              <el-option :label="item.name" :value="item.id" v-for="item in tagList" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item style="text-align: right;">
        <el-button type="success" @click="addMusic"><i class="el-icon-headset"></i>添加音乐</el-button>
        <el-button type="primary" @click="addDialogVisible = true"><i class="el-icon-edit"></i>新增标签</el-button>
        <el-button type="primary" @click="dialogVisible=true">保存</el-button>
      </el-form-item>
    </el-form>

    <!--编辑可见性状态对话框-->
    <el-dialog title="博客可见性" width="30%" :visible.sync="dialogVisible">
      <!--内容主体-->
      <el-form label-width="50px" @submit.native.prevent>
        <el-form-item>
          <el-radio-group v-model="blog.published">
            <el-radio :label="true">公开</el-radio>
            <el-radio :label="false">私密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="blog.published">
          <el-row>
            <el-col :span="6">
              <el-switch v-model="blog.appreciation" active-text="赞赏" ></el-switch>
            </el-col>
            <el-col :span="6">
              <el-switch v-model="blog.recommend" active-text="推荐" ></el-switch>
            </el-col>
            <el-col :span="6">
              <el-switch v-model="blog.commentEnabled" active-text="评论" ></el-switch>
            </el-col>
            <el-col :span="6">
              <el-switch v-model="blog.top" active-text="置顶"></el-switch>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="dialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="submit">保存</el-button>
			</span>
    </el-dialog>
    <el-dialog
        title="新增标签"
        :visible.sync="addDialogVisible"
        width="30%"
        :before-close="handleClose">
      <!--内容主体-->
      <el-form :model="tagForm" :rules="tagRules" ref="addFormRef" label-width="80px">
        <el-form-item label="标签名称" prop="name">
          <el-input v-model="tagForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="addDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="saveTag">保存</el-button>
			</span>
    </el-dialog>
  </div>
</template>

<script>
import Breadcrumb from "@/components/Breadcrumb"
import {getCategoryAndTag,saveBlog,getBlog} from "@/api/blog";
import {saveTag} from "@/api/tag";

export default {
  name: "BlogEdit",
  components: {Breadcrumb},
  data(){
    return {
      dialogVisible: false,
      blog:{
        id: null,
        title: '',
        firstPicture: '',
        content: '',
        description: '',
        published: null,
        commentEnabled: null,
        appreciation: null,
        recommend: null,
        top: null,
        categoryId: null,
        tagList: []
      },
      blogRules: {
        title: [{required: true, message: '请输入标题', trigger: 'change'}],
        firstPicture: [{required: true, message: '请输入文章封面图链接', trigger: 'change'}],
        categoryId: [{required: true, message: '请选择分类', trigger: 'change'}],
        tagList: [{required: true, message: '请选择标签', trigger: 'change'}],
      },
      categoryList: [],
      tagList: [],
      addDialogVisible: false,
      tagForm:{
        name: '',
        id: null
      },
      tagRules: {
        name: [{required: true, message: '请输入标签名称', trigger: 'blur'}]
      }
    }
  },
  created(){
    this.getData()
    if (this.$route.params.id){
      this.getBlog(this.$route.params.id)
    }
  },
  methods:{
    getData(){
      getCategoryAndTag().then(res => {
        this.categoryList = res.data.categoryList
        this.tagList = res.data.tagList
      })
    },
    submit(){
      this.$refs.blogRef.validate(valid =>{
        if (valid){
          if (!this.blog.published ){
            this.blog.appreciation = false
            this.blog.commentEnabled = false
            this.blog.top = false
            this.blog.recommend = false
          }
          saveBlog(this.blog).then(res => {
            this.msgSuccess(res.msg)
            this.$router.push("/blog/list")
          })
        }else {
          this.dialogVisible = false
          return this.msgError('请填写必要的表单项')
        }
      })
    },
    getBlog(id){
      getBlog(id).then(res => {
        this.computedCategoryAndTags(res.data)
        this.blog = res.data
      })
    },
    computedCategoryAndTags(blog){
      blog.categoryId = blog.category.id
      blog.tags.forEach(tag => {
        blog.tagList.push(tag.id)
      })

    },
    addMusic(){
      this.blog.content += "<meting-js server=\"netease\" type=\"song\" id=\"1441758494\" theme=\"#25CCF7\"></meting-js>"
    },
    handleClose(){
      this.tagForm.id = null;
      this.tagForm.name = '';
      this.addDialogVisible = false
    },
    saveTag(){
      this.$refs.addFormRef.validate(valid => {
        if (valid && this.tagForm.name !== ''){
          saveTag(this.tagForm).then(res => {
            this.msgSuccess(res.msg)
            this.handleClose()
            this.getData()
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>