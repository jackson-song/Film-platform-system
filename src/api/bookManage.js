import request from '@/utils/request'


export default{
    getBookList(searchModel){
        return request({
            url: '/books/pageByBookalltest',
            method: 'get',
            params:{
                page: searchModel.pageNo,
                size: searchModel.pageSize,
                text: searchModel.book
            }
        });
    },

    getBookbyisbn(isbn){
        return request({
            url: '/books/pageByBookalltest',
            method: 'get',
            params:{
                page: 1,
                size: 10,
                text: isbn
            }
        });
    },

    addBook(book){
        return request({
            url: '/books',
            method: 'post',
            data: book
        });
    },
    upDataBook(book){
        return request({
            url: '/books',
            method: 'put',
            data: book
        });
    },
    deleteBook(book){
        return request({
            url: '/books',
            method: 'delete',
            data: book
        });
    }
}
