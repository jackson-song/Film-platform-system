<template>

  <div>

  <div class="left-content">
    <div class="content-header">

      <!--      <h2>{{currentBookTag}}</h2>-->
      <h2>{{currentBookTag}}</h2>
      <!--      <h2>{{processedBooks}}</h2>-->
      <!--      <router-link to="book-t ag-more-info" class="common-link">更多»</router-link>-->

      <!--      <base-slide-->
      <!--        class="book-tag-content-slide"-->
      <!--        :pageCount="pageCount"-->
      <!--        :current-page="currentPage"-->
      <!--        background-color="#9b9a8e"-->
      <!--        @change-page="changePage"-->
      <!--        @change-direction="changeDirection"-->
      <!--      />-->
    </div>
    <div class="search-target">
<!--      <el-input style="width: 200px" v-model="input1"  suffix-icon="el-icon-search" placeholder="书名、作者、ISBN"></el-input>-->
<!--      <el-button class="ml-5" type="primary" @click="SearchByDetail">搜索</el-button>-->
      <el-input style="width: 200px" v-model="input"  suffix-icon="el-icon-search" placeholder="书名、作者、ISBN"></el-input>
      <el-button class="ml-5" type="primary" @click="search">搜索</el-button>

      <el-table :data="tableData" @row-click="handleRowClick">
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
        <el-table-column prop="press" label="出版社" width="80">
        </el-table-column>
        <el-table-column prop="publicationtime" label="出版时间" width="80">
        </el-table-column>
        <el-table-column prop="bookgrading" label="评分" width="80">
        </el-table-column>

<!--         <el-table-column prop="synopsis" label="简介" width="200"> -->
<!--         </el-table-column> -->

      </el-table>
      <div  style="padding: 10px 0">
        <el-pagination v-if="sw==0"
          @size-change="handleSizeChange1"
          @current-change="handleCurrentChange1"
          :current-page="page"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
        <el-pagination v-else
          @size-change="handleSizeChange2"
          @current-change="handleCurrentChange2"
          :current-page="page"
          :page-sizes="[3, 5, 10, 20]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>

<!--  在这里进行分割-->

  <aside class="aside-content">

    <h2 class="content-header">标签列表</h2>

    <ul class="book-tag-list">
      <li
        v-for="(bookTag, index) in bookTags"
        :key="index"
        class="book-tag-item"
        :class="{'book-active-tag-item': bookTag === currentBookTags}"
        @click="changeCurrentBookTags(bookTag)"
      >
        {{bookTag.tagName}}
      </li>
    </ul>

    <ul>
      <li
        v-for="(subTag, index) in currentBookTags.subTags"
        :key="index"
        class="aside-item"
        :class="{'book-active-subtag-item': subTag === currentBookTag}"
        @click="updateSearch(subTag)"
      >
        <!--        @click="changeCurrentBookTag(subTag)"-->
        {{subTag}}
      </li>
    </ul>
  </aside>

  </div>

</template>

