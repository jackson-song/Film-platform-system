
import * as types from '../mutation-types'
import axios from 'axios'

const bookTags = [
  {tagName: '全部', subTags: ['全部']},
  {tagName: '文学', subTags: ['小说', '散文', '名著', '日本文学', '随笔', '名著']},
  {tagName: '流行', subTags: ['青春', '科幻', '武侠', '奇幻', '艺术', '电影']},
  {tagName: '文化', subTags: ['历史', '哲学', '建筑', '推理', '电影', '回忆录']},
  {tagName: '生活', subTags: ['旅行', '美食', '健康', '漫画', '美食', '励志']},
  {tagName: '经管', subTags: ['管理', '金融', '经济学', '股票']},
  {tagName: '科技', subTags: ['互联网', '编程', '算法']}
]

const bookTypes = ['虚构类', '非虚构类']

const state = {
  bookTags,
  currentBookTags: bookTags[0],
  currentBookTag: bookTags[0].subTags[0],
  tagBooks: {},
  currentTagBooks: [],
  bookTypes,
  currentBookType: bookTypes[0],
  typeBooks: {},
  currentTypeBooks: []
}

const mutations = {
  [types.SET_CURRENT_BOOK_TAGS] (state, tags) {
    state.currentBookTags = tags
  },
  [types.SET_CURRENT_BOOK_TAG] (state, tag) {
    state.currentBookTag = tag
  },
  [types.SET_TAG_BOOKS] (state, {books, type}) {
    // 当type为merge时，会将新的数据与原来的数据合并
    if (type !== 'merge') {
      state.tagBooks[state.currentBookTag] = books
    } else {
      state.tagBooks[state.currentBookTag] = state.tagBooks[state.currentBookTag].concat(books)
    }
  },
  [types.SET_CURRENT_TAG_BOOKS] (state, {books, type}) {
    // 当type为merge时，会将新的数据与原来的数据合并
    if (type !== 'merge') {
      state.currentTagBooks = books
    } else {
      state.currentTagBooks = state.currentTagBooks.concat(books)
    }
  },
  [types.SET_CURRENT_BOOK_TYPE] (state, type) {
    state.currentBookType = type
  },
  [types.SET_TYPE_BOOKS] (state, books) {
    state.typeBooks[state.currentBookType] = books
  },
  [types.SET_CURRENT_TYPE_BOOKS] (state, books) {
    state.currentTypeBooks = books
  }
}

const actions = {
  // getCurrentTagBooks ({commit, state}, {count = 10, start = 0, type}) {
  //   axios.get('books/pageByBookalltest', {
  //     params: {
  //       tag: state.currentBookTag,
  //       count,
  //       start
  //     }
  //   })
  //     .then(response => {
  //       commit(types.SET_TAG_BOOKS, {books: response.data.books, type})
  //       commit(types.SET_CURRENT_TAG_BOOKS, {books: response.data.books, type})
  //     })
  // },
  getCurrentTagBooks ({commit, state}, {page = 0, size = 10, type}) {
    axios.get('books/pagebytype', {
      params: {
        page,
        size,
        type: state.currentBookTag
      }
    })
      .then(response => {
        commit(types.SET_TAG_BOOKS, {books: response.data.records, type})
        commit(types.SET_CURRENT_TAG_BOOKS, {books: response.data.records, type})
      })
  },
  getTypeBooks ({commit, state}, {count = 10, start = 0}) {
    axios.get('books/pagebytype', {
      params: {
        q: state.currentBookType,
        count,
        start
      }
    })
      .then(response => {
        commit(types.SET_TYPE_BOOKS, response.data.books)
        commit(types.SET_CURRENT_TYPE_BOOKS, response.data.books)
      })
  }
}

export default {
  state,
  mutations,
  actions
}
