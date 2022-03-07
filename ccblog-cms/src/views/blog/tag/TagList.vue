<template>
  <div class="app-container">
    <el-row>
      <el-button size="small" type="primary" @click="addDialogVisible = true"><i class="el-icon-edit"></i>新增标签</el-button>
    </el-row>
    <!--标签列表-->
    <el-table :data="tagList">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="名称" prop="name" show-overflow-tooltip></el-table-column>
      <el-table-column label="操作" width="200">
        <template v-slot="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEdit(scope.row)">编辑</el-button>
          <el-popconfirm
            title="确定要删除该标签嘛?"
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

    <el-dialog
        title="编辑标签"
        :visible.sync="editDialogVisible"
        width="30%"
        :before-close="handleClose">
      <!--内容主体-->
      <el-form :model="tagForm" :rules="tagRules" ref="editFormRef" label-width="80px">
        <el-form-item label="标签名称" prop="name">
          <el-input v-model="tagForm.name"></el-input>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editTag">保存</el-button>
			</span>
    </el-dialog>
  </div>
</template>

<script>
import Breadcrumb from "@/components/Breadcrumb";
import {getTagListByQueryInfo,saveTag,editTag,deleteTag} from "@/api/tag";

export default {
  name: "TagList",
  components: {Breadcrumb},
  data(){
    return{
      queryInfo:{
        pageNum: 1,
        pageSize: 10
      },
      tagList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      tagForm:{
        name: '',
        id: null
      },
      tagRules: {
        name: [{required: true, message: '请输入标签名称', trigger: 'blur'}]
      }
    }
  },
  created() {
    this.getData()
  },
  methods:{
    getData(){
      getTagListByQueryInfo(this.queryInfo).then(res => {
        this.tagList = res.data.data
        this.total = res.data.total
      })
    },
    //监听 pageSize 改变事件
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize
      this.getData()
    },
    //监听页码改变的事件
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage
      this.getData()
    },
    showEdit(row){
      this.tagForm.id = row.id;
      this.tagForm.name = row.name;
      this.editDialogVisible = true;
    },
    handleClose(){
      this.tagForm.id = null;
      this.tagForm.name = '';
      this.editDialogVisible = false
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
    },
    editTag(){
      this.$refs.editFormRef.validate(valid => {
        if (valid){
          editTag(this.tagForm).then(res => {
            this.msgSuccess(res.msg)
            this.handleClose()
            this.getData()
          })
        }
      })
    },
    handleDelete(id){
      deleteTag(id).then(res => {
        this.msgSuccess(res.msg)
        this.getData()
      })
    }

  }
}
</script>

<style scoped>

</style>