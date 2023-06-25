<template>
  <div>
    <!-- <el-card >
      <el-row>
        <el-col :span="20">
          <h3>书籍详情：</h3>
        </el-col>
        <el-col :span="4" align="right">
        </el-col>
      </el-row>
    </el-card> -->

    <el-card>
      <div>
        <div>
          <el-button @click="addshelf()" type="primary" round icon="el-icon-plus" size="mini">加入书架</el-button>
        </div>
        <div>
            <el-form :model="bookForm" ref="bookFromRef">
              <el-form-item :label-width="formLabelWidth" prop="imgurl">
                <img referrerpolicy="no-referrer" :src="bookForm.imgurl"  min-width="300" height="300" :alt=bookForm.imgurl :title=bookForm.imgurl />
              </el-form-item>
              <el-form-item label="书名" :label-width="formLabelWidth" prop="bookname">
                <dd>《{{bookForm.bookname}}》</dd>
              </el-form-item>
              <el-form-item label="ISBN" :label-width="formLabelWidth" prop="isbn">
                <dd>{{bookForm.isbn}}</dd>
              </el-form-item>
              <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
                <dd>{{bookForm.author}}</dd>
              </el-form-item>
              <el-form-item label="出版社" :label-width="formLabelWidth" prop="press">
                <dd>{{bookForm.press}}</dd>
              </el-form-item>
              <el-form-item label="出版时间" :label-width="formLabelWidth" prop="publicationtime">
                <dd>{{bookForm.publicationtime}}</dd>
              </el-form-item>
              <el-form-item label="评分" :label-width="formLabelWidth" prop="bookgrading">
                <dd>{{bookForm.bookgrading}}</dd>
              </el-form-item>
              <el-form-item label="简介" :label-width="formLabelWidth" prop="synopsis">
                <div id="div">{{bookForm.synopsis}}</div>
              </el-form-item>
            </el-form>
        </div>
          <div >
            <div align="left">
              <el-button @click="getCommentList()" type="primary" round icon="el-icon-edit" size="mini">全部评论</el-button>
              <el-button @click="getCommentHot()" type="primary" round icon="el-icon-edit" size="mini">热门书评</el-button>
              <el-button @click="getCommentNew()" type="primary" round icon="el-icon-edit" size="mini">最新书评</el-button>
            </div>
            <div align="right">
              <el-button @click="openEditUI()" type="primary" round icon="el-icon-edit">评论评分</el-button>
            </div>
            <el-table align="center" :data="commentList" stripe style="width: 100%">
<!--              <el-table-column prop="userid" label="用户id" width="80">-->
<!--              </el-table-column>-->
              <el-table-column prop="username" label="用户姓名 " width="120">
              </el-table-column>
              <el-table-column prop="commenttime" label="评论时间" width="120">

              </el-table-column>
              <el-table-column prop="comment" label="评论" width="180">
              </el-table-column>
              <el-table-column  label="书籍评分" width="170">
                <template slot-scope="scope">
                  <el-rate v-model="scope.row.rate" disabled show-score text-color="#ff9900"></el-rate>
                </template>
              </el-table-column>
              <el-table-column prop="likeing" width="170">
                <template slot-scope="scope">
                  <el-button @click="good(scope.row.commentid)" type="primary" icon="el-icon-star-on" size="mini">点赞{{ scope.row.likeing }}</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="searchModel.pageNo"
              :page-sizes="[15]"
              :page-size="searchModel.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              align="center">
            </el-pagination>
      </div>
      </div>
    </el-card>
    <el-dialog :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="commentForm" :rules="rules" ref="commentFromRef">
        <el-form-item label="评分：" :label-width="formLabelWidth" prop="rate">
          <!-- <el-input v-model="commentForm.comment" autocomplete="off"></el-input> -->
          <el-rate v-model="commentForm.rate" autocomplete="off"></el-rate>
        </el-form-item>
        <el-form-item label="评论：" :label-width="formLabelWidth" prop="comment">
          <el-input v-model="commentForm.comment" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取  消</el-button>
        <el-button v-if="sw==1" type="primary" @click="upDatacomment">确定修改</el-button>
        <el-button v-if="sw==1" type="danger" @click="deletecomment">删除评论</el-button>
        <el-button v-if="sw==0" type="primary" @click="addcomment">确定保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import bookApi from '@/api/bookManage'
