
import Vue from 'vue'
import Router from 'vue-router'
// import Book from '../store/module/book'

Vue.use(Router)

// import WebLogin from "@/views/WebLogin.vue";
// import WebIndex from "@/views/WebIndex.vue";
// import WebRegister from "@/views/WebRegister.vue";

// const WebLoginTest = () => import('../views/WebLoginTest.vue')
const WebLogin = () => import('../views/WebLogin.vue')
const WebIndex = () => import('../views/WebIndex.vue')
const WebRegister = () => import('../views/WebRegister.vue')
const BaseHeader = () => import('../components/common/BaseHeader.vue')
const BookTag = () => import('../components/book/BookTag.vue')
const BookTagContent = () => import('../components/book/BookTagContent.vue')
const BookTagMoreContent = () => import('../components/book/BookTagMoreContent.vue')
// const BookTypeContent = () => import('../components/book/BookTypeContent.vue')
const MovieTimeTypeContent = () => import('../components/movie/MovieTimeTypeContent.vue')
const MovieTagContent = () => import('../components/movie/MovieTagContent.vue')
const MovieTag = () => import('../components/movie/MovieTag.vue')
const MusicTagContent = () => import('../components/music/MusicTagContent.vue')
const CityTag = () => import('../components/city/CityTag.vue')
const CityActivityContent = () => import('../components/city/CityActivityContent')
const BaseUpdateHeader = () => import('../components/common/BaseUpdateHeader.vue')
const BookTest = () => import('../components/book/BookTest.vue')
const Test = () => import('../components/book/Test.vue')
const Test2 = () => import('../components/book/TotalPage.vue')
const HotBook = () => import('../components/book/HotBook.vue')
const SimpleHeader = () => import('../components/common/SimpleHeader')
export default new Router({
  routes: [
    /*     {
      path: '/',
      name: 'home',
      component: WebIndex
    }, */
    {
      path: '/login',
      name: 'login',
      component: WebLogin
    }, {
      path: '/index',
      name: 'index',
      components: {
        default: SimpleHeader,
        content: WebIndex
      }
    }, {
      path: '/register',
      name: 'register',
      component: WebRegister
    }, {
      path: '/',
      redirect: '/book-final'
    },
    {
      path: '/book-tag',
      name: 'bookTag',
      components: {
        default: BaseHeader,
        aside: BookTag,
        // content: BookTagContent
        content: BookTest
      }
    },
    {
      path: '/book-tag-update',
      name: 'bookTagTest',
      components: {
        default: BaseUpdateHeader,
        aside: BookTag,
        content: BookTagContent
      }
    },
    {
      path: '/book-tag-more-info',
      name: 'bookTagMoreInfo',
      components: {
        default: BaseHeader,
        aside: BookTag,
        content: BookTagMoreContent
      }
    },
    {
      path: '/book-hot',
      name: 'bookType',
      components: {
        default: BaseHeader,
        // content: BookTypeContent
        content: HotBook
      }
    },
    {
      path: '/movie-show-time',
      name: 'movieShowTime',
      components: {
        default: BaseHeader,
        content: MovieTimeTypeContent
      }
    },
    {
      path: '/movie-tag',
      name: 'movieTag',
      components: {
        default: BaseHeader,
        aside: MovieTag,
        content: MovieTagContent
      }
    },
    {
      path: '/music-tag',
      name: 'musicTag',
      components: {
        default: BaseHeader,
        content: MusicTagContent
      }
    },
    {
      path: '/city',
      name: 'city',
      components: {
        default: BaseHeader,
        aside: CityTag,
        content: CityActivityContent
      }
    }, {
      path: '/test',
      name: 'test',
      component: Test
    }, {
      path: '/book-final',
      name: 'test2',
      components: {
        default: BaseHeader,
        // aside: BookTag,
        content: Test2
      }
    }, {
      path: '/book-final-update',
      name: 'book-final-update',
      components: {
        default: BaseUpdateHeader,
        content: Test2
      }
    }
  ]
})
