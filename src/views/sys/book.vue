<template>
  <div>
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input v-model="searchModel.book" placeholder="请输入书籍信息" clearable></el-input>
          <el-button @click="getBookList" type="primary" round icon="el-icon-search">搜索</el-button>
        </el-col>
        <el-col :span="4" align="right">
          <el-button @click="openEditUI(null)" type="primary" circle icon="el-icon-plus"></el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card>
      <el-table :data="bookList" stripe style="width: 100%">
        <el-table-column label="封面" width="180">
          <template slot-scope="scope">            
            <img referrerpolicy="no-referrer" :src="scope.row.imgurl"  min-width="170" height="170" :alt=scope.row.imgurl :title=scope.row.imgurl />
        </template>
      </el-table-column>
      <el-table-column prop="isbn" label="ISBN" width="180">
      </el-table-column>
      <el-table-column prop="bookname" label="书名" width="180">
      </el-table-column>
      <el-table-column prop="author" label="作者" width="100">
      </el-table-column>
      <el-table-column prop="press" label="出版社" width="80">
      </el-table-column>
      <el-table-column prop="publicationtime" label="出版时间" width="80">
      </el-table-column>
      <el-table-column prop="bookgrading" label="评分" width="80">
      </el-table-column>
      <el-table-column prop="synopsis" label="简介" width="180">
      </el-table-column>
        <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button @click="openEditUI(scope.row.isbn)" type="primary" round icon="el-icon-edit" size="mini">修改</el-button>
            <el-button @click="deleteBook(scope.row)" type="danger" round icon="el-icon-delete" size="mini">删除</el-button>
        </template>
      </el-table-column>
      </el-table>
    </el-card>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo"
      :page-sizes="[10, 20, 40, 80, 160]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>


    <el-dialog @close="clearFrom" :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="bookForm" :rules="rules" ref="bookFromRef">
        <el-form-item label="书名" :label-width="formLabelWidth" prop="bookname">
          <el-input v-model="bookForm.bookname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="ISBN" v-if="bookForm.switch==0" :label-width="formLabelWidth" prop="isbn">
          <el-input v-model="bookForm.isbn" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="封面链接" :label-width="formLabelWidth" prop="imgurl">
          <el-input v-model="bookForm.imgurl" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
          <el-input v-model="bookForm.author" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出版社" :label-width="formLabelWidth" prop="press">
          <el-input v-model="bookForm.press" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出版时间" :label-width="formLabelWidth" prop="publicationtime">
          <el-input v-model="bookForm.publicationtime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="评分" :label-width="formLabelWidth" prop="bookgrading">
          <el-input v-model="bookForm.bookgrading" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介" :label-width="formLabelWidth" prop="synopsis">
          <el-input type="textarea" v-model="bookForm.synopsis" :rows="8" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取  消</el-button>
        <el-button v-if="bookForm.switch==1" type="primary" @click="upDataBook">确定修改</el-button>
        <el-button v-if="bookForm.switch==0" type="primary" @click="addBook">确定保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import bookApi from '@/api/bookManage';
export default {
    data(){
      return {
        total:0,
        dialogFormVisible:false,
        title:null,
        
        bookForm:{
          ratnum:0,
          switch:null,
          imgurl:"暂无封面"
        },
        formLabelWidth:'130px',
        searchModel:{
          pageNo:1,
          pageSize:10,
          book:""
        },
        bookList:[],
        rules:{
          bookname: [
            { required: true, message: '请输入书名', trigger: 'blur' }
          ],
          isbn: [
            { required: true, message: '请输入ISBN', trigger: 'blur' }
          ],
          author: [
            { required: true, message: '请输入作者', trigger: 'blur' }
          ]
        }
      }
    },
    methods:{
      addBook(){
        this.$refs.bookFromRef.validate((valid) => {
          if (valid) {
            bookApi.addBook(this.bookForm).then(response =>{
              if(response.code==200){
                this.$message.success("添加成功！")
                this.dialogFormVisible=false;
                this.getBookList(this.bookForm.isbn);
              }else{
                this.$message.error("添加失败！");
              }
            });
          } else {
            return false;
          }
        });
      },
      upDataBook(){
        this.$refs.bookFromRef.validate((valid) => {
          if (valid) {
            bookApi.upDataBook(this.bookForm).then(response =>{
              if(response.code==200){
                this.$message.success("修改成功！")
                this.dialogFormVisible=false;
                this.getBookList(this.bookForm.isbn);
              }else{
                this.$message.error("修改失败！");
              }
            });
          } else {
            return false;
          }
        });
      },
      deleteBook(book){
        bookApi.deleteBook(book).then(response=>{
          if(response.code==200){
              this.$message.success("删除成功！")
              this.getBookList();
          }else{
            this.$message.error("删除失败！");
          }
        });
      },
      clearFrom(){
        this.bookForm={};
        this.$refs.bookFromRef.clearValidate();
      },
      handleSizeChange(pageSize){
        this.searchModel.pageSize=pageSize;
        this.getBookList();
      },
      handleCurrentChange(pageNo){
        this.searchModel.pageNo=pageNo;
        this.getBookList();
      },
      getBookList(){
        bookApi.getBookList(this.searchModel).then(response=>{
          this.bookList=response.data.records;
          this.total=response.data.total;
        });
      },
      openEditUI(isbn){
        if(isbn==null){
          this.title='新增书籍';
          this.bookForm.switch=0;
        }else{
          this.title='修改书籍信息';
          bookApi.getBookbyisbn(isbn).then(response =>{
            this.bookForm=response.data.records[0];
            this.bookForm.switch=1;
          });
        }
        this.dialogFormVisible=true;
      }
    },
    created(){
      this.getBookList();
    }
}
</script>

<style>
  #search .el-input{
    width: 200px;
    margin-right: 10px;
  }
  .el-dialog .el-input{
    width:85%;
  }
</style>