<script>
// import BookTest from './BookTest.vue'
export default {
  data () {
    return {
      currentPage: 0,
      slideDirection: 'right',
      contentPosition: null,
      loadPrompt: false,
      showPrompt: false,
      promptStyle: null,
      currentBookPrompt: null,
      tableData: [],
      total: 0,
      page: 1,
      size: 2,
      input1: '',
      input: '',
      sw: null,
      bt: null
    }
  },
  name: 'BookTag',
  created () {
    // this.load()
    this.search()
  },
  // watch: {
  //   tableData: function () {
  //     this.$nextTick(() => {
  //       this.$forceUpdate()
  //     })
  //   }
  // },
  computed: {
    bookTags () {
      return this.$store.state.book.bookTags
    },
    currentBookTags () {
      return this.$store.state.book.currentBookTags
    },
    currentBookTag () {
      return this.$store.state.book.currentBookTag
    },
    tagBooks () {
      return this.$store.state.book.tagBooks
    },
    // currentTagBooks () {
    //   return this.$store.state.book.currentTagBooks.slice(0, 40)
    // },
    processedBooks () {
      return this.myUtils.processedArray(this.currentTagBooks, 10)
    },
    pageCount () {
      // 默认每页显示十本书，通过 书的总量/10 得出页数
      return Math.ceil(this.currentTagBooks.length / 10)
    },
    transitionName () {
      // 获取翻页的方向
      return this.slideDirection === 'right' ? 'move-to-right' : 'move-to-left'
    }
  },
  /*   mounted () {
    if (this.currentTagBooks.length < 40) {
      // 默认加载40本书
      this.$store.dispatch('getCurrentTagBooks', {count: 40})
    }
  }, */

  methods: {

    // push () {
    //   this.$router.push('/book')
    // },
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

    // 全部书籍，可以通过isbn，书名，作者来进行搜索
    load () {
      fetch('http://localhost:3000/books/pageByBookalltest?page=' + this.page + '&size=' + this.size + '&text=' + this.input)
        .then(res => res.json()).then(res => {
        // console.log(res)
          this.tableData = res.data.records
          this.total = res.data.total
        })
    },
    // 进行书籍type分类搜索
    search () {
      // console.log(Vue.$data.currentBookTag)
      // eslint-disable-next-line no-undef

      // this.changeCurrentBookTag(subTag)
      // console.log(this.currentBookTag)
      this.sw = 0
      if (this.input !== null) {
        fetch('http://localhost:3000/books/pageByBookalltest?page=' + this.page + '&size=' + this.size + '&text=' + this.input)
          .then(res => res.json()).then(res => {
          // console.log(res)
            this.tableData = res.data.records
            this.total = res.data.total
          })
      } else {
        fetch('http://localhost:3000/books/pagebytype?page=' + this.page + '&size=' + this.size + '&type=' + this.currentBookTag)

          .then(res => res.json()).then(res => {
            // console.log(res)
            this.tableData = res.data.records
            this.total = res.data.total
          })
      }
    },

    SearchByDetail (subTag) {
      this.changeCurrentBookTag(subTag)
      // console.log(this.currentBookTag)
      fetch('http://localhost:3000/books/pagebytypedetail?page=' + this.page + '&size=' + this.size + '&type=' + this.currentBookTag + '&text=' + this.input1)
        .then(res => res.json()).then(res => {
        // console.log(res)
          this.tableData = res.data.records
          this.total = res.data.total
        })
    },
    // 进行size大小调整
    handleSizeChange1 (size) {
      console.log(size)
      this.size = size
      this.search()
    },
    // 进行页面调整使用
    handleCurrentChange1 (page) {
      console.log(page)
      this.page = page
      this.search()
    },
    handleSizeChange2 (size) {
      console.log(size)
      this.size = size
      this.updateSearch(this.bt)
    },
    // 进行页面调整使用
    handleCurrentChange2 (page) {
      console.log(page)
      this.page = page
      this.updateSearch(this.bt)
    },

    changePage (page) {
      this.currentPage = page
    },

    changeDirection (direction) {
      this.slideDirection = direction
    },
    updateSearch (subTag) {
      // BookTest.search()
      this.sw = 1
      this.bt = subTag
      this.changeCurrentBookTag(subTag)
      // doSearch()
      // // BookTest.methods.search(this.page, this.size, this.currentBookTag)
      // function doSearch () {
      // console.log(this.currentBookTag)
      if (this.currentBookTag === '全部') {
        fetch('http://localhost:3000/books/pageByBookalltest?page=' + this.page + '&size=' + this.size + '&text')
          .then(res => res.json()).then(res => {
          // console.log(res)
            this.tableData = res.data.records
            this.total = res.data.total
          })
      } else {
        fetch('http://localhost:3000/books/pagebytype?page=' + this.page + '&size=' + this.size + '&type=' + this.currentBookTag)
        // fetch('http://localhost:3000/books/pagebytype?page=1&size=10&type=' + this.currentBookTag)
          .then(res => res.json()).then(res => {
            console.log(res)
            this.tableData = res.data.records
            this.total = res.data.total
            // return res.data().records
          })
      }
    },

    // BookTest.methods.search(this.currentBookTag)
    // doSearch(){
    // }

    changeCurrentBookTags (tags) {
      this.$store.commit('SET_CURRENT_BOOK_TAGS', tags)
      this.$store.commit('SET_CURRENT_BOOK_TAG', tags.subTags[0])
      if (!this.tagBooks[tags.subTags[0]]) {
        this.$store.dispatch('getCurrentTagBooks', {count: 40})
      } else {
        this.$store.commit('SET_CURRENT_TAG_BOOKS', {books: this.tagBooks[tags.subTags[0]]})
      }
    },

    changeCurrentBookTag (subTag) {
      this.$store.commit('SET_CURRENT_BOOK_TAG', subTag)
      if (!this.tagBooks[subTag]) {
        this.$store.dispatch('getCurrentTagBooks', {count: 40})
      } else {
        this.$store.commit('SET_CURRENT_TAG_BOOKS', {books: this.tagBooks[subTag]})
      }
    }
  }
}
</script>

<style scoped>
.book-tag-list{
  border-bottom: 1px solid #eee;
  padding: 10px 0;
}
.book-tag-item{
  display: inline-block;
  margin: 5px 30px 3px 0;
  padding: 3px 5px;
  font-size: 12px;
  color: #37a;
  cursor: pointer;
}
.book-tag-item:hover{
  color: #09f;
}
.book-active-tag-item{
  background: #111;
  color: white;
}
.book-active-tag-item:hover{
  color: white;
}
.aside-item:hover{
  background: #f6f6f1;
}
.book-active-subtag-item{
  background: #f6f6f1;
}
</style>
