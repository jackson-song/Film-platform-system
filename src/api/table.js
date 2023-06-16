import request from '@/utils/request'

export default{
    getAllList(){
        return request({
            url: '/statistics/all',
            method: 'get'
        });
    },
    getGoodList(){
        return request({
            url: '/statistics/good',
            method: 'get'
        });
    },
    getWeekList(){
        return request({
            url: '/statistics/weeklystastic',
            method: 'get'
        });
    },
    getMostList(){
        return request({
            url: '/statistics/most',
            method: 'get'
        });
    },
    getMonthList(){
        return request({
            url: '/statistics/month',
            method: 'get'
        });
    }
}