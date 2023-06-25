<template>
  <el-card>
  <div class="search-target">
<h2 style="border:black">推荐书籍</h2>
        <div align="right" >
          <el-button @click="more()" type="primary" round icon="el-icon-plus" size="mini">更多</el-button>
        </div>
    <el-table :data="bookList" @cell-click="infor" :show-header="false">
      <el-table-column prop="a0" label="封面" width="175">
          <template slot-scope="scope">
            <img referrerpolicy="no-referrer" :src="scope.row.a0.imgurl"  min-width="175" height="240" :alt=scope.row.a0.imgurl :title=scope.row.a0.imgurl />
            <span>{{ scope.row.a0.bookname }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="a1" label="封面" width="175">
        <template slot-scope="scope">
            <img referrerpolicy="no-referrer" :src="scope.row.a1.imgurl"  min-width="175" height="240" :alt=scope.row.a1.imgurl :title=scope.row.a1.imgurl />
            <span>{{ scope.row.a1.bookname }}</span>
          </template>
      </el-table-column>
      <el-table-column prop="a2" label="封面" width="175">
        <template slot-scope="scope">
            <img referrerpolicy="no-referrer" :src="scope.row.a2.imgurl"  min-width="175" height="240" :alt=scope.row.a2.imgurl :title=scope.row.a2.imgurl />
            <span>{{ scope.row.a2.bookname }}</span>
          </template>
      </el-table-column>
      <el-table-column prop="a3" label="封面" width="175">
        <template slot-scope="scope">
            <img referrerpolicy="no-referrer" :src="scope.row.a3.imgurl"  min-width="175" height="240" :alt=scope.row.a3.imgurl :title=scope.row.a3.imgurl />
            <span>{{ scope.row.a3.bookname }}</span>
          </template>
      </el-table-column>
      <el-table-column prop="a4" label="封面" width="175">
      <template slot-scope="scope">
            <img referrerpolicy="no-referrer" :src="scope.row.a4.imgurl"  min-width="175" height="240" :alt=scope.row.a4.imgurl :title=scope.row.a4.imgurl />
            <span>{{ scope.row.a4.bookname }}</span>
          </template>
      </el-table-column>
    </el-table>
  </div>
    <div>
      <div>
        <div align="left">
          <h2 style="border:black">热门书评</h2>
        </div>
        <el-table align="center" :data="commentHotList" stripe style="width: 100%">
          <el-table-column prop="imgurl" label="书籍" width="100">
            <template slot-scope="scope">
              <img referrerpolicy="no-referrer" :src="scope.row.imgurl"  max-width="100" height="110" :alt=scope.row.imgurl :title=scope.row.imgurl />
              <span>{{ scope.row.bookname }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="username" label="用户昵称" width="80">
          </el-table-column>
          <el-table-column prop="comment" label="评论" width="180">
          </el-table-column>
          <el-table-column  label="书籍评分" width="170">
            <template slot-scope="scope">
              <el-rate v-model="scope.row.rate" disabled show-score text-color="#ff9900"></el-rate>
            </template>
          </el-table-column>
          <!-- <el-table-column prop="likeing" width="170">
            <template slot-scope="scope">
              <el-button @click="good(scope.row.commentid)" type="primary" icon="el-icon-star-on" size="mini">点赞{{ scope.row.likeing }}</el-button>
            </template>
          </el-table-column> -->
        </el-table>
        <el-pagination
          @size-change="handleSizeChange1"
          @current-change="handleCurrentChange1"
          :current-page="searchModel.pageNo"
          :page-sizes="[3,5,15]"
          :page-size="searchModel.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          align="center">
        </el-pagination>
      </div>
      <div>
        <div align="left">
          <h2 style="border:black">最新书评</h2>
        </div>
        <el-table align="center" :data="CommentNewList" stripe style="width: 100%">
          <el-table-column prop="imgurl" label="书籍" width="100">
            <template slot-scope="scope">
              <img referrerpolicy="no-referrer" :src="scope.row.imgurl"  max-width="100" height="110" :alt=scope.row.imgurl :title=scope.row.imgurl />
              <span>{{ scope.row.bookname }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="username" label="用户昵称" width="80">
          </el-table-column>
          <el-table-column prop="comment" label="评论" width="180">
          </el-table-column>
          <el-table-column  label="书籍评分" width="170">
            <template slot-scope="scope">
              <el-rate v-model="scope.row.rate" disabled show-score text-color="#ff9900"></el-rate>
            </template>
          </el-table-column>
          <!-- <el-table-column prop="likeing" width="170">
            <template slot-scope="scope">
              <el-button @click="good(scope.row.commentid)" type="primary" icon="el-icon-star-on" size="mini">点赞{{ scope.row.likeing }}</el-button>
            </template>
          </el-table-column> -->
        </el-table>
        <el-pagination
          @size-change="handleSizeChange1"
          @current-change="handleCurrentChange1"
          :current-page="searchModel.pageNo"
          :page-sizes="[3,5,15]"
          :page-size="searchModel.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          align="center">
        </el-pagination>
      </div>
    </div>
  </el-card>
</template>
<script>
import bookApi from '@/api/bookManage'
import commentApi from '@/api/Comment'
export default {
  data () {
    return {
      searchbook: {
        pageNo: 1,
        pageSize: 10,
        book: ''
      },
      searchModel: {
        pageNo: 1,
        pageSize: 3
      },
      total: 0,
      bookList: [],
      commentHotList: [],
      CommentNewList: []
    }
  // },
  //   return {
  //     searchModel: {
  //       pageNo: 1,
  //       pageSize: 10,
  //       book: ''
  //     },
  //     bookList: [],
  //     showList: []
  //   }
  },
  methods: {
    getCommentHot () {
      commentApi.CommentHot(this.searchModel).then(response => {
        this.commentHotList = response.data
        this.total = response.total
      })
    },
    getCommentNew () {
      commentApi.CommentNew(this.searchModel).then(response => {
        this.CommentNewList = response.data
        this.total = response.total
      })
    },
    handleSizeChange1 (pageSize) {
      this.searchModel.pageSize = pageSize
      this.getCommentHot()
    },
    handleCurrentChange1 (pageNo) {
      this.searchModel.pageNo = pageNo
      this.getCommentHot()
    },
    handleSizeChange2 (pageSize) {
      this.searchModel.pageSize = pageSize
      this.getCommentNew()
    },
    handleCurrentChange2 (pageNo) {
      this.searchModel.pageNo = pageNo
      this.getCommentNew()
    },
    more () {
      this.$router.push('/book-index')
    },
    getBookList () {
      bookApi.getBookList(this.searchbook).then(response => {
        // this.bookList.push({'a0': response.data.records[0]})
        this.bookList.push({'a0': response.data[0]})
        // this.bookList[0]['a1'] = response.data.records[1]
        this.bookList[0]['a1'] = response.data[1]
        // this.bookList[0]['a2'] = response.data.records[2]
        this.bookList[0]['a2'] = response.data[2]
        this.bookList[0]['a3'] = response.data[3]
        this.bookList[0]['a4'] = response.data[4]
        this.bookList.push({'a0': response.data[5]})
        this.bookList[1]['a1'] = response.data[6]
        this.bookList[1]['a2'] = response.data[7]
        this.bookList[1]['a3'] = response.data[8]
        this.bookList[1]['a4'] = response.data[9]
      })
    },
    cellClick (row) {
      this.$router.push({
        name: 'book',
        params: {
          imgurl: row.imgurl,
          bookname: row.bookname,
          isbn: row.isbn,
          author: row.author,
          press: row.press,
          publicationtime: row.press,
          bookgrading: row.bookgrading,
          synopsis: row.synopsis
        }
      })
    },
    infor (row, column, event, cell) {
      console.log(row)
      console.log(column)
      console.log(event)
      console.log(cell)
      switch (column.property) {
        case 'a0': this.cellClick(row.a0)
          console.log(row.a0)
          break
        case 'a1': this.cellClick(row.a1)
          console.log(row.a1)
          break
        case 'a2': this.cellClick(row.a2)
          console.log(row.a2)
          break
        case 'a3': this.cellClick(row.a3)
          console.log(row.a3)
          break
        case 'a4': this.cellClick(row.a4)
          console.log(row.a4)
          break
      }
    }
  },
  created () {
    this.getBookList()
    this.getCommentHot()
    this.getCommentNew()
  }
}
</script>
<style scoped>

</style>
