<template>
  <div>
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input v-model="searchModel.user" placeholder="请输入用户邮箱或昵称" clearable></el-input>
          <el-button @click="getUser" type="primary" round icon="el-icon-search">搜索</el-button>
        </el-col>
        <el-col :span="4" align="right">
          <!-- <el-button type="primary" circle icon="el-icon-plus"></el-button> -->
        </el-col>
      </el-row>
    </el-card>

    <el-card>
      <el-table :data="userList" stripe style="width: 100%">
      <el-table-column prop="userid" label="用户id" width="80">
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="180">
      </el-table-column>
      <el-table-column prop="username" label="昵称" width="100">
      </el-table-column>
      <el-table-column prop="pwd" label="密码" width="200">
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="80">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80">
      </el-table-column>
      <el-table-column prop="introduction" label="个人简介" width="180">
      </el-table-column>
      <el-table-column label="账号权限" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.upower==1">管理员</span>
          <span v-else-if="scope.row.upower==0">普通用户</span>
          <span v-else>权限错误</span>
        </template>
      </el-table-column>
      <el-table-column label="账号状态" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.state==1">正常</span>
          <span v-else-if="scope.row.state==0">已冻结</span>
          <span v-else>状态错误</span>
        </template>
      </el-table-column>
        <el-table-column label="操作">
        <template slot-scope="scope">
          <span v-if="scope.row.state==1">
            <el-button @click="freezeUser(scope.row.userid)" type="danger" round icon="el-icon-edit" size="mini">冻结</el-button>
          </span>
          <span v-else>
            <el-button @click="thaw(scope.row.userid)" type="primary" round icon="el-icon-plus" size="mini">解冻</el-button>
          </span>
        </template>
      </el-table-column>
      </el-table>
    </el-card>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo"
      :page-sizes="[5, 10, 20, 40, 50]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import userApi from '@/api/userManage';
export default {
    data(){
      return {
        total:0,
        searchModel:{
          pageNo:1,
          pageSize:10
        },
        userList:[]
      }
    },
    methods:{
      handleSizeChange(pageSize){
        this.searchModel.pageSize=pageSize;
        this.getUserList();
      },
      handleCurrentChange(pageNo){
        this.searchModel.pageNo=pageNo;
        this.getUserList();
      },
      getUserList(){
        userApi.getUserList(this.searchModel).then(response=>{
          this.userList=response.data.records;
          this.total=response.data.total;
        });
      },
      getUser(){
        userApi.getUser(this.searchModel).then(response=>{
          this.userList=response.data;
          this.total=response.data.total;
        });
      },
      freezeUser(id){
        userApi.freezeUser(id).then(response=>{
        if(response.code==200){
          this.getUserList();
        }else{
          this.getUserList();
          this.$message.error("修改失败");
        }
      });
      },
      thaw(id){
        userApi.thaw(id).then(response=>{
        if(response.code==200){
          this.getUserList();
        }else{
          this.getUserList();
          this.$message.error("修改失败");
        }
        });
      }
    },
    created(){
      this.getUserList();
    }
}
</script>

<style>
  #search .el-input{
    width: 200px;
    margin-right: 10px;
  }
</style>