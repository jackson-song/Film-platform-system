<template>
  <div class="left-content">
    <div class="content-header">

    <div>
    <el-input style="width: 200px" v-model="input"  suffix-icon="el-icon-search" placeholder="书名、作者、ISBN"></el-input>
    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
    </div>
    <el-table :data="tableData">
<!--      <el-table-column prop="id" label="id" width="80">-->
<!--      </el-table-column>-->

      <el-table-column prop="imgurl" label="封面" width="80">
     <template slot-scope="scope">
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
      <el-table-column prop="synopsis" label="简介" width="400">
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
    </div>
  </div>
</template>

<script>

export default {
  name: 'BookTagContent',
  data () {
    return {
      tableData: [],
      total: 0,
      page: 1,
      size: 2,
      input: ''
    }
  },

  created () {
    this.load()
  },
  // created () {
  //   fetch('http://localhost:3000/books/pageByBookalltest?page=' + this.page + '&size=' + this.size + '&text')
  //     .then(res => res.json()).then(res => {
  //       console.log(res)
  //       this.tableData = res.data.records
  //       this.total = res.data.total
  //     })
  // },
  computed: {
    currentBookTag () {
      return this.$store.state.book.currentBookTag
    }
  },
  methods: {
    load () {
      fetch('http://localhost:3000/books/pageByBookalltest?page=' + this.page + '&size=' + this.size + '&text=' + this.input)
        .then(res => res.json()).then(res => {
          console.log(res)
          this.tableData = res.data.records
          this.total = res.data.total
        })
    },
    search () {
      // eslint-disable-next-line no-undef
      fetch('http://localhost:3000/books/pagebytype?page=' + this.page + '&size=' + this.size + '&type=' + bookTags[0].subTags[0])
        .then(res => res.json()).then(res => {
          console.log(res)
          this.tableData = res.data.records
          this.total = res.data.total
        })
    },
    handleSizeChange (size) {
      console.log(size)
      this.size = size
      this.load()
    },
    handleCurrentChange (page) {
      console.log(page)
      this.page = page
      this.load()
    }
  }
}
</script>

<style scoped>
.common-link{
  margin-left: 10px;
}
.book-tag-content-slide{
  float: right;
  margin-top: 5px;
}
.book-tag-content{
  width: 100%;
  height: 489px;
  position: relative;
  overflow: hidden;
  font-size: 13px;
  margin-top: 16px;
}
.book-tag-content-list{
  width: 100%;
  height: 100%;
  position: absolute;
}
.book-tag-content-list li{
  display: inline-block;
  width: 20%;
  height: 50%;
  text-align: center;
}
.book-tag-content-image{
  width: 85%;
  height: 70%;
}
.book-tag-content-author{
  margin: 0 0 6px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.book-tag-content-prompt{
  width: 330px;
  max-height:220px;
  position: absolute;
  background: #F9F9F7;
  border: 1px solid #acacac;
  border-radius: 5px;
  box-sizing: border-box;
  padding: 10px;
  box-shadow: 0 1px 1px #fdfdfd inset, 0 1px 1px #dcdbd4;
}
.book-tag-content-prompt span{
  position: absolute;
  width: 0;
  height: 0;
  top: 45%;
  border-top: 8px solid transparent;
  border-bottom: 8px solid transparent;
}
.outside-triangle{
  border-right:8px solid #acacac;
  left:-8px;
}
.inside-triangle{
  border-right:8px solid #F9F9F7;
  left:-7px;
}
.prompt-title{
  font-size: 14px;
  margin-bottom: 6px;
  color: #666;
}
.prompt-introduce{
  font-size: 12px;
  margin-bottom: 6px;
}
.prompt-summary{
  max-height: 130px;
  font-size: 12px;
  line-height: 1.6;
}
</style>
