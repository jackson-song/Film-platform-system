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
  </el-card>
</template>
<script>
import bookApi from '@/api/bookManage'
export default {
  data () {
    return {
      searchModel: {
        pageNo: 1,
        pageSize: 10,
        book: ''
      },
      bookList: [],
      showList: []
    }
  },
  methods: {
    more () {
      this.$router.push('/book-index')
    },
    getBookList () {
      bookApi.getBookList(this.searchModel).then(response => {
        this.bookList.push({'a0': response.data.records[0]})
        this.bookList[0]['a1'] = response.data.records[1]
        this.bookList[0]['a1'] = response.data.records[1]
        this.bookList[0]['a2'] = response.data.records[2]
        this.bookList[0]['a3'] = response.data.records[3]
        this.bookList[0]['a4'] = response.data.records[4]
        this.bookList.push({'a0': response.data.records[5]})
        this.bookList[1]['a1'] = response.data.records[6]
        this.bookList[1]['a2'] = response.data.records[7]
        this.bookList[1]['a3'] = response.data.records[8]
        this.bookList[1]['a4'] = response.data.records[9]
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
  }
}
</script>
<style scoped>

</style>
