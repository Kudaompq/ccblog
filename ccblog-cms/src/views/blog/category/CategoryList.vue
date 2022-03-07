<template>
  <div class="app-container">
    <el-row>
      <el-button size="small" type="primary" @click="addDialogVisible = true"><i class="el-icon-edit"></i>新增分类</el-button>
    </el-row>
    <!--分类列表-->
    <el-table :data="categoryList">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="名称" prop="name" show-overflow-tooltip></el-table-column>
      <el-table-column label="操作" width="200">
        <template v-slot="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定要删除该分类嘛?"
              @confirm="handleDelete(scope.row.id)"
              style="margin-left: 10px">
            <el-button size="mini" type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
                   :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                   layout="total, sizes, prev, pager, next, jumper" background>
    </el-pagination>

    <el-dialog
        title="新增分类"
        :visible.sync="addDialogVisible"
        width="30%"
        :before-close="handleClose">
      <!--内容主体-->
      <el-form :model="categoryForm" :rules="categoryRules" ref="addFormRef" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="categoryForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="addDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="saveCategory">保存</el-button>
			</span>
    </el-dialog>

    <el-dialog
        title="编辑分类"
        :visible.sync="editDialogVisible"
        width="30%"
        :before-close="handleClose">
      <!--内容主体-->
      <el-form :model="categoryForm" :rules="categoryRules" ref="editFormRef" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="categoryForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editCategory">保存</el-button>
			</span>
    </el-dialog>
  </div>
</template>

<script>
import Breadcrumb from "@/components/Breadcrumb";
import {getCategoryList,saveCategory,editCategory,deleteCategory} from "@/api/category"
export default {
  name: "CategoryList",
  components: {Breadcrumb},
  data(){
    return {
      queryInfo:{
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      categoryList: [],
      addDialogVisible: false,
      editDialogVisible: false,
      categoryForm:{
        id: null,
        name: ''
      },
      categoryRules:{
        name: [{required: true, message: '请输入分类名称', trigger: 'blur'}]
      }

    }
  },
  created() {
    this.getData()
  },
  methods:{
    getData(){
      getCategoryList(this.queryInfo).then(res => {
        this.categoryList = res.data.data
        this.total = res.data.total
      })
    },
    handleSizeChange(newSize){
      this.queryInfo.pageSize = newSize
      this.getData()
    },
    handleCurrentChange(newPage){
      this.queryInfo.pageNum = newPage
      this.getData()
    },
    handleClose(){
      this.categoryForm.id = null
      this.categoryForm.name = ''
      this.addDialogVisible = false
      this.editDialogVisible = false
    },
    saveCategory(){
      this.$refs.addFormRef.validate(valid => {
        if (valid){
          saveCategory(this.categoryForm).then(res => {
            this.msgSuccess(res.msg)
            this.getData()
            this.addDialogVisible = false
          })
        }
      })
    },
    showEditDialog(row){
      this.categoryForm.id = row.id
      this.categoryForm.name = row.name
      this.editDialogVisible = true
    },
    editCategory(){
      this.$refs.editFormRef.validate(valid => {
        if (valid && this.categoryForm.name !== ''){
          editCategory(this.categoryForm).then(res =>{
            this.msgSuccess(res.msg)
            this.getData()
            this.editDialogVisible = false
          })
        }
      })
    },
    handleDelete(id){
      deleteCategory(id).then(res=>{
        this.msgSuccess(res.msg)
        this.getData()
      })
    }
  }
}
</script>

<style scoped>

</style>