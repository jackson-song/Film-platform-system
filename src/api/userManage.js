import request from '@/utils/request'


export default{
    getUserList(searchModel){
        return request({
            url: '/users',
            method: 'get',
            params:{
                page: searchModel.pageNo,
                size: searchModel.pageSize
            }
        });
    },
    getUser(searchModel){
        return request({
            url: '/users/selectuser',
            method: 'get',
            params:{
                page: searchModel.pageNo,
                size: searchModel.pageSize,
                text: searchModel.user
            }
        });
    },
    freezeUser(id){
        return request({
            url: '/users/'+id,
            method: 'post'
        });
    },
    thaw(id){
        return request({
            url: '/users/thaw/'+id,
            method: 'post'
        });
    }
}
