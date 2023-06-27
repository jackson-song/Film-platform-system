<template>
  <el-card>
  <div class="search-target">
<h2 style="border:black">推荐书籍</h2>
    <el-table :data="tableData"  @row-click="handleRowClick ">
      <el-table-column prop="imgurl"  label="封面" width="150">
        <template   slot-scope="scope">
          <img referrerpolicy="no-referrer"  :src="scope.row.imgurl"  min-width="170" height="170" :alt=scope.row.imgurl :title=scope.row.imgurl />
        </template>
      </el-table-column>
      <el-table-column prop="bookname" label="书名" width="80">
      </el-table-column>
      <el-table-column prop="isbn" label="ISBN" width="80">
      </el-table-column>
      <el-table-column prop="author" label="作者" width="80">
      </el-table-column>
      <el-table-column prop="ratnum" label="热度" width="80">
      </el-table-column>
      <el-table-column prop="press" label="出版社" width="80">
      </el-table-column>
      <el-table-column prop="publicationtime" label="出版时间" width="80">
      </el-table-column>
      <el-table-column prop="bookgrading" label="评分" width="80">
      </el-table-column>
    </el-table>
  </div>
  </el-card>
</template>
<script>
// import axios from 'axios'

import axios from 'axios'
import {log} from 'nightwatch/lib/util/logger'
// import {data} from "autoprefixer";

export default {
  data () {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: []
    }
  },
  created () {
    this.getInfo()
  },
  methods: {
    handleRowClick (row) {
      this.$router.push({
        name: 'book',
        params: {
          imgurl: row.imgurl,
          bookname: row.bookname,
          isbn: row.isbn,
          ratnum: row.ratnum,
          author: row.author,
          press: row.press,
          publicationtime: row.press,
          bookgrading: row.bookgrading,
          synopsis: row.synopsis
        }
      })
    },
    getInfo () {
      /*      fetch('http://localhost:3000/userbased')
        .then(res => res.json()).then(res => {
          console.log(res)
          // this.u
          this.tableData = res
          // this.total = res.data.total
        }) */
      axios.post('http://localhost:3000/userbasedall?userid=' + this.user.userid)
        .then(response => {
          console.log(this.user.userid)
          console.log(response.data.data)
          this.tableData = response.data.data
          return response.data.data
        })
    }
  }
}

</script>
<style scoped>

</style>
