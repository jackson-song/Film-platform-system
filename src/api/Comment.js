import request from '@/util/request'

export default{
  data () {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  getCommentList (searchModel) {
    return request({
      url: 'http://localhost:3000/comments/BookComment',
      method: 'get',
      params: {
        page: searchModel.pageNo,
        size: searchModel.pageSize,
        isbn: searchModel.book
      }
    })
  },
  getCommentHot (searchModel) {
    return request({
      url: 'http://localhost:3000/comments/bookhotcomment/' + searchModel.book,
      method: 'get',
      params: {
        page: searchModel.pageNo,
        size: searchModel.pageSize
      }
    })
  },
  getCommentNew (searchModel) {
    return request({
      url: 'http://localhost:3000/comments/booknewest/' + searchModel.book,
      method: 'get',
      params: {
        page: searchModel.pageNo,
        size: searchModel.pageSize
      }
    })
  },
  good (commentid) {
    return request({
      url: 'http://localhost:3000/comments/' + commentid,
      method: 'put'
    })
  },
  postComment (comment) {
    return request({
      url: 'http://localhost:3000/comments/posts',
      method: 'post',
      data: comment
    })
  },
  postCommentTest (comment) {
    return request({
      url: 'http://localhost:3000/comments/posts',
      method: 'post',
      params: {
        userid: this.user.userid,
        username: this.user.username,
        isbn: comment.isbn
      }
    })
  },
  getcommentbyidandisbn (userid, isbn) {
    return request({
      url: 'http://localhost:3000/comments/usercomment/' + userid + '/' + isbn,
      method: 'get'
    })
  },
  UDcomment (comment) {
    return request({
      url: 'http://localhost:3000/comments',
      method: 'put',
      data: comment
    })
  },
  decomment (commentid) {
    return request({
      url: 'http://localhost:3000/comments/' + commentid,
      method: 'delete'
    })
  },
  CommentHot (searchModel) {
    return request({
      url: 'http://localhost:3000/comments/hotcomment',
      method: 'get',
      params: {
        page: searchModel.pageNo,
        size: searchModel.pageSize
      }
    })
  },
  CommentNew (searchModel) {
    return request({
      url: 'http://localhost:3000/comments/newest',
      method: 'get',
      params: {
        page: searchModel.pageNo,
        size: searchModel.pageSize
      }
    })
  }
}
