import request from '@/util/request'

export default{
  getBookList (searchModel) {
    return request({
      url: 'http://localhost:3000/books/pageByBookalltest',
      method: 'get',
      params: {
        page: searchModel.pageNo,
        size: searchModel.pageSize,
        text: searchModel.book
      }
    })
  },

  getBookbyisbn (isbn) {
    return request({
      url: 'http://localhost:3000/books/pageByBookalltest',
      method: 'get',
      params: {
        page: 1,
        size: 10,
        text: isbn
      }
    })
  },

  addBook (book) {
    return request({
      url: '/books',
      method: 'post',
      data: book
    })
  },
  upDataBook (book) {
    return request({
      url: '/books',
      method: 'put',
      data: book
    })
  },
  deleteBook (book) {
    return request({
      url: '/books',
      method: 'delete',
      data: book
    })
  },
  addshelf (userid, isbn) {
    return request({
      url: 'http://localhost:3000/books/shelf/' + userid + '/' + isbn,
      method: 'post'
    })
  }
}
