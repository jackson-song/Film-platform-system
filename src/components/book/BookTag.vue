<template>
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
</template>

<script>
// import BookTest from './BookTest.vue'
export default {
  name: 'BookTag',
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
      input: ''
    }
  },
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
    }
  },

  methods: {
    updateSearch (subTag) {
      // BookTest.search()
      this.changeCurrentBookTag(subTag)
      // doSearch()
      // // BookTest.methods.search(this.page, this.size, this.currentBookTag)
      // function doSearch () {
      console.log(this.currentBookTag)
      fetch('http://localhost:3000/books/pagebytype?page=1&size=10&type=' + this.currentBookTag)
        .then(res => res.json()).then(res => {
          console.log(res)
          this.tableData = res.data.records
          this.total = res.data.total
          // return res.data().records
        })
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