import commentApi from '@/api/Comment'
// import {Message} from 'element-ui'
// import TotalPage from './TotalPage.vue'
export default {
  data () {
    return {
      imgurl: this.$route.params.imgurl,
      bookname: this.$route.params.bookname,
      isbn: this.$route.params.isbn,
      author: this.$route.params.author,
      press: this.$route.params.press,
      publicationtime: this.$route.params.press,
      bookgrading: this.$route.params.bookgrading,
      synopsis: this.$route.params.synopsis,
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      total: 0,
      dialogFormVisible: false,
      title: null,
      sw: 0,
      bookForm: {
        ratnum: 0,
        switch: null,
        imgurl: '暂无封面'
      },
      formLabelWidth: '130px',
      searchModel: {
        pageNo: 1,
        pageSize: 15,
        book: this.$route.params.isbn
      },
      commentList: [],
      commentForm: {
        commenttime: null,
        userid: null,
        username: null,
        isbn: this.$route.params.isbn,
        likeing: 0
      },
      rules: {
        rate: [
          { required: true, message: '评分不能为空', trigger: 'blur' }
        ],
        comment: [
          { required: true, message: '评论不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    addshelf () {
      this.commentForm.userid = this.user.userid
      this.commentForm.username = this.user.username
      bookApi.addshelf(this.commentForm.userid, this.commentForm.isbn).then(response => {
        if (response.code == 200) {
          this.$message.success('添加成功！')
        } else if (response.code == 500) {
          this.$message.success('已添加！')
        } else {
          this.$message.error('添加失败！')
        }
      })
    },

    addcomment () {
      this.commentForm.userid = this.user.userid
      this.commentForm.username = this.user.username
      this.$refs.commentFromRef.validate((valid) => {
        if (valid) {
          commentApi.postComment(this.commentForm).then(response => {
            if (response.code == 200) {
              this.$message.success('添加成功！')
              this.dialogFormVisible = false
              this.getCommentList()
            } else {
              this.$message.error('添加失败！')
            }
          })
        } else {
          return false
        }
      })
    },
    upDatacomment () {
      this.commentForm.userid = this.user.userid
      this.commentForm.username = this.user.username
      this.$refs.commentFromRef.validate((valid) => {
        if (valid) {
          commentApi.UDcomment(this.commentForm).then(response => {
            if (response.code == 200) {
              this.$message.success('修改成功！')
              this.dialogFormVisible = false
              this.getCommentList()
            } else {
              this.$message.error('修改失败！')
            }
          })
        } else {
          return false
        }
      })
    },
    deletecomment () {
      this.commentForm.userid = this.user.userid
      this.commentForm.username = this.user.username
      commentApi.decomment(this.commentForm.commentid).then(response => {
        if (response.code == 200) {
          this.$message.success('删除成功！')
          this.dialogFormVisible = false
          this.getCommentList()
          this.getcommentbyidandisbn()
        } else {
          this.$message.error('删除失败！')
        }
      })
    },
    // clearFrom(){
    //   this.$refs.commentFromRef.validate((valid) => {
    //   // this.commentForm={};
    //   // this.$refs.commentFromRef.clearValidate();
    //   this.dialogFormVisible=false;
    //   });
    // },
    handleSizeChange (pageSize) {
      this.searchModel.pageSize = pageSize
      this.getCommentList()
    },
    handleCurrentChange (pageNo) {
      this.searchModel.pageNo = pageNo
      this.getCommentList()
    },
    getBookList () {
      // this.searchModel.Book = this.$route.params.isbn
      // this.searchModel.Book = this.isbn
      // console.log(this.isbn)
      bookApi.getBookList(this.searchModel).then(response => {
        // this.bookForm = response.data.records[0]
        this.bookForm = response.data[0]
        // this.total = response.data.total
        this.total = response.total
      })
    },
    getCommentList () {
      commentApi.getCommentList(this.searchModel).then(response => {
        this.commentList = response.data.records
        this.total = response.data.total
      })
    },
    getCommentHot () {
      commentApi.getCommentHot(this.searchModel).then(response => {
        this.commentList = response.data
        this.total = response.total
      })
    },
    getCommentNew () {
      commentApi.getCommentNew(this.searchModel).then(response => {
        this.commentList = response.data
        this.total = response.total
      })
    },
    getcommentbyidandisbn () {
      // this.commentForm.userid
      this.commentForm.userid = this.user.userid
      this.commentForm.username = this.user.username
      console.log(this.user.userid)
      commentApi.getcommentbyidandisbn(this.user.userid, this.commentForm.isbn).then(response => {
        if (response.data != null) {
          this.commentForm = response.data
          this.sw = 1
        } else {
          this.sw = 0
        }
      })
    },

    openEditUI () {
      this.getcommentbyidandisbn()
      if (this.sw == 0) {
        this.title = '评论评分'
      } else {
        this.title = '修改我的评论评分'
      }
      this.dialogFormVisible = true
    },

    /*   openEditUI () {
      console.log(this.user.userid)
      // this.user.userid == undefined || this.user.userid == null
      if (typeof this.user.userid !== 'undefined') {
        this.$message.error('请先登录')
      } else {
        this.getcommentbyidandisbn()
        if (this.sw == 0) {
          this.title = '评论评分'
        } else {
          this.title = '修改我的评论评分'
        }
        this.dialogFormVisible = true
      }
    }, */
    good (id) {
      commentApi.good(id).then(response => {
        if (response.code == 200) {
          this.getCommentList()
        } else {
          this.$message.error('点赞失败！')
        }
      })
    }
  },
  created () {
    this.getBookList()
    this.getCommentList()
    // this.openEditUI()
  }
}
</script>

<style>
.el-dialog .el-input{
  width:85%;
}
#div{
  width: 400px;
  height: 300px;
}
</style>
