<template>
  <div class="book-type-content">
<el-card>
    <div class="content-header">
      <h2>热门图书榜</h2>
      <el-table :data="tableData" @row-click="handleRowClick">
        <el-table-column prop="imgurl" label="封面" width="200">
          <template   slot-scope="scope">
            <img referrerpolicy="no-referrer" :src="scope.row.imgurl"  min-width="170" height="170" :alt=scope.row.imgurl :title=scope.row.imgurl />
          </template>
        </el-table-column>
        <el-table-column prop="bookname" label="书名" width="80">
        </el-table-column>
        <el-table-column prop="isbn" label="ISBN" width="80">
        </el-table-column>
        <el-table-column prop="author" label="作者" width="80">
        </el-table-column>
        <el-table-column prop="press" label="出版社" width="80">
        </el-table-column>
        <el-table-column prop="publicationtime" label="出版时间" width="80">
        </el-table-column>
        <el-table-column prop="bookgrading" label="评分" width="80">
        </el-table-column>
        <el-table-column prop="synopsis" label="简介" width="200">
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
      <h2>最新图书榜</h2>

      <el-table :data="tableData2" @row-click="handleRowClick">
        <el-table-column prop="imgurl" label="封面" width="200">
          <template   slot-scope="scope">
            <img referrerpolicy="no-referrer" :src="scope.row.imgurl"  min-width="170" height="170" :alt=scope.row.imgurl :title=scope.row.imgurl />
          </template>
        </el-table-column>
          <el-table-column prop="bookname" label="书名" width="80">
          </el-table-column>
          <el-table-column prop="isbn" label="ISBN" width="80">
          </el-table-column>
          <el-table-column prop="author" label="作者" width="80">
          </el-table-column>
          <el-table-column prop="press" label="出版社" width="80">
          </el-table-column>
        <el-table-column prop="publicationtime" label="出版时间" width="80">
        </el-table-column>
        <el-table-column prop="bookgrading" label="评分" width="80">
        </el-table-column>
        <el-table-column prop="synopsis" label="简介" width="200">
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0">
        <el-pagination
          @size-change="handleSizeChange2"
          @current-change="handleCurrentChange2"
          :current-page="page"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
</el-card>
  </div>

</template>
<script>
export default {
  name: 'HotBook',
  data () {
    return {
      tableData: [],
      tableData2: [],
      total: 0,
      page: 1,
      size: 2
    }
  },
  created () {
    this.hotSearch()
    this.newSearch()
  },
  methods: {
    handleRowClick (row) {
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
    hotSearch () {
      fetch('http://localhost:3000/books/hotbook?page=' + this.page + '&size=' + this.size)
        .then(res => res.json()).then(res => {
        // console.log(res)
          this.tableData = res.data
          this.total = res.total
        })
    },
    newSearch () {
      fetch('http://localhost:3000/books/newest?page=' + this.page + '&size=' + this.size)
        .then(res => res.json()).then(res => {
        // console.log(res)
          this.tableData2 = res.data
          this.total = res.total
        })
    },
    handleSizeChange (size) {
      console.log(size)
      this.size = size
      this.hotSearch()
    },
    handleSizeChange2 (size) {
      console.log(size)
      this.size = size
      this.newSearch()
    },
    handleCurrentChange (page) {
      console.log(page)
      this.page = page
      this.hotSearch()
    },
    handleCurrentChange2 (page) {
      console.log(page)
      this.page = page
      this.newSearch()
    }
  }
}

</script>
<style scoped>

</style>